package com.ddd.airplane.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RandomNicknameGeneratorTest {
    private RandomNicknameGenerator generator = new RandomNicknameGenerator();

    @Test
    public void generate() {
        log.info(generator.generate());
        log.info(generator.generate());
        log.info(generator.generate());
        log.info(generator.generate());
        log.info(generator.generate());
    }
}