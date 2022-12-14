package com.boribori.authserver.member;

import com.boribori.authserver.exception.NotFoundUserException;
import com.boribori.authserver.member.dto.DtoOfGetMemberInfo;
import com.boribori.authserver.member.dto.DtoOfUpdateMember;
import com.boribori.authserver.member.event.dto.DtoOfGetNotification;
import com.boribori.authserver.notification.Notification;
import com.boribori.authserver.jwt.JwtService;
import com.boribori.authserver.member.dto.DtoOfUpdateNickname;
import com.boribori.authserver.member.event.MemberEventPublisher;
import com.boribori.authserver.member.event.dto.EventOfPublishReplyNotification;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
                        .profile_image(userProfile.getKakaoAccount().getProfile().getProfileImageUrl())
                        .nickname(userProfile.getProperties().getNickname())
                        .build())
                );

    }

    public Mono<DtoOfUpdateNickname> updateNickname(String header, String nickname){
        return jwtService.getUserData(header)
                .flatMap(tokenData -> memberRepository.findById(tokenData.getUserId()))
                .flatMap(memberEntity -> Mono.just(memberEntity.updateNickname(nickname)))
                .flatMap(member -> {
                    memberEventPublisher.sendEventUpdateProfile(member);
                    return memberRepository.save(member);
                })
                .flatMap(updatedMemberEntity ->
                        Mono.just(DtoOfUpdateNickname.builder()
                                .nickname(updatedMemberEntity.getNickname())
                                .build()));

    }

    public void updateAlarm(EventOfPublishReplyNotification event){
        // ???????????? ???????????? user ??????
        memberRepository.findById(event.getCommentUserId())
                .flatMap(v -> {
                    v.addNotification(
                            Notification.builder()
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
                    return Mono.just(null);
                }).subscribe();

    }

    public Flux<DtoOfGetNotification> getNotification(String header){
        return jwtService.getUserData(header)
                .flatMap(tokenData -> memberRepository.findById(tokenData.getUserId()))
                .flatMap(member -> {

                    Mono<List<DtoOfGetNotification>> mono = Mono.just(member.getNotifications().stream()
                            .filter(m -> m.isChecked() == false)
                            .collect(Collectors.toList())
                            .stream()
                            .map(v -> {
                                v.updateIsChecked();
                                return DtoOfGetNotification.of(v);
                            })
                            .collect(Collectors.toList()));
                    memberRepository.insert(member).subscribe();
                    return mono;
                }).flatMapMany(Flux :: fromIterable);

    }

    public Mono<DtoOfGetMemberInfo> getMemberInfo(String header){

        return jwtService.getUserData(header)
                .flatMap(tokenData -> memberRepository.findById(tokenData.getUserId()))
                .flatMap(member -> Mono.just(DtoOfGetMemberInfo.of(member)))
                .switchIfEmpty(Mono.error(new NotFoundUserException("????????? ?????? ??? ????????????.")));

    }

    public Mono<DtoOfUpdateMember> updateMemberImage(String header, String imageUrl){

        return jwtService.getUserData(header)
                .flatMap(tokenData -> memberRepository.findById(tokenData.getUserId()))
                .flatMap(member -> {
                    member.updateMemberImage(imageUrl);
                    return memberRepository.insert(member);
                })
                .flatMap(updatedMember -> {
                    memberEventPublisher.sendEventUpdateProfile(updatedMember);
                    return Mono.just(
                            DtoOfUpdateMember.builder()
                                    .imagePath(updatedMember.getProfile_image())
                                    .build()
                    );
                })
                .switchIfEmpty(Mono.error(new NotFoundUserException("????????? ?????? ??? ????????????.")));
    }
    



}
