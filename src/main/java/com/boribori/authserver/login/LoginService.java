package com.boribori.authserver.login;

import com.boribori.authserver.oauth2.util.OAuth2RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final OAuth2RequestUtil oAuth2RequestUtil;

//    public Mono<DtoOfOauth2TokenResponse> login(String authorizationCode){
//        return oAuth2RequestUtil.requestAuth(authorizationCode);
//    }
}
