package com.boribori.authserver.jwt;

import com.boribori.authserver.jwt.dto.DtoOfSaveRefreshToken;
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

    private final JwtProvider jwtProvider;

    /**
     * 레디스 처리 필요
     */

    private final JwtFactory jwtFactory;

    public Mono<DtoOfSuccessLogin> login(Mono<Member> memberEntityMono){
        return jwtFactory.login(memberEntityMono)
                .flatMap(loginEntity -> {return saveRefreshTokenEntity(loginEntity);}
        );
    }

    public Mono<DtoOfSuccessLogin> saveRefreshTokenEntity(DtoOfSaveRefreshToken dto){
        return refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(dto.getRefreshToken())
                .userId(dto.getUserId())
                .id(dto.getId())
                .nickname(dto.getNickname())
                .build())
                .flatMap(entity -> {
                    return Mono.just(DtoOfSuccessLogin.builder()
                                    .tokenId(entity.getId())
                            .refreshToken(dto.getRefreshToken())
                            .accessToken(dto.getAccessToken())
                            .id(dto.getUserId())
                            .nickname(dto.getNickname())
                            .build());
        });
    }

    public Mono<DtoOfSuccessLogin> refreshToken(Mono<String> refreshToken){


        return refreshToken
                .map(token -> {
                    System.out.println("1번요~ = " + token);
                    return jwtProvider.authenticateRefreshToken(token);
                })
                .flatMap(token2 -> {
                    System.out.println("1.5");
                    return jwtProvider.getRefreshTokenSubject(token2);
                })
                .flatMap(subject -> {
                    System.out.println("2번요 ~" + subject);
                    return refreshTokenRepository.findById(subject);
                })
                .transform(refreshTokenMono -> {
                    return jwtProvider.refresh(refreshTokenMono);
                })
                .flatMap(response -> {
                    System.out.println("token id = " + response.getTokenId());
                    return refreshTokenRepository.findById(response.getTokenId())
                                    .flatMap(entity -> refreshTokenRepository.deleteById(entity.getId()))
                                            .thenReturn(response);
                })
                .flatMap(response -> {
                    return refreshTokenRepository.save(RefreshToken.builder()
                            .refreshToken(response.getRefreshToken())
                            .userId(response.getId())
                            .id(response.getTokenId())
                            .nickname(response.getNickname())
                            .build())
                            .thenReturn(response);
                });

    }

    public Mono<RefreshToken> getTokenEntity(String id){
        return refreshTokenRepository.findById(id);
    }

}
