package com.quizapp.modes.llmmode.api;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

import com.quizapp.modes.llmmode.helpers.Prompt;

public class GemmaClient {
    private static final String HOST = "http://localhost:11434";

    public static String generateQuiz(String topic , String difficulty) throws Exception {
        OllamaAPI ollamaAPI = new OllamaAPI(HOST);

        ollamaAPI.setRequestTimeoutSeconds(60);

        String promptText = Prompt.buildQuizPrompt(topic , difficulty);

        OllamaResult result = ollamaAPI.generate("gemma4:e2b" , promptText , false , new OptionsBuilder().build());

        return result.getResponse();
    }
}