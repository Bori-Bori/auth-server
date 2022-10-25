package com.boribori.authserver.advice;

import com.boribori.authserver.common.Response;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MalformedJwtException.class)
    public Mono<ResponseEntity> handleMalformedJwtException(){
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg("Invalid Token").build())
                .content(null)
                        .build())
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public Mono<ResponseEntity> handleExpiredJwtException(){
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg("Expired Token").build())
                .content(null)
                .build())
        );
    }
}
