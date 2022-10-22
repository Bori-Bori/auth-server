package com.boribori.authserver.member;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface MemberRepository extends ReactiveCassandraRepository<Member, String> {
}
