package com.boribori.authserver.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class NotFoundRefreshTokenException extends RuntimeException{
    private String msg;
}
