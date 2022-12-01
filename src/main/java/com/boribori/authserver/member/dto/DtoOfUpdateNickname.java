package com.boribori.authserver.member.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoOfUpdateNickname {

    private String id;
    private String nickname;

}
