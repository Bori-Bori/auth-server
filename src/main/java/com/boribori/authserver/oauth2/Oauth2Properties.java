package com.boribori.authserver.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2")
@Configuration
@EnableConfigurationProperties
public class Oauth2Properties {
    private Map<String, DtoOfOauth2Properties> properties;

    @Setter
    @Getter
    public static class DtoOfOauth2Properties {
        private String clientId;
        private String clientSecret;
        private String tokenUri;
        private String userInfoUri;

    }
}
