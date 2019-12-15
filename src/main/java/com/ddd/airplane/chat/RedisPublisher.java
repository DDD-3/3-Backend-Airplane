package com.ddd.airplane.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    void publish(ChannelTopic topic, ChatMessage chatMessage) {
        redisTemplate.convertAndSend(topic.getTopic(), chatMessage);
    }
}
