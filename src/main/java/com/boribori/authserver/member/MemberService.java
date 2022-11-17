package com.boribori.authserver.member;

import com.boribori.authserver.alarm.Alarm;
import com.boribori.authserver.jwt.JwtService;
import com.boribori.authserver.jwt.dto.TokenData;
import com.boribori.authserver.member.dto.DtoOfUpdateNickname;
import com.boribori.authserver.member.event.MemberEventPublisher;
import com.boribori.authserver.member.event.dto.EventOfPublishReplyAlarm;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberEventPublisher memberEventPublisher;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;


    public Mono<Member> login(Mono<DtoOfOauth2UserProfile> userProfileMono){

        return userProfileMono.flatMap(
                userProfile -> {
                    return getMemberEntity(userProfile.getId())
                            .switchIfEmpty(Mono.defer(() ->
                            {
                                return saveMemberEntity(userProfileMono);
                            })
                            );
                }
        );

    }

    public Mono<Member> getMemberEntity(Long oauth2Id){
        return memberRepository.findById(oauth2Id.toString());
    }

    public Mono<Member> saveMemberEntity(Mono<DtoOfOauth2UserProfile> userProfileMono){
        return userProfileMono.flatMap(
                userProfile -> memberRepository.save(
                Member.builder()
                        .id(userProfile.getId().toString())
                        .nickname(userProfile.getProperties().getNickname())
                        .build())
                );

    }

    public Mono<DtoOfUpdateNickname> updateNickname(String header, String nickname){
        return jwtService.getUserData(header)
                .flatMap(tokenData -> memberRepository.findById(tokenData.getUserId()))
                .flatMap(memberEntity -> Mono.just(memberEntity.updateNickname(nickname)))
                .flatMap(member -> {
                    memberEventPublisher.sendEventUpdateNickname(member);
                    return memberRepository.save(member);
                })
                .flatMap(updatedMemberEntity ->
                        Mono.just(DtoOfUpdateNickname.builder()
                                .id(updatedMemberEntity.getId())
                                .nickname(updatedMemberEntity.getNickname())
                                .build()));

    }

    public void updateAlarm(EventOfPublishReplyAlarm event){
        // 이벤트에 해당하는 user 조회
        memberRepository.findById(event.getCommentId())
                .flatMap(v -> {
                    v.addAlarm(
                            Alarm.builder()
                                    .replyUserNickname(event.getReplyUserNickname())
                                    .commentId(event.getCommentId())
                                    .commentContent(event.getCommentContent())
                                    .replyId(event.getReplyId())
                                    .replyContent(event.getReplyContent())
                                    .boardId(event.getBoardId())
                                    .page(event.getPage())
                                    .isChecked(false)
                                    .createdAt(event.getCreatedAt())
                                    .build()
                    );
                    memberRepository.insert(v).subscribe();
                    return null;
                }).subscribe();

    }

    public void getEvent(){
        // 이벤트 조회
        // 조회된 이벤트 식별자 변경
    }



}
