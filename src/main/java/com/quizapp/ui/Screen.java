package com.quizapp.ui;

public class Screen {
    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                new ProcessBuilder("cmd" , "/c" , "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
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

    public static String padRight(String s , int n) {
        return Terminal.padRight(s , n);
    }

    public static void smallHeader(String title , String subtitle) {
        Terminal.printCentered(Theme.BORDER_COLOR + "╭────────────────────────────────────────────╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine(title , 44) + Theme.BORDER_COLOR + "│" + Theme.RESET);

        if (subtitle != null && !subtitle.isBlank()) {
            Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine(subtitle , 44) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        }

        Terminal.printCentered(Theme.BORDER_COLOR + "╰────────────────────────────────────────────╯" + Theme.RESET);
    }
}