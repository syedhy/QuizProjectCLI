package com.quizapp.ui;

public class ProgressUI {
    private static final int WIDTH = 78;

    public static void printQuizDashboard(int current , int total , String label , String value) {
        int barWidth = 28;

        double percent = (double) current / total;
        int filled = (int) (percent * barWidth);

        String bar = buildBar(filled , barWidth);

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(WIDTH) + "╮" + Theme.RESET);

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine("QUIZ STATUS" , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(WIDTH) + "┤" + Theme.RESET);

        String line1 = "Progress  " + bar + "  " + current + "/" + total;
        String line2 = label + ": " + value;

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.OPTION_TEXT + Terminal.padRight("  " + line1 , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.MUTED_TEXT + Terminal.padRight("  " + line2 , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(WIDTH) + "╯" + Theme.RESET);
    }

    public static void printQuizDashboard(int current , int total , String label1 , String value1 , String label2 , String value2) {
        int barWidth = 28;

        double percent = (double) current / total;
        int filled = (int) (percent * barWidth);

        String bar = buildBar(filled , barWidth);

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(WIDTH) + "╮" + Theme.RESET);

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine("QUIZ STATUS" , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(WIDTH) + "┤" + Theme.RESET);

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.OPTION_TEXT + Terminal.padRight("  Progress  " + bar + "  " + current + "/" + total , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.MUTED_TEXT + Terminal.padRight("  " + label1 + ": " + value1 , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.MUTED_TEXT + Terminal.padRight("  " + label2 + ": " + value2 , WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );

        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(WIDTH) + "╯" + Theme.RESET);
    }

    public static void printQuestionProgress(int current , int total) {
        printQuizDashboard(current , total , "Question" , current + "/" + total);
    }

    public static void printStatLine(String left , String right) {
        printQuizDashboard(1 , 1 , left , right);
    }

    public static void printLiveStats(int score , int lives , int question , int total) {
        printQuizDashboard(
            question ,
            total ,
            "Score" ,
            String.valueOf(score) ,
            "Lives" ,
            String.valueOf(lives)
        );
    }

    public static void printResultCard(String title , int score , int total , String extra) {
        int width = 58;

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(width) + "╮" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine(title , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(width) + "┤" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.OPTION_TEXT + Terminal.centerLine("Score: " + score + " / " + total , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);

        if (extra != null && !extra.isBlank()) {
            Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine(extra , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        }

        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(width) + "╯" + Theme.RESET);
    }

    private static String buildBar(int filled , int width) {
        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < width; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }

        bar.append("]");

        return bar.toString();
    }
}