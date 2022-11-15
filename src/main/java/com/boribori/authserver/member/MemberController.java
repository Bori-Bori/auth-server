package com.boribori.authserver.member;

import com.boribori.authserver.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public Mono<ResponseEntity<Response>> updateNickname(@RequestHeader("Authorization") String token, @RequestBody String nickname){

        return memberService.updateNickname(token, nickname)
                .flatMap(v -> Mono.just(
                        ResponseEntity.ok(Response.builder()
                                .status(Response.Status.builder()
                                        .msg("성공적으로 변경되었습니다.").build())
                                .content(v)
                                .build()
                        )

                ));
    }
}
