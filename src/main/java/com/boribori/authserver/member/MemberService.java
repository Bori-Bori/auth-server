package com.boribori.authserver.member;

import com.boribori.authserver.jwt.JwtService;
import com.boribori.authserver.jwt.dto.TokenData;
import com.boribori.authserver.member.dto.DtoOfUpdateNickname;
import com.boribori.authserver.member.event.MemberEventPublisher;
import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
                                 saveMemberEntity(userProfileMono))
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
                    //memberEventPublisher.sendEventUpdateNickname(member);
                            return Mono.just(member);
                })
                .flatMap(updatedMemberEntity ->
                        Mono.just(DtoOfUpdateNickname.builder()
                                .id(updatedMemberEntity.getId())
                                .nickname(updatedMemberEntity.getNickname())
                                .build()));

        //find by ㅎㅐ서 entity 찾기
        //닉네임 update
        // event 보내기
        //수정 완료 보내기
    }

}
