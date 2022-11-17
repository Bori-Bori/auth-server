package com.boribori.authserver.member;

import com.boribori.authserver.alarm.Alarm;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.CassandraType;
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


    @Column(value = "alarms")
    private List<Alarm> alarms = new ArrayList<>();

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

    public void addAlarm(Alarm alarm){
        if(this.alarms == null){
            this.alarms = new ArrayList<>();
        }
        this.alarms.add(alarm);
    }
}
