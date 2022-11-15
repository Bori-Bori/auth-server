package com.boribori.authserver.util;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ParsingUtil {

    private final String AUTHORIZATION_PREFIX = "Bearer ";

    public Mono<String> getToken(String header){
        return Mono.just(header.substring(AUTHORIZATION_PREFIX.length()));
    }
}
