package com.boribori.authserver.alarm;

import com.boribori.authserver.member.event.dto.EventOfPublishReplyAlarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public void saveAlarm(EventOfPublishReplyAlarm event){
        Alarm alarm = Alarm.builder()
                .replyId(event.getReplyId())
                .commentId(event.getCommentId())
                .createdAt(event.getCreatedAt())
                .build();

        alarmRepository.save(alarm);
    }

    public void getAlarm(){

    }
}
