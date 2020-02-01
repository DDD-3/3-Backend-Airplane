package com.ddd.airplane.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNicknameGenerator {
    private static final String DELIMITER = " ";

    private List<String> nouns = new ArrayList<>();
    private List<String> adjectives = new ArrayList<>();
    private Random random = new Random();

    public RandomNicknameGenerator() {
        try {
            nouns = load("file/nouns.txt");
            adjectives = load("file/adjectives.txt");
        } catch (IOException e) {
            //
        }
    }

    public String generate() {
        int randomNounIndex = random.nextInt(nouns.size());
        int randomAdjectiveIndex = random.nextInt(adjectives.size());

        return adjectives.get(randomAdjectiveIndex) + DELIMITER + nouns.get(randomNounIndex);
    }

    private List<String> load(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        Path path = Paths.get(resource.getURI());
        return Files.readAllLines(path);
    }
}
