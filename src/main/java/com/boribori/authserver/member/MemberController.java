package com.boribori.authserver.member;

import com.boribori.authserver.common.Response;
import com.boribori.authserver.member.dto.DtoOfRequestUpdateImage;
import com.boribori.authserver.member.dto.DtoOfUpdateNickname;
import com.boribori.authserver.member.event.dto.DtoOfGetNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/api/member/notification")
    public Flux<DtoOfGetNotification> getUserNotification(@RequestHeader("Authorization") String authorization){
        // 동기 이슈 & 비동기 이슈

        // 아래 로직은 동기로 흐름, 성능 이슈 있을 수 있음

        //fixme 비동기 코드로 변경해야함 ex) Flux<ResponseEntity<Response<DtoOfGetNotification>>>
//        return memberService.getNotification(authorization)
//                .flatMap(v -> {
//                    Response response = Response.builder()
//                            .status(Response.Status.builder()
//                                    .msg("성공적으로 조회되었습니다.")
//                                    .build())
//                            .content(v)
//                            .build();
//                    ResponseEntity<Response> responseEntity = ResponseEntity.ok(
//                        response
//                    );
//
//                    return Flux.just(responseEntity);
//                });

        return memberService.getNotification(authorization);

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
