package com.boribori.authserver.member;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
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

    private String oauth2Id;

    private String nickname;

    private List<Integer> list;

}
