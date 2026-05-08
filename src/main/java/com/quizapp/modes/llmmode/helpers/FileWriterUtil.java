package com.quizapp.modes.llmmode.helpers;

import java.nio.file.Files;
import java.util.List;

import com.quizapp.data.AppData;

public class FileWriterUtil {
    public static void writeQuestions(List<String> questions) {
        try {
            AppData.ensureGeneratedDataFolder();

            Files.write(AppData.LLM_QUESTIONS_FILE , questions);
        } catch (Exception e) {
            System.out.println("Error saving questions to disk: " + e.getMessage());
        }
    }
}