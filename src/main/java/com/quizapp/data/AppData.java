package com.quizapp.data;

import java.nio.file.Files;
import java.nio.file.Path;

public class AppData {
    public static final Path GENERATED_DATA_DIR = Path.of("generated_data");
    public static final Path DB_FILE = GENERATED_DATA_DIR.resolve("quiz.db");
    public static final Path LLM_QUESTIONS_FILE = GENERATED_DATA_DIR.resolve("Questions.txt");

    public static void ensureGeneratedDataFolder() {
        try {
            if (!Files.exists(GENERATED_DATA_DIR)) {
                Files.createDirectories(GENERATED_DATA_DIR);
            }
        } catch (Exception e) {
            System.out.println("Error creating generated_data folder: " + e.getMessage());
        }
    }
}