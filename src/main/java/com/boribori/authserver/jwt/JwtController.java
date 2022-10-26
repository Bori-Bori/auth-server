package com.boribori.authserver.jwt;

import com.boribori.authserver.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/api/refresh")
    public Mono<ResponseEntity> refresh(@RequestParam String refreshToken){

//        jwtService.refreshToken(Mono.just(refreshToken))
//                .flatMap(response -> ResponseEntity.status(HttpStatus.OK)
//                        .body(Response.builder()
//                                        .status(Response.Status.builder()
//                                                .msg("성공적으로 갱신되었습니다.")
//                                                .build())
//                                        .content(response)
//                                        .build()))).cast(ResponseEntity.class);
        return jwtService.refreshToken(Mono.just(refreshToken))
                .map(response -> {
                    System.out.println("여기 실행 response = " + response.getId());
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(Response.builder()
                                    .status(Response.Status.builder()
                                            .msg("성공적으로 갱신되었습니다.")
                                            .build())
                                    .content(response)
                                    .build());
                }).cast(ResponseEntity.class);
    }
}
