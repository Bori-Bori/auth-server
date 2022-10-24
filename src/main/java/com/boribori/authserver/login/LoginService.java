package com.boribori.authserver.login;

import com.boribori.authserver.oauth2.dto.DtoOfOauth2TokenResponse;
import com.boribori.authserver.oauth2.util.OAuth2RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final OAuth2RequestUtil oAuth2RequestUtil;

//    public Mono<DtoOfOauth2TokenResponse> login(String authorizationCode, String target){
//        return oAuth2RequestUtil.requestAuth(authorizationCode, target);
//    }

    public void login(String authorizationCode, String target){
        oAuth2RequestUtil.requestAuth(authorizationCode, target);
    }
}
