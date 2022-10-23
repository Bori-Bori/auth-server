package com.boribori.authserver.oauth2.util;

import com.boribori.authserver.oauth2.dto.DtoOfOauth2TokenResponse;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import com.datastax.oss.protocol.internal.SegmentCodec;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2RequestUtil {

    private final WebClient webClient;

    public OAuth2RequestUtil(){
        this.webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com").build();
    }

    public Mono<DtoOfOauth2UserProfile> requestAuth(String code){
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        queryParams.add("grant_type", "authorization_code");
        queryParams.add("client_id", "1e9f53ef5d1b41846fe3d2e9e47a0e51");
        queryParams.add("redirect_uri", "http://localhost:8080/redirect/oauth");
        queryParams.add("code", code);
        queryParams.add("client_secret", "rcgeTZx1l9QBCMGPNY6TwUnjbU8dS5FV");
        return webClient.post()
                .uri(uriBuilder ->
                    uriBuilder.path("/oauth/token")
                            .queryParams(queryParams)
                            .build()
                )
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(DtoOfOauth2TokenResponse.class)
                .flatMap(response -> getUserProfile(response.getAccessToken()));

    }

    public Mono<DtoOfOauth2UserProfile> getUserProfile(String accessToken){
        WebClient webClient1 = WebClient.builder().baseUrl("https://kapi.kakao.com").build();
        return webClient1.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/user/me")
                        .build())
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(DtoOfOauth2UserProfile.class);

    }
}
