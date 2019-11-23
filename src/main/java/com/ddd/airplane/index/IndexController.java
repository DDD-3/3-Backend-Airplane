package com.ddd.airplane.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.text.MessageFormat;

import static java.net.InetAddress.getLocalHost;

@Slf4j
@RestController
public class IndexController {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @GetMapping("/")
    public String index() throws UnknownHostException {
        log.info("datasourceUrl : {}", datasourceUrl);
        String address = getLocalHost().getHostAddress();
        return MessageFormat.format("{0} : Hello, World!", address);
    }
}
