package com.boribori.authserver.login;

import com.boribori.authserver.jwt.util.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final JwtProperties jwtProperties;


//    @GetMapping("/api/login/{target}")
//    public Mono<ResponseEntity> login(@RequestParam String code, @PathVariable(name = "target")String target){
//        Mono<ResponseEntity> a = oAuth2RequestUtil.requestAuth(code, target)
//                .map(response ->
//                    ResponseEntity.status(HttpStatus.OK).body(response)
//                ).cast(ResponseEntity.class);
//
//        return a;
//    }

    @GetMapping("/api/login/{target}")
    public Mono<ResponseEntity> login(@RequestParam String code, @PathVariable(name = "target")String target){
        Mono<ResponseEntity> a = loginService.login(code, target)
                .map(response ->
                        ResponseEntity.status(HttpStatus.OK).body(response)
                ).cast(ResponseEntity.class);

        return a;
    }
    @GetMapping("/api/test")
    public void test(){


    }

}
