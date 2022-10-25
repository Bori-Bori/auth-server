package com.boribori.authserver.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/api/refresh")
    public Mono<String> refresh(@RequestBody String refreshToken){
        return jwtService.refreshToken(refreshToken);
    }
}
