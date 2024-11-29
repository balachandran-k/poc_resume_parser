package com.procyon.resume.extract.util;

import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: balachandran
 * Date: 20/11/24
 */
public class SkillExtractor {

    private static final List<String> SKILL_SET = List.of("Java", "Spring Boot", "Python", "Machine Learning", "Docker");

    public static List<String> extractSkills(String text) {
        String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(text);
        return SKILL_SET.stream()
                .filter(skill -> {
                    for (String token : tokens) {
                        if (token.equalsIgnoreCase(skill)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}

