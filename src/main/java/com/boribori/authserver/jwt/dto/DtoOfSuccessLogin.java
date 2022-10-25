package com.boribori.authserver.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfSuccessLogin {
    private String id;
    private String accessToken;
    private String refreshToken;
    private String nickname;
}
