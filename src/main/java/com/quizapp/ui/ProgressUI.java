package com.quizapp.ui;

public class ProgressUI {

    public static void printQuestionProgress(int current , int total) {
        int width = 30;

        double percent = (double) current / total;
        int filled = (int) (percent * width);

        StringBuilder bar = new StringBuilder();

        for (int i = 0; i < width; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }

        String text =
            Theme.TITLE_TEXT +
            "[" + bar + "] " +
            current + "/" + total +
            Theme.RESET;

        Terminal.printCentered(text);
    }

    public static void printStatLine(String left , String right) {
        String line =
            Theme.MUTED_TEXT +
            left +
            Theme.RESET +
            "   │   " +
            Theme.OPTION_TEXT +
            right +
            Theme.RESET;

        Terminal.printCentered(line);
    }

    public static void printDivider() {
        Terminal.printCentered(
            Theme.BORDER_COLOR +
            "─".repeat(60) +
            Theme.RESET
        );
    }

    public static void printResultCard(
            String title ,
            int score ,
            int total ,
            String extra
    ) {

        Terminal.printCentered(
            Theme.BORDER_COLOR +
            "╭────────────────────────────────────────────╮" +
            Theme.RESET
        );

        Terminal.printCentered(
            Theme.BORDER_COLOR +
            "│" +
            Theme.TITLE_TEXT +
            Terminal.centerLine(title , 44) +
            Theme.BORDER_COLOR +
            "│" +
            Theme.RESET
        );

        Terminal.printCentered(
            Theme.BORDER_COLOR +
            "├────────────────────────────────────────────┤" +
            Theme.RESET
        );

        Terminal.printCentered(
            Theme.BORDER_COLOR +
            "│" +
            Theme.OPTION_TEXT +
            Terminal.centerLine("Score: " + score + " / " + total , 44) +
            Theme.BORDER_COLOR +
            "│" +
            Theme.RESET
        );

        if (extra != null && !extra.isBlank()) {
            Terminal.printCentered(
                Theme.BORDER_COLOR +
                "│" +
                Theme.MUTED_TEXT +
                Terminal.centerLine(extra , 44) +
                Theme.BORDER_COLOR +
                "│" +
                Theme.RESET
            );
        }

        Terminal.printCentered(
            Theme.BORDER_COLOR +
            "╰────────────────────────────────────────────╯" +
            Theme.RESET
        );
    }

    public static void printLiveStats(
            int score ,
            int lives ,
            int question ,
            int total
    ) {

        printDivider();

        printStatLine(
            "Question: " + question + "/" + total ,
            "Score: " + score
        );

        if (lives >= 0) {
            printStatLine(
                "Lives Remaining",
                String.valueOf(lives)
            );
        }

        printDivider();
    }
}