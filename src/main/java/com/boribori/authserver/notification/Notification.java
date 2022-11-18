package com.boribori.authserver.notification;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType
public class Notification {
    private String replyUserNickname;

    private String commentId;
    private String commentContent;

    private String replyId;
    private String replyContent;


    private String boardId;

    private int page;

    private boolean isChecked = false;

    private LocalDateTime createdAt;

    public void updateIsChecked(){
        this.isChecked = true;
    }
}
