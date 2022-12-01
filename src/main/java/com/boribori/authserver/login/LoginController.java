package com.boribori.authserver.login;

import com.boribori.authserver.member.event.MemberEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/api/login/{target}")
    public Mono<ResponseEntity> login(@RequestParam String code, @PathVariable(name = "target")String target){
        Mono<ResponseEntity> result = loginService.login(code, target)
                .map(response ->
                        ResponseEntity.status(HttpStatus.OK).body(response)
                ).cast(ResponseEntity.class);

        return result;
    }

}
