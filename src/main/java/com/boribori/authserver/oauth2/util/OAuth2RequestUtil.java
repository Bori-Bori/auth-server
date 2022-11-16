package com.boribori.authserver.oauth2.util;

import com.boribori.authserver.oauth2.Oauth2Properties;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2TokenResponse;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2RequestUtil {

    private final Oauth2Properties oauth2Properties;

    public Mono<DtoOfOauth2UserProfile> requestAuth(String code, String target){
        Oauth2Properties.DtoOfOauth2Properties properties =
                oauth2Properties.getProperties().get(target);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("grant_type", "authorization_code");
        queryParams.add("client_id", properties.getClientId());
        queryParams.add("redirect_uri", properties.getRedirectUri());
        queryParams.add("code", code);
        queryParams.add("client_secret", properties.getClientSecret());
        return WebClient.builder().baseUrl(properties.getTokenUri()).build()
                .post()
                .uri(uriBuilder ->
                    uriBuilder
                            .queryParams(queryParams)
                            .build()
                )
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(DtoOfOauth2TokenResponse.class)
                .flatMap(response -> getUserProfile(response.getAccessToken(), properties.getUserInfoUri()));
    }

    public Mono<DtoOfOauth2UserProfile> getUserProfile(String accessToken, String userInfoUri){
        WebClient webClient = WebClient.builder().baseUrl(userInfoUri).build();
        return webClient.get()
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(DtoOfOauth2UserProfile.class);
    }
}