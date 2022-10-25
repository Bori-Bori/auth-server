package com.boribori.authserver.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfUserDataFromJwt {

    private String id;
    private String nickname;
}
