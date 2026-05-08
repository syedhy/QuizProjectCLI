package com.quizapp.modes.llmmode.helpers;

import java.util.ArrayList;
import java.util.List;

public class QuizParser {
    public static List<String> extractValidQuestions(String raw) {
        List<String> validQuestions = new ArrayList<>();

        if (raw == null || raw.isEmpty()) return validQuestions;

        String[] lines = raw.split("\\R");

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty() || !line.contains("@")) continue;

            String[] parts = line.split("@");

            if (parts.length >= 6) {
                String answer = parts[parts.length - 1].trim().toUpperCase();

                if (answer.matches("A|B|C|D")) {
                    validQuestions.add(line);
                }
            }

            if (validQuestions.size() == 10) break;
        }

        return validQuestions;
    }
}