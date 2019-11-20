package com.ddd.airplane;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static java.net.InetAddress.getLocalHost;

@RestController
public class DummyController {
    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String index() throws UnknownHostException {
        String address = getLocalHost().getHostAddress();
        return MessageFormat.format("{0} : Hello, World!", address);
    }

    @GetMapping("/v1/dummies")
    public List<String> getDummies() {
        return this.jdbcTemplate.queryForList("SELECT 1 FROM dual").stream()
                .map(m -> m.values().toString())
                .collect(Collectors.toList());
    }

    public DummyController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
