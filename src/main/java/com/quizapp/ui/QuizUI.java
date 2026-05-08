package com.quizapp.ui;

public class QuizUI {
    private static final int WIDTH = 78;

    public static void printQuestionBox(int qNum , String question , String[] options) {
        Terminal.printEmptyLines(1);

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(WIDTH) + "╮" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine("QUESTION " + qNum , WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(WIDTH) + "┤" + Theme.RESET);

        printQuestion(question);

        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(WIDTH) + "┤" + Theme.RESET);

        char optionLetter = 'A';

        for (int i = 0; i < 4; i++) {
            printOption(optionLetter , options[i]);
            optionLetter++;
        }

        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(WIDTH) + "╯" + Theme.RESET);

        Terminal.printEmptyLines(1);
        Terminal.printInline(Theme.QUESTION_TEXT + Theme.BOLD + "> Answer: " + Theme.RESET);
    }

    private static void printQuestion(String question) {
        String[] lines = wrapText(question , WIDTH - 6);

        for (String line : lines) {
            Terminal.print(
                Theme.BORDER_COLOR + "│" +
                Theme.QUESTION_TEXT + Theme.BOLD + Terminal.padRight("  " + line , WIDTH) +
                Theme.BORDER_COLOR + "│" +
                Theme.RESET
            );
        }
    }

    private static void printOption(char letter , String option) {
        String text = letter + ". " + option;
        String[] lines = wrapText(text , WIDTH - 8);

        for (String line : lines) {
            Terminal.print(
                Theme.BORDER_COLOR + "│" +
                Theme.OPTION_TEXT + Terminal.padRight("  " + line , WIDTH) +
                Theme.BORDER_COLOR + "│" +
                Theme.RESET
            );
        }
    }

    private static String[] wrapText(String text , int maxWidth) {
        if (text.length() <= maxWidth) {
            return new String[]{text};
        }

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < text.length()) {
            int end = Math.min(index + maxWidth , text.length());

            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(" " , end);

                if (lastSpace > index) {
                    end = lastSpace;
                }
            }

            result.append(text.substring(index , end).trim()).append("\n");
            index = end;
        }

        return result.toString().split("\\R");
    }

    public static void printFeedback(boolean isCorrect) {
        Terminal.printEmptyLines(1);

        if (isCorrect) {
            Terminal.print(Theme.FEEDBACK_CORRECT + Theme.BOLD + "✓ Correct" + Theme.RESET);
        } else {
            Terminal.print(Theme.FEEDBACK_WRONG + Theme.BOLD + "✗ Wrong" + Theme.RESET);
        }
    }

    public static void printAnswerFeedback(char userAnswer , char correctAnswer) {
        boolean isCorrect = Character.toUpperCase(userAnswer) == Character.toUpperCase(correctAnswer);

        if (isCorrect) {
            printFeedback(true);
        } else {
            printFeedback(false);
            Terminal.print(Theme.MUTED_TEXT + "You chose: " + Character.toUpperCase(userAnswer) + Theme.RESET);
            Terminal.print(Theme.FEEDBACK_CORRECT + "Correct answer: " + Character.toUpperCase(correctAnswer) + Theme.RESET);
        }
    }

    public static void printInfo(String message) {
        Terminal.print(Theme.MUTED_TEXT + message + Theme.RESET);
    }

    public static void printSuccess(String message) {
        Terminal.print(Theme.FEEDBACK_CORRECT + message + Theme.RESET);
    }

    public static void printError(String message) {
        Terminal.print(Theme.FEEDBACK_WRONG + message + Theme.RESET);
    }
}