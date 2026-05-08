package com.quizapp.ui;

import com.github.lalyos.jfiglet.FigletFont;

public class Art {

    public static void printBootLogo() {
        Screen.clear();

        try {
            String ascii = FigletFont.convertOneLine("Quiz Project");

            String[] lines = ascii.split("\\R");

            for (String line : lines) {
                Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + line + Theme.RESET);
            }

        } catch (Exception e) {
            Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "QUIZ PROJECT" + Theme.RESET);
        }

        Terminal.printEmptyLines(1);

        Terminal.print(
            Theme.MUTED_TEXT +
            "Profiles  •  ELO  •  AI Quizzes  •  Dashboard" +
            Theme.RESET
        );

        Screen.pause(700);
    }

    public static void printModeIntro(String modeName , String description , String rules) {
        Screen.clear();

        try {
            String ascii = FigletFont.convertOneLine(modeName);

            String[] lines = ascii.split("\\R");

            for (String line : lines) {
                Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + line + Theme.RESET);
            }

        } catch (Exception e) {
            Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + modeName.toUpperCase() + Theme.RESET);
        }

        Terminal.printEmptyLines(1);

        Terminal.print(Theme.OPTION_TEXT + description + Theme.RESET);

        Terminal.printEmptyLines(1);

        String[] rulesArray = rules.split("\\n");

        for (String rule : rulesArray) {
            Terminal.print(Theme.MUTED_TEXT + "• " + rule.trim() + Theme.RESET);
        }

        Terminal.printEmptyLines(2);
    }
}