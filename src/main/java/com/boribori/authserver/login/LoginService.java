package com.boribori.authserver.login;

import com.boribori.authserver.jwt.JwtService;
import com.boribori.authserver.jwt.dto.DtoOfSuccessLogin;
import com.boribori.authserver.member.MemberService;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2TokenResponse;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import com.boribori.authserver.oauth2.util.OAuth2RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberService memberService;
    private final OAuth2RequestUtil oAuth2RequestUtil;

    private final JwtService jwtService;

//    public Mono<DtoOfOauth2TokenResponse> login(String authorizationCode, String target){
//        return oAuth2RequestUtil.requestAuth(authorizationCode, target);
//    }

    public Mono<DtoOfSuccessLogin> login(String authorizationCode, String target){

        return oAuth2RequestUtil.requestAuth(authorizationCode, target).flatMap(
                profile -> memberService.login(Mono.just(profile))
        ).publish(memberMono -> {
            return jwtService.login(memberMono);
        });
    }
}
