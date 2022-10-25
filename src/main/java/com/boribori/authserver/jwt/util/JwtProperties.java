package com.boribori.authserver.jwt.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Map<String, DtoOfJwtProperties> properties;

    @Getter
    @Setter
    public static class DtoOfJwtProperties {
        private String key;
        private Long expiredTime;
    }

}
