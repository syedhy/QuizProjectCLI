package com.quizapp.ui;
public class QuizUI {
    public static void printQuestionBox(int qNum , String question , String[] options) {
        int width = 76;

        Terminal.printCentered(Theme.BORDER_COLOR + "╭" + "─".repeat(width) + "╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.padRight("  Question " + qNum , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(width) + "┤" + Theme.RESET);

        animatedWrappedLine(question , width , Theme.QUESTION_TEXT + Theme.BOLD);
        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(width) + "┤" + Theme.RESET);

        char optionLetter = 'A';

        for (int i = 0; i < 4; i++) {
            String optionText = "[" + optionLetter + "] " + options[i];
            printWrappedLine(optionText , width , Theme.OPTION_TEXT);
            optionLetter++;
        }

        Terminal.printCentered(Theme.BORDER_COLOR + "╰" + "─".repeat(width) + "╯" + Theme.RESET);

        System.out.print("\n" + Theme.QUESTION_TEXT + Theme.BOLD + "> Answer: " + Theme.RESET);
    }

    private static void printWrappedLine(String text , int width , String color) {
        int contentWidth = width - 4;

        if (text.length() <= contentWidth) {
            Terminal.printCentered(
                Theme.BORDER_COLOR + "│" +
                color + Terminal.padRight("  " + text , width) +
                Theme.BORDER_COLOR + "│" +
                Theme.RESET
            );

            return;
        }

        int index = 0;

        while (index < text.length()) {
            int end = Math.min(index + contentWidth , text.length());

            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(" " , end);

                if (lastSpace > index) {
                    end = lastSpace;
                }
            }

            String part = text.substring(index , end).trim();

            Terminal.printCentered(
                Theme.BORDER_COLOR + "│" +
                color + Terminal.padRight("  " + part , width) +
                Theme.BORDER_COLOR + "│" +
                Theme.RESET
            );

            index = end;
        }
    }

    private static void animatedWrappedLine(String text, int width, String color) {
        int contentWidth = width - 4;

        if (text.length() <= contentWidth) {

            String finalLine = Theme.BORDER_COLOR + "│" +
                    color + Terminal.padRight("  " + text, width) +
                    Theme.BORDER_COLOR + "│" +
                    Theme.RESET;

            int terminalWidth = Terminal.getWidth();
            int padding = Math.max(0, (terminalWidth - Terminal.removeAnsi(finalLine).length()) / 2);

            System.out.print(" ".repeat(padding));

            try {
                for (char c : finalLine.toCharArray()) {
                    System.out.print(c);
                    System.out.flush();
                    Thread.sleep(2);
                }

                System.out.println();

            } catch (Exception e) {
                System.out.println(finalLine);
            }

            return;
        }

        printWrappedLine(text, width, color);
    }

    public static void printFeedback(boolean isCorrect) {
        if (isCorrect) {
            System.out.println();
            Terminal.printCentered(Theme.FEEDBACK_CORRECT + Theme.BOLD + "[ CORRECT ]" + Theme.RESET);
        } else {
            System.out.println();
            Terminal.printCentered(Theme.FEEDBACK_WRONG + Theme.BOLD + "[ WRONG ]" + Theme.RESET);
        }
    }

    public static void printAnswerFeedback(char userAnswer , char correctAnswer) {
        boolean isCorrect = Character.toUpperCase(userAnswer) == Character.toUpperCase(correctAnswer);

        if (isCorrect) {
            printFeedback(true);
        } else {
            printFeedback(false);
            Terminal.printCentered(Theme.MUTED_TEXT + "Your Answer: " + Character.toUpperCase(userAnswer) + Theme.RESET);
            Terminal.printCentered(Theme.FEEDBACK_CORRECT + "Correct Answer: " + Character.toUpperCase(correctAnswer) + Theme.RESET);
        }
    }

    public static void printInfo(String message) {
        Terminal.printCentered(Theme.MUTED_TEXT + message + Theme.RESET);
    }

    public static void printSuccess(String message) {
        Terminal.printCentered(Theme.FEEDBACK_CORRECT + message + Theme.RESET);
    }

    public static void printError(String message) {
        Terminal.printCentered(Theme.FEEDBACK_WRONG + message + Theme.RESET);
    }
}