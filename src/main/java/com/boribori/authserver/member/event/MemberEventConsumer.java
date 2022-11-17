package com.boribori.authserver.member.event;

import com.boribori.authserver.member.MemberService;
import com.boribori.authserver.member.event.dto.EventOfPublishReplyAlarm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberEventConsumer {

    private final MemberService memberService;

    @KafkaListener(topics = "reply", groupId = "foo")
    public void consume(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        EventOfPublishReplyAlarm dto = objectMapper.readValue(json, EventOfPublishReplyAlarm.class);

        memberService.updateAlarm(dto);

    }
}
