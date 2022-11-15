package com.boribori.authserver.member;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("member")
public class Member {
    @PrimaryKey()
    @Builder.Default
    private String id = UUID.randomUUID().toString();;

    private String nickname;

    public Member updateNickname(String nickname){
        if (nickname == null){
            return this;
        }
        if(nickname.equals("")){
            return this;
        }
        this.nickname = nickname;
        return this;
    }
}
