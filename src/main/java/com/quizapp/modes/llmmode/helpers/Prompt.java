package com.quizapp.modes.llmmode.helpers;

public class Prompt{
    public static String buildQuizPrompt(String topic , String difficulty) {
        return """
            Task: Generate a 15 unique random question multiple-choice quiz on %s
            Strict Output Schema:
            question@option1@option2@option3@option4@correctOption

            Mandatory Constraints:
            - Difficulty of the mcqs should be : %s
            - Delimiter Accuracy: Use @ to separate sections
            - Correct Option Format: Final character must be A , B , C , or D
            - Zero Verbosity: No intro , no numbering , no titles
            - Physical Structure: Exactly one question per line
            - Do not repeat the same question , even if the wording is changed
            - Make sure the questions are random and like if asked the same question then it shouldnt give the same set of questions twice
            - The questions and their options should not be too long , take this as an example : Which instrument is used to measure temperature?@Barometer@Thermometer@Hygrometer@Altimeter@B

            For math topics:
            - No LaTeX
            - No symbolic notation
            - No integrals , derivatives or equation-heavy questions
            - Plain ASCII text only
            - Prefer worded arithmetic questions

            Example:
            What is the capital of France?@Lyon@Paris@Marseille@Nice@B
            """.formatted(topic , difficulty).stripIndent();
    }
}