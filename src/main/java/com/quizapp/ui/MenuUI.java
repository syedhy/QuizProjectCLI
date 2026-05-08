package com.quizapp.ui;

import java.util.Scanner;

public class MenuUI {
    private static final int MENU_WIDTH = 70;

    public static void printMainMenu() {
        printMenuHeader("QUIZ PROJECT");

        printMenuOption("1" , "Timed Mode" , "Race the clock");
        printMenuOption("2" , "Survival Mode" , "Three lives only");
        printMenuOption("3" , "Sudden Death" , "One mistake ends it");
        printMenuOption("4" , "Player Vs Player" , "Two player local battle");
        printMenuOption("5" , "LLM Mode" , "AI generated quiz");
        printMenuOption("6" , "ELO Mode" , "Ranked quiz mode");
        printMenuOption("7" , "Dashboard" , "Open web stats");
        printMenuOption("8" , "Switch Profile" , "Return to profile hub");
        printMenuOption("9" , "Exit" , "Close the app");

        printMenuFooter();

        Terminal.printInline(Theme.TITLE_TEXT + "> Select mode: " + Theme.RESET);
    }

    public static void printSubjectMenu() {
        printMenuHeader("SELECT SUBJECT");

        printMenuOption("1" , "General Knowledge" , "Mixed trivia");
        printMenuOption("2" , "Math" , "Numbers and logic");
        printMenuOption("3" , "Science" , "Basic science");

        printMenuFooter();

        Terminal.printInline(Theme.TITLE_TEXT + "> Choice: " + Theme.RESET);
    }

    public static void printDifficultyMenu() {
        printMenuHeader("SELECT DIFFICULTY");

        printMenuOption("1" , "Easy" , "Warm up");
        printMenuOption("2" , "Medium" , "Balanced");
        printMenuOption("3" , "Hard" , "Challenge");

        printMenuFooter();

        Terminal.printInline(Theme.TITLE_TEXT + "> Choice: " + Theme.RESET);
    }

    public static void printModeDescription(String modeName , String description , String rules) {
        Art.printModeIntro(modeName , description , rules);
    }

    private static void printMenuHeader(String title) {
        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(MENU_WIDTH) + "╮" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.BOLD + Theme.TITLE_TEXT + Terminal.centerLine(title , MENU_WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(MENU_WIDTH) + "┤" + Theme.RESET);
    }

    private static void printMenuFooter() {
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(MENU_WIDTH) + "┤" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine("Choose an option by typing its number" , MENU_WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(MENU_WIDTH) + "╯" + Theme.RESET);
        Terminal.printEmptyLines(1);
    }

    private static void printMenuOption(String number , String title , String description) {
        String left = "  [" + number + "] " + title;
        String right = description;

        int spacing = MENU_WIDTH - Terminal.removeAnsi(left).length() - Terminal.removeAnsi(right).length();

        if (spacing < 1) {
            spacing = 1;
        }

        String line = left + " ".repeat(spacing) + Theme.MUTED_TEXT + right + Theme.OPTION_TEXT;

        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.OPTION_TEXT + line + Theme.BORDER_COLOR + "│" + Theme.RESET);
    }

    public static void pressEnterToContinue(Scanner sc) {
        Terminal.printEmptyLines(1);
        Terminal.printInline(Theme.MUTED_TEXT + "Press ENTER to continue..." + Theme.RESET);

        String input = sc.nextLine();

        while (input == null) {
            input = sc.nextLine();
        }

        Screen.clear();
    }
}