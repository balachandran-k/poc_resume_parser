package com.procyon.resume.extract.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: balachandran
 * Date: 20/11/24
 */
public class ResumeFieldExtractor {

    public static String extractEmail(String text) {
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }

    public static String extractPhone(String text) {
        String phoneRegex = "\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }

    public static String extractName(String text) {
        // Simplistic name extraction, usually the first line or first capitalized phrase
        String nameRegex = "^[A-Z][a-z]+( [A-Z][a-z]+)+";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }
}

