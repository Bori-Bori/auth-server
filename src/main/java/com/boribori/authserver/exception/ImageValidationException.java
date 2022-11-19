package com.boribori.authserver.exception;

import lombok.Getter;

@Getter
public class ImageValidationException extends RuntimeException{

    private String msg;

    public ImageValidationException(String msg){
        this.msg = msg;
    }
}
