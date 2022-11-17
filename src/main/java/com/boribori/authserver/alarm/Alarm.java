package com.boribori.authserver.alarm;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType
public class Alarm {
    private String replyUserNickname;

    private String commentId;
    private String commentContent;

    private String replyId;
    private String replyContent;


    private String boardId;

    private int page;

    private boolean isChecked = false;

    private LocalDateTime createdAt;

    private void updateIsChecked(){
        this.isChecked = true;
    }
}
