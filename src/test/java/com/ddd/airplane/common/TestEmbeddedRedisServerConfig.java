package com.ddd.airplane.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
@Configuration
public class TestEmbeddedRedisServerConfig {
    private static RedisServer redisServer;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @PostConstruct
    public void start() {
        if (redisServer == null) {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
            log.info("EMBEDDED REDIS START : port={}", redisPort);
        }
    }

    @PreDestroy
    public void stop() {
        if (redisServer != null) {
            List<Integer> ports = redisServer.ports();
            redisServer.stop();
            log.info("EMBEDDED REDIS STOP : port={}", ports);
        }
    }
}
