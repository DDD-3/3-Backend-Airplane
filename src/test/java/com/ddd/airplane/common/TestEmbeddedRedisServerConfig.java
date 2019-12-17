package com.ddd.airplane.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Configuration
public class TestEmbeddedRedisServerConfig {
    @Value("${spring.redis.port}")
    private Integer redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void start() throws IOException {
        if (!isRedisRunning(redisPort)) {
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

    private boolean isRedisRunning(int port) throws IOException {
        return isRunning(executeGrepProcessCommand(port));
    }

    private int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN | grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        log.info("GREP PROCESS START");
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                log.info("GREP PROCESS RESULT : {}", line);
                pidInfo.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("GREP PROCESS END");

        return !StringUtils.isEmpty(pidInfo.toString());
    }
}
