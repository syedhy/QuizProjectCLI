package com.quizapp.ui;

public class Screen {

    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                new ProcessBuilder("cmd" , "/c" , "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                new ProcessBuilder("clear")
                    .inheritIO()
                    .start()
                    .waitFor();
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void printSectionTitle(String title) {
        Terminal.print(Theme.BORDER_COLOR + "╭────────────────────────────────────╮" + Theme.RESET);

        Terminal.print(
            Theme.BORDER_COLOR +
            "│" +
            Theme.TITLE_TEXT +
            Theme.BOLD +
            Terminal.centerLine(title , 36) +
            Theme.BORDER_COLOR +
            "│" +
            Theme.RESET
        );

        Terminal.print(Theme.BORDER_COLOR + "╰────────────────────────────────────╯" + Theme.RESET);
    }

    public static void printSeparator(int width) {
        Terminal.print(
            Theme.BORDER_COLOR +
            "─".repeat(width) +
            Theme.RESET
        );
    }
}