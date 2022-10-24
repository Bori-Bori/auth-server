package com.boribori.authserver.jwt.util;

import com.boribori.authserver.jwt.dto.DtoOfUserDataFromJwt;
import com.boribori.authserver.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    /**
     * 엑세스 토큰 키
     */


    /**
     * 리프레시 토큰 키
     */


    public void authenticateAccessToken(String accessToken){

        Jwts.parser()
                .setSigningKey(jwtProperties.getProperties().get("accessToken").getKey().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public void authenticateRefreshToken(String refreshToken){
        Jwts.parser().setSigningKey(jwtProperties.getProperties().get("refreshToken").getKey().getBytes(StandardCharsets.UTF_8)).parseClaimsJws(refreshToken).getBody();
    }

    public DtoOfUserDataFromJwt getUserData(String accessToken){

        Claims claims = getClaims(accessToken, this.jwtProperties.getProperties().get("accessToken").getKey());


        return DtoOfUserDataFromJwt.builder()
                .id(claims.getSubject())
                .nickname(claims.get("nickname").toString())
                .build();


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
}
