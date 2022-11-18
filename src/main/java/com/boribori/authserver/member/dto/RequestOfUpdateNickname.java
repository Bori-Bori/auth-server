package com.boribori.authserver.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestOfUpdateNickname {
    private String nickname;
}
