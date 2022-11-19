package com.boribori.authserver.member;

import com.boribori.authserver.notification.Notification;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("member")
public class Member {
    @PrimaryKey
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Column(value = "nickname")
    private String nickname;

    @Column(value = "profile_image")
    private String profile_image = null;


    @Column(value = "notifications")
    private List<Notification> notifications = new ArrayList<>();

    public Member updateNickname(String nickname){
        if (nickname == null){
            return this;
        }
        if(nickname.equals("")){
            return this;
        }
        this.nickname = nickname;
        return this;
    }

    public void addNotification(Notification notification){
        if(this.notifications == null){
            this.notifications = new ArrayList<>();
        }
        this.notifications.add(notification);
    }
}
