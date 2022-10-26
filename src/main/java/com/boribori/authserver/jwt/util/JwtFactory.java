package com.boribori.authserver.jwt.util;

import com.boribori.authserver.jwt.RefreshToken;
import com.boribori.authserver.jwt.dto.DtoOfCreateRefreshToken;
import com.boribori.authserver.jwt.dto.DtoOfSaveRefreshToken;
import com.boribori.authserver.jwt.dto.DtoOfSuccessLogin;
import com.boribori.authserver.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtFactory {

    private final JwtProperties jwtProperties;



    /**
     * Base64로 인코딩된 엑세스 토큰 키
     */
    private String encodedAccessKey;

    /**
     * Base64로 인코딩된 리프레시 토큰 키
     */
    private String encodedRefreshKey;

    /**
     * 인코딩을 하기위한 메서드
     */
    @PostConstruct
    protected void init(){
        encodedAccessKey = Base64.getEncoder().encodeToString(this.jwtProperties.getProperties().get("accessToken").getKey().getBytes());
        encodedRefreshKey = Base64.getEncoder().encodeToString(this.jwtProperties.getProperties().get("refreshToken").getKey().getBytes());
    }

    /**
     * 엑세스 토큰을 생성하기 위한 메서드
     * @param member : 요청자 엔티티
     * @return 생성된 엑세스 토큰
     */
    public String createAccessToken(Member member){

        Claims claims = Jwts.claims().setSubject(member.getId());
        claims.put("nickname", member.getNickname());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.jwtProperties.getProperties().get("accessToken").getExpiredTime()))
                .signWith(SignatureAlgorithm.HS256, this.encodedAccessKey)
                .compact();
    }

    /**
     * 리프레시 토큰 생성 메서드
     * @param accessToken : 생성된 엑세스 토큰
     * @return : 생성된 레프레시토큰
     */
    public DtoOfCreateRefreshToken createRefreshToken(String accessToken){
        String refreshTokenId = UUID.randomUUID().toString();
        Date now = new Date();

        return DtoOfCreateRefreshToken.builder().refreshToken(Jwts.builder()
                .setSubject(refreshTokenId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.jwtProperties.getProperties().get("refreshToken").getExpiredTime()))
                .signWith(SignatureAlgorithm.HS256, this.encodedRefreshKey)
                .compact())
                .id(refreshTokenId)
                .build();
    }

    public Mono<DtoOfSaveRefreshToken> login(Mono<Member> memberEntityMono){

        return memberEntityMono.flatMap(
                member ->  {
                    String accessToken = createAccessToken(member);
                    DtoOfCreateRefreshToken refreshToken = createRefreshToken(accessToken);
                    return Mono.just(DtoOfSaveRefreshToken.builder()
                            .id(refreshToken.getId())
                            .accessToken(accessToken)
                            .userId(member.getId())
                            .refreshToken(refreshToken.getRefreshToken())
                            .nickname(member.getNickname())
                            .build());
                }

        );

    }

    public String createAccessToken(String id, String nickname){
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("nickname", nickname);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.jwtProperties.getProperties().get("accessToken").getExpiredTime()))
                .signWith(SignatureAlgorithm.HS256, this.encodedAccessKey)
                .compact();
    }

    public Mono<DtoOfSuccessLogin> refreshAccessToken(Mono<RefreshToken> refreshTokenMono){

        return refreshTokenMono
                .flatMap(tokenEntity -> {
                    String accessToken = createAccessToken(tokenEntity.getId(), tokenEntity.getNickname());

                    return Mono.just(DtoOfSuccessLogin.builder()
                            .accessToken(accessToken)
                            .tokenId(tokenEntity.getId())
                            .refreshToken(tokenEntity.getRefreshToken())
                            .id(tokenEntity.getUserId())
                            .nickname(tokenEntity.getNickname())
                            .build());
                });


    }

    public Mono<DtoOfSuccessLogin> refreshBoth(Mono<RefreshToken> refreshTokenMono){
        return refreshTokenMono
                .flatMap(tokenEntity -> {
                    String accessToken = createAccessToken(tokenEntity.getId(), tokenEntity.getNickname());
                    DtoOfCreateRefreshToken refreshToken = createRefreshToken(accessToken);
                    return Mono.just(DtoOfSuccessLogin.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken.getRefreshToken())
                            .id(tokenEntity.getUserId())
                            .tokenId(tokenEntity.getId())
                            .nickname(tokenEntity.getNickname())
                            .build());
                });
    }
}
