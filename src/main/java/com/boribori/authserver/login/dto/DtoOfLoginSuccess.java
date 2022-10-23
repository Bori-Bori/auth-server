package com.boribori.authserver.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfLoginSuccess {

    private String id;
    private String accessToken;
    private String refreshToken;
    private String nickname;
}
