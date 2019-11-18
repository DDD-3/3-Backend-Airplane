package com.ddd.airplane;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.text.MessageFormat;

import static java.net.InetAddress.getLocalHost;

@RestController
public class HelloWorldController {
    @GetMapping("/")
    public String hello() throws UnknownHostException {
        String address = getLocalHost().getHostAddress();
        return MessageFormat.format("{0} : Hello, World!", address);
    }
}
