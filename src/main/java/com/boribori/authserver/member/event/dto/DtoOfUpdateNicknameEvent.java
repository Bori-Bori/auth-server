package com.boribori.authserver.member.event.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class DtoOfUpdateNicknameEvent{

    private String id;
    private String nickname;
    private String profilePath;

}
