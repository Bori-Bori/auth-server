package com.boribori.authserver.exception;

import lombok.Getter;

@Getter
public class NotFoundUserException extends RuntimeException{

    private String msg;

    public NotFoundUserException(String msg){
        this.msg = msg;
    }
}
