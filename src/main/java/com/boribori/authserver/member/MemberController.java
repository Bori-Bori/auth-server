package com.boribori.authserver.member;

import com.boribori.authserver.common.Response;
import com.boribori.authserver.member.dto.DtoOfUpdateNickname;
import com.boribori.authserver.member.event.MemberEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/api/member/nickname")
    public Mono<ResponseEntity<Response>> updateNickname(@RequestHeader("Authorization") String token, @RequestBody DtoOfUpdateNickname dtoOfUpdateNickname){
        return memberService.updateNickname(token, dtoOfUpdateNickname.getNickname())
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
