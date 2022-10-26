package com.boribori.authserver.login;

import com.boribori.authserver.jwt.RefreshTokenRepository;
import com.boribori.authserver.jwt.util.JwtProperties;
import com.boribori.authserver.login.dto.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;


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
    public Mono<String> test(@RequestParam String abc){
       return refreshTokenRepository.findById(abc)
                .flatMap(p -> refreshTokenRepository.deleteById(p.getId())).thenReturn("Rmx");
    }

}
