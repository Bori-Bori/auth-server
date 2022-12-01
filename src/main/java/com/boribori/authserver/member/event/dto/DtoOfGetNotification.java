package com.boribori.authserver.member.event.dto;

import com.boribori.authserver.notification.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@Getter
public class DtoOfGetNotification {


    @Builder
    private DtoOfGetNotification(Notification notification){
        this.boardId = notification.getBoardId();
        this.commentId = notification.getCommentId();
        this.replyId = notification.getReplyId();
        this.replyContent = notification.getReplyContent();
        this.commentContent = notification.getCommentContent();
        this.replyUserNickname = notification.getReplyUserNickname();
        this.page = notification.getPage();
        this.createdAt = notification.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private String boardId;
    private String commentId;
    private String replyId;

    private String replyContent;
    private String commentContent;

    private String replyUserNickname;
    private int page;
    private String createdAt;

    public static DtoOfGetNotification of(Notification notification){
        return DtoOfGetNotification.builder().notification(notification).build();
    }

}
