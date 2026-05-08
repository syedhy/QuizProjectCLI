package com.quizapp.ui;

import com.github.lalyos.jfiglet.FigletFont;

public class Art {

    public static void printBootLogo() {
        try {
            String ascii = FigletFont.convertOneLine("Quiz Project");

            Terminal.printEmptyLines(1);

            String[] lines = ascii.split("\\R");

            for (String line : lines) {
                Terminal.printCentered(Theme.TITLE_TEXT + line + Theme.RESET);
            }

            Terminal.printEmptyLines(1);

            Terminal.printCentered(Theme.MUTED_TEXT + "Interactive CLI Quiz Experience" + Theme.RESET);

        } catch (Exception e) {
            Terminal.printCentered(Theme.TITLE_TEXT + "QUIZ PROJECT" + Theme.RESET);
        }
    }

    public static void printModeBanner(String modeName) {
        try {
            String ascii = FigletFont.convertOneLine(modeName);

            System.out.println();

            String[] lines = ascii.split("\\R");

            for (String line : lines) {
                Terminal.printCentered(Theme.QUESTION_TEXT + line + Theme.RESET);
            }

            System.out.println();

        } catch (Exception e) {
            Terminal.printCentered(modeName.toUpperCase());
        }
    }

    public static void printProfileArt(String profileName) {
        Terminal.printCentered(Theme.BORDER_COLOR + "╭────────────────────╮" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Terminal.centerLine("PROFILE" , 20) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "├────────────────────┤" + Theme.RESET);

        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.OPTION_TEXT + Terminal.centerLine("◉" , 20) + Theme.BORDER_COLOR + "│" + Theme.RESET);

        Terminal.printCentered(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine(profileName , 20) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.printCentered(Theme.BORDER_COLOR + "╰────────────────────╯" + Theme.RESET);
    }
}