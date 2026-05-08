package com.quizapp.modes.llmmode.api;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import com.quizapp.modes.llmmode.helpers.Prompt;

public class GeminiClient {
    private static final String API_KEY = System.getenv("GEMINI_API_KEY");

    public static String generateQuiz(String topic , String difficulty) throws Exception {
        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY environment variable is not set.");
        }

        Client client = Client.builder().apiKey(API_KEY).build();

        String promptText = Prompt.buildQuizPrompt(topic , difficulty);

        GenerateContentResponse response = client.models.generateContent("gemini-3-flash-preview" , promptText , null);

        return response.text();
    }
}