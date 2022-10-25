package com.boribori.authserver.jwt;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface RefreshTokenRepository extends ReactiveCassandraRepository<RefreshToken, String> {
}
