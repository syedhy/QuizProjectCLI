package com.quizapp.dashboard;

import java.util.Scanner;

import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.ui.Terminal;
import com.quizapp.ui.Theme;

public class DashboardPrompt {

    public static void ask(Scanner sc) {
        Terminal.printEmptyLines(1);

        Terminal.printInline(
            Theme.MUTED_TEXT +
            "Open updated dashboard? [Y/N]: " +
            Theme.RESET
        );

        String choice = sc.nextLine().trim();

        if (choice.equalsIgnoreCase("y")) {
            Profile profile = ProfileSession.getCurrentProfile();

            if (profile != null) {
                DashboardGenerator.openDashboard(profile);
            }

            Terminal.printEmptyLines(1);

            Terminal.printInline(
                Theme.MUTED_TEXT +
                "Press ENTER to continue..." +
                Theme.RESET
            );

            sc.nextLine();
        }
    }
}