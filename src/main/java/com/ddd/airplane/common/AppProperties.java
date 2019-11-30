package com.ddd.airplane.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {
    @Value("${client.id}") private String clientId;
    @Value("${client.secret}") private String clientSecret;
}
