package com.quizapp.ui;

import java.util.Scanner;

public class MenuUI {
    private static final int MENU_WIDTH = 52;
    private static final int DESCRIPTION_WIDTH = 76;

    public static void printMainMenu() {
        Terminal.printCentered(Theme.BORDER_COLOR + "╭" + "─".repeat(MENU_WIDTH) + "╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.BOLD + Theme.TITLE_TEXT + Terminal.centerLine("QUIZ PROJECT" , MENU_WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(MENU_WIDTH) + "┤" + Theme.RESET);

        printMenuOption("1" , "Timed Mode" , "Race the clock");
        printMenuOption("2" , "Survival Mode" , "Three lives only");
        printMenuOption("3" , "Sudden Death" , "One mistake ends it");
        printMenuOption("4" , "Player Vs Player" , "Two player local battle");
        printMenuOption("5" , "LLM Mode" , "AI generated quiz");
        printMenuOption("6" , "ELO Mode" , "Ranked quiz mode");
        printMenuOption("7" , "Dashboard" , "Open web stats");
        printMenuOption("8" , "Exit" , "Close the app");

        Terminal.printCentered(Theme.BORDER_COLOR + "╰" + "─".repeat(MENU_WIDTH) + "╯" + Theme.RESET);
        Terminal.printCentered(Theme.MUTED_TEXT + "Choose an option by typing its number" + Theme.RESET);
        System.out.print("\n" + Theme.TITLE_TEXT + "> Select mode: " + Theme.RESET);
    }

    private static void printMenuOption(String number , String title , String description) {
        String left = "  [" + number + "] " + title;
        String right = description;

        int spacing = MENU_WIDTH - Terminal.removeAnsi(left).length() - Terminal.removeAnsi(right).length();

        if (spacing < 1) {
            spacing = 1;
        }

        String line = left + " ".repeat(spacing) + Theme.MUTED_TEXT + right + Theme.OPTION_TEXT;

        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.OPTION_TEXT + line + Theme.BORDER_COLOR + "│" + Theme.RESET);
    }

    public static void printSubjectMenu() {
        Terminal.printCentered(Theme.BORDER_COLOR + "╭" + "─".repeat(MENU_WIDTH) + "╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.BOLD + Theme.TITLE_TEXT + Terminal.centerLine("SELECT SUBJECT" , MENU_WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(MENU_WIDTH) + "┤" + Theme.RESET);

        printMenuOption("1" , "General Knowledge" , "Mixed trivia");
        printMenuOption("2" , "Math" , "Numbers and logic");
        printMenuOption("3" , "Science" , "Basic science");

        Terminal.printCentered(Theme.BORDER_COLOR + "╰" + "─".repeat(MENU_WIDTH) + "╯" + Theme.RESET);
        System.out.print("\n" + Theme.TITLE_TEXT + "> Choice: " + Theme.RESET);
    }

    public static void printDifficultyMenu() {
        Terminal.printCentered(Theme.BORDER_COLOR + "╭" + "─".repeat(MENU_WIDTH) + "╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.BOLD + Theme.TITLE_TEXT + Terminal.centerLine("SELECT DIFFICULTY" , MENU_WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(MENU_WIDTH) + "┤" + Theme.RESET);

        printMenuOption("1" , "Easy" , "Warm up");
        printMenuOption("2" , "Medium" , "Balanced");
        printMenuOption("3" , "Hard" , "Challenge");

        Terminal.printCentered(Theme.BORDER_COLOR + "╰" + "─".repeat(MENU_WIDTH) + "╯" + Theme.RESET);
        System.out.print("\n" + Theme.TITLE_TEXT + "> Choice: " + Theme.RESET);
    }

    public static void printModeDescription(String modeName , String description , String rules) {
        Screen.clear();

        Art.printModeBanner(modeName);

        Terminal.printEmptyLines(1);

        Terminal.printCentered(Theme.BORDER_COLOR + "╭" + "─".repeat(DESCRIPTION_WIDTH) + "╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.BOLD + Theme.TITLE_TEXT + Terminal.centerLine(modeName.toUpperCase() , DESCRIPTION_WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(DESCRIPTION_WIDTH) + "┤" + Theme.RESET);

        printDescriptionLine(description);

        Terminal.printCentered(Theme.BORDER_COLOR + "├" + "─".repeat(DESCRIPTION_WIDTH) + "┤" + Theme.RESET);
        printDescriptionLine("Rules");

        String[] rulesArray = rules.split("\n");

        for (String rule : rulesArray) {
            printDescriptionLine("• " + rule.trim());
        }

        Terminal.printCentered(Theme.BORDER_COLOR + "╰" + "─".repeat(DESCRIPTION_WIDTH) + "╯" + Theme.RESET);
    }

    private static void printDescriptionLine(String text) {
        Terminal.printCentered(
            Theme.BORDER_COLOR + "│" +
            Theme.OPTION_TEXT + Terminal.padRight("  " + text , DESCRIPTION_WIDTH) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );
    }

    public static void pressEnterToContinue(Scanner sc) {
        System.out.print("\n" + Theme.MUTED_TEXT + "Press ENTER to continue..." + Theme.RESET);

        String input = sc.nextLine();

        while (input == null) {
            input = sc.nextLine();
        }

        Screen.clear();
    }
}