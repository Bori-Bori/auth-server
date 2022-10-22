package com.boribori.authserver.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberRepository memberRepository;

    @PostMapping("/test")
    public Mono<Member> createBoard(){

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        return memberRepository.save(
                Member.builder()
                        .oauth2Id("123123")
                        .nickname("ㅌㅔ스트용아이디")
                        .list(list)
                        .build()
        );
    }
}
