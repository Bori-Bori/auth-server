package com.boribori.authserver.jwt.util;

import com.boribori.authserver.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtFactory {

    private final JwtProperties jwtProperties;
    /**
     * JWT 의 엑세스 토큰 키
     */
    private String ACCESS_TOKEN_SECRET_KEY = jwtProperties.getProperties().get("accessToken").getKey();

    /**
     * JWT 의 리프레시 토큰 키
     */
    private String REFRESH_TOKEN_SECRET_KEY = jwtProperties.getProperties().get("refreshToken").getKey();

    /**
     * 엑세스 토큰 유효 기간
     */
    private Long ACCESS_TOKEN_VALID_TIME = jwtProperties.getProperties().get("accessToken").getExpiredTime();

    /**
     * 리프레시 토큰 유효 기간
     */
    private Long REFRESH_TOKEN_VALID_TIME = jwtProperties.getProperties().get("refreshToken").getExpiredTime();

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
        encodedAccessKey = Base64.getEncoder().encodeToString(this.ACCESS_TOKEN_SECRET_KEY.getBytes());
        encodedRefreshKey = Base64.getEncoder().encodeToString(this.REFRESH_TOKEN_SECRET_KEY.getBytes());
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
                .setExpiration(new Date(now.getTime() + this.ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.encodedAccessKey)
                .compact();
    }

    /**
     * 리프레시 토큰 생성 메서드
     * @param accessToken : 생성된 엑세스 토큰
     * @return : 생성된 레프레시토큰
     */
    public String createRefreshToken(String accessToken){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.encodedRefreshKey)
                .compact();
    }


}
