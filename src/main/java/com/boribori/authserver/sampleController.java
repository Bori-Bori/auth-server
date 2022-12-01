package com.boribori.authserver;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class sampleController {

    @GetMapping("/api/aabbb")
    public Mono<String> abC(){
        return Mono.just("ㄱㅜㄷ굳");
    }

    @GetMapping("/api/test2")
    public Mono<String> aabb(){
        return Mono.just("redirect:https://kauth.kakao.com/oauth/authorize?client_id=de251a4cfa22c0563789d81e137437de&redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code");
    }
}
