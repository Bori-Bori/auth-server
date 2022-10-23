package com.boribori.authserver.login;

import com.boribori.authserver.oauth2.util.OAuth2RequestUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class LoginController {


    private final OAuth2RequestUtil oAuth2RequestUtil;

    @GetMapping("/api/login")
    public Mono<ResponseEntity> login(@RequestParam String code){
        Mono<ResponseEntity> a = oAuth2RequestUtil.requestAuth(code)
                .map(response ->
                    ResponseEntity.status(HttpStatus.OK).body(response)
                ).cast(ResponseEntity.class);

        return a;
    }
}
