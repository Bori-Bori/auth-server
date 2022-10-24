package com.boribori.authserver.member;

import com.boribori.authserver.oauth2.dto.DtoOfOauth2UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class MemberService {


    private final MemberRepository memberRepository;



    public Mono<Member> login(Mono<DtoOfOauth2UserProfile> userProfileMono){

        return userProfileMono.flatMap(
                userProfile -> {
                    System.out.println("aaaaa = " + userProfile.getProperties().getNickname());

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

}
