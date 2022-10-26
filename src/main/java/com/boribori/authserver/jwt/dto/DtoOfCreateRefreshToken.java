package com.boribori.authserver.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfCreateRefreshToken {
    private String refreshToken;
    private String id;
}
