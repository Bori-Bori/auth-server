package com.boribori.authserver.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfRequestUpdateImage {
    private String id;
    private String imagePath;
}
