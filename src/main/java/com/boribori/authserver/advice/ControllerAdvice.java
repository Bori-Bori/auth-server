package com.boribori.authserver.advice;

import com.boribori.authserver.common.Response;
import com.boribori.authserver.exception.ImageValidationException;
import com.boribori.authserver.exception.NotFoundRefreshTokenException;
import com.boribori.authserver.exception.NotFoundUserException;
import com.boribori.authserver.notification.exception.NotFoundNotificationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({MalformedJwtException.class, NotFoundRefreshTokenException.class, SignatureException.class})
    public Mono<ResponseEntity> handleMalformedJwtException(){
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg("Invalid Token").build())
                .content(null)
                        .build())
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public Mono<ResponseEntity> handleExpiredJwtException(){
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg("Expired Token").build())
                .content(null)
                .build())
        );
    }

//    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity> handleCommonException(){
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg("Bad Request").build())
                .content(null)
                .build())
        );
    }

    @ExceptionHandler(NotFoundUserException.class)
    public Mono<ResponseEntity> handleNotFoundUserException(NotFoundUserException e){
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg(e.getMsg()).build())
                .content(null)
                .build())
        );
    }

    @ExceptionHandler(ImageValidationException.class)
    public Mono<ResponseEntity> handleNImageValidationException(ImageValidationException e){
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg(e.getMsg()).build())
                .content(null)
                .build())
        );
    }

    @ExceptionHandler(NotFoundNotificationException.class)
    public Mono<ResponseEntity> handleNotFoundNotificationException(){
        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(Response.
                builder()
                .status(Response.Status.builder()
                        .msg("????????? ????????? ???????????? ????????????.").build())
                .content(null)
                .build())
        );
    }




}
