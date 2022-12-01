package com.boribori.authserver.notification.exception;

import lombok.Getter;

@Getter
public class NotFoundNotificationException extends RuntimeException{

    private String msg;

    public NotFoundNotificationException(String msg){
        this.msg = msg;
    }
}
