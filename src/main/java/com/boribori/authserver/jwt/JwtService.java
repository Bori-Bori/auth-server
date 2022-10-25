package com.boribori.authserver.jwt;

import com.boribori.authserver.jwt.dto.DtoOfSuccessLogin;
import com.boribori.authserver.jwt.util.JwtFactory;
import com.boribori.authserver.jwt.util.JwtProvider;
import com.boribori.authserver.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 레디스 처리 필요
     */

    private final JwtFactory jwtFactory;

    public Mono<DtoOfSuccessLogin> login(Mono<Member> memberEntityMono){
        return jwtFactory.login(memberEntityMono)
                .flatMap(loginEntity -> {return saveRefreshTokenEntity(loginEntity);}
        );
    }

    public Mono<DtoOfSuccessLogin> saveRefreshTokenEntity(DtoOfSuccessLogin dto){

        return refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(dto.getRefreshToken())
                .userId(dto.getId())
                .nickname(dto.getNickname())
                .build()).flatMap(entity -> {
                    return Mono.just(dto);
        });
    }

}
