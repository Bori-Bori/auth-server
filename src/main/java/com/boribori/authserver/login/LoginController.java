package com.boribori.authserver.login;

import com.boribori.authserver.member.event.MemberEventPublisher;
import com.boribori.authserver.member.event.dto.DtoOfUpdateNicknameEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    private final MemberEventPublisher memberEventPublisher;

    @GetMapping("/api/login/{target}")
    public Mono<ResponseEntity> login(@RequestParam String code, @PathVariable(name = "target")String target){
        Mono<ResponseEntity> result = loginService.login(code, target)
                .map(response ->
                        ResponseEntity.status(HttpStatus.OK).body(response)
                ).cast(ResponseEntity.class);

        return result;
    }

}
