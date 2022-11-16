package com.boribori.authserver.member.event;

import com.boribori.authserver.member.Member;
import com.boribori.authserver.member.event.dto.DtoOfUpdateNicknameEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberEventPublisher {

    public static final String TOPIC = "nickname";
    private final KafkaTemplate<String, String> kafkaTemplate;

//    public void sendEventUpdateNickname(Member memberEntity){
//        DtoOfUpdateNicknameEvent eventDto = DtoOfUpdateNicknameEvent.builder()
//                .id(memberEntity.getId())
//                .nickname(memberEntity.getNickname())
//                .build();
//        this.kafkaTemplate.send(TOPIC, eventDto);
//
//
//    }

    public void sendEventUpdateNickname(Member memberEntity){

        DtoOfUpdateNicknameEvent eventDto = DtoOfUpdateNicknameEvent.builder()
                .id(memberEntity.getId())
                .nickname(memberEntity.getNickname())
                .build();
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(eventDto);
            this.kafkaTemplate.send(TOPIC, json);
        }catch (Exception e){
            throw new RuntimeException();
        }



    }
}
