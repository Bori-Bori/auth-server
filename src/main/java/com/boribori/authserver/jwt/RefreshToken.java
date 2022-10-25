package com.boribori.authserver.jwt;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("refresh_token")
public class RefreshToken {
    @PrimaryKey()
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String nickname;
    private String refreshToken;
}
