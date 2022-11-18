package com.boribori.authserver.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfUpdateNickname {

    private String id;
    private String nickname;

}
