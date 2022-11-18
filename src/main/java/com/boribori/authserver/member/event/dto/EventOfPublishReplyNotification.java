package com.boribori.authserver.member.event.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventOfPublishReplyNotification {

    private String replyUserNickname;
    private String commentUserId;
    private String commentId;
    private String commentContent;


    private String replyId;
    private String replyContent;

    private String boardId;

    private int page;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
}
