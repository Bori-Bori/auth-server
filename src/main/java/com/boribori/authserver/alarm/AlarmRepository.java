package com.boribori.authserver.alarm;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface AlarmRepository extends ReactiveCassandraRepository<Alarm, String> {
}
