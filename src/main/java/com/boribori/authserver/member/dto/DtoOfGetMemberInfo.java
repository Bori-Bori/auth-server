package com.boribori.authserver.member.dto;


import com.boribori.authserver.member.Member;
import com.boribori.authserver.member.event.dto.DtoOfGetNotification;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DtoOfGetMemberInfo {

    private String nickname;
    private String id;
    private String profileImage;

    @Builder(access = AccessLevel.PRIVATE)
    private DtoOfGetMemberInfo(String nickname, String id, String profileImage){
        this.nickname = nickname;
        this.id = id;
        this.profileImage = profileImage;
    }

    public static DtoOfGetMemberInfo of(Member member){
        return DtoOfGetMemberInfo
                .builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImage(member.getProfile_image())
                .build();
    }
}
