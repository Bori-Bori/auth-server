package com.boribori.authserver.jwt.util;

import com.boribori.authserver.jwt.RefreshToken;
import com.boribori.authserver.jwt.dto.DtoOfSuccessLogin;
import com.boribori.authserver.jwt.dto.DtoOfUserDataFromJwt;
import com.boribori.authserver.jwt.dto.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final JwtFactory jwtFactory;

    private String encodedRefreshKey;
    private String encodedAccessKey;

    @PostConstruct
    protected void init(){
        encodedAccessKey = Base64.getEncoder().encodeToString(this.jwtProperties.getProperties().get("accessToken").getKey().getBytes());
        encodedRefreshKey = Base64.getEncoder().encodeToString(this.jwtProperties.getProperties().get("refreshToken").getKey().getBytes());
    }

    public Mono<String> authenticateAccessToken(String accessToken){

        Jwts.parser()
                .setSigningKey(encodedAccessKey)
                .parseClaimsJws(accessToken)
                .getBody();
        return Mono.just(accessToken);
    }

    public Mono<DtoOfSuccessLogin> refresh(Mono<RefreshToken> refreshTokenMono){

        // refresh 유효 체크
        // refresh 갱신 체크

       return refreshTokenMono
                .flatMap(v -> {
                    if(checkRenewRefreshToken(v.getRefreshToken(), 3L)){
                        return jwtFactory.refreshBoth(refreshTokenMono);
                    }
                    Mono<DtoOfSuccessLogin> dto = jwtFactory.refreshAccessToken(refreshTokenMono)
                            .flatMap(dtoOfSuccessLogin -> Mono.just(dtoOfSuccessLogin));
                    return dto;
                });

    }

    public Mono<String> authenticateRefreshToken(String refreshToken){
        Jwts.parser().setSigningKey(encodedRefreshKey).parseClaimsJws(refreshToken).getBody();

        return Mono.just(refreshToken);
    }



    /**
     * 리프레시 토큰을 갱신해야하는지 판별하는 메서드
     * @param refreshToken : 요청자에게 전달받은 리프레시 토큰
     * @param time : 리프레시 토큰 유효기간
     * @return true일 경우 리프레시 필요, false일 경우 리프레시 불 필요
     */
    public boolean checkRenewRefreshToken(String refreshToken, Long time){


        Instant now = Instant.now();
        Instant expiredTime = getClaims(refreshToken, this.jwtProperties.getProperties().get("refreshToken").getKey()).getExpiration().toInstant();

        long diffTIme = now.until(expiredTime, ChronoUnit.DAYS);

        return diffTIme < time;
    }

    /**
     * 토큰으로부터 Claims 객체를 얻기위한 메서드
     * @param token : JWT
     * @param tokenKey : JWT key
     * @return JWT claims
     */
    private Claims getClaims(String token, String tokenKey){
        return Jwts.parser()
                .setSigningKey(tokenKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    private Mono<Claims> getClaimsMono(String token, String tokenKey){
        return Mono.just(Jwts.parser()
                .setSigningKey(tokenKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody());
    }

    public Mono<String> getRefreshTokenSubject(Mono<String> token){
        return token
                .flatMap(value -> getClaimsMono(value, this.jwtProperties.getProperties().get("refreshToken").getKey())
                        .map(claims -> {
                            if(claims.isEmpty()){
                            }
                            return claims.getSubject();
                        }));
    }

    public Mono<TokenData> getTokenData(String accessToken){
        Claims claims = getClaims(accessToken, this.jwtProperties.getProperties().get("accessToken").getKey());

        return Mono.just(
                TokenData.builder()
                        .userId(claims.getSubject())
                        .nickname((String) claims.get("nickname"))
                        .build()
        );

    }
}
