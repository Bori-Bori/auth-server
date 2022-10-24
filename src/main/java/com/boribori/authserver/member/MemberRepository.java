package com.boribori.authserver.member;

import jnr.a64asm.Mem;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCassandraRepository<Member, String> {


    Mono<Member> findByOauth2Id(Long oauth2Id);
}
