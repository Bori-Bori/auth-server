package com.boribori.authserver.member;

import com.boribori.authserver.common.Response;
import com.boribori.authserver.member.dto.DtoOfRequestUpdateImage;
import com.boribori.authserver.member.dto.DtoOfUpdateNickname;
import com.boribori.authserver.member.event.dto.DtoOfGetNotification;
import com.boribori.authserver.notification.exception.NotFoundNotificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/api/member/nickname")
    public Mono<ResponseEntity<Response>> updateNickname(@RequestHeader("Authorization") String token,  @RequestBody DtoOfUpdateNickname dtoOfUpdateNickname){
        System.out.println("~~");
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

    @GetMapping("/api/member/notification")
    public Flux<ServerResponse> getUserNotification(@RequestHeader("Authorization") String authorization){

        return memberService.getNotification(authorization)
                .switchIfEmpty(v -> {
                    throw new NotFoundNotificationException("알림이 없습니다.");
                })
                .flatMap(v -> ok().body(BodyInserters.fromValue(v)));

    }

    @GetMapping("/api/member")
    public Mono<ResponseEntity<Response>> getMemberInfo(@RequestHeader("Authorization") String header){
        return memberService.getMemberInfo(header)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(
                        Response.builder()
                                .status(Response.Status.builder()
                                        .msg("성공적으로 조회되었습니다.").build())
                                .content(dto)
                                .build()
                )));
    }

    @PostMapping("/api/member/image")
    public Mono<ResponseEntity<Response>> updateMemberInfo(@RequestHeader("Authorization") String header, @RequestBody DtoOfRequestUpdateImage dtoOfRequestUpdateImage){

        return memberService.updateMemberImage(header, dtoOfRequestUpdateImage.getImagePath())
                .flatMap(dto -> Mono.just(ResponseEntity.ok(
                        Response.builder()
                                .status(Response.Status.builder()
                                        .msg("성공적으로 수정되었습니다.").build())
                                .content(dto)
                                .build()
                )));

    }

}
