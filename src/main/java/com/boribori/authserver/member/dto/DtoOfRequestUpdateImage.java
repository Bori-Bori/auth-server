package com.boribori.authserver.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoOfRequestUpdateImage {
    private String id;
    private String imagePath;
}
