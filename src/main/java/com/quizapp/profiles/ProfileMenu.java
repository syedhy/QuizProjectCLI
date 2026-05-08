package com.quizapp.profiles;

import java.util.List;
import java.util.Scanner;
import com.quizapp.ui.Art;
import com.quizapp.ui.Animator;
import com.quizapp.ui.Screen;
import com.quizapp.ui.Theme;

public class ProfileMenu {
    public static Profile chooseProfile(Scanner sc) {
        while (true) {
            Screen.clear();

            System.out.println(Theme.BORDER_COLOR + "╔════════════════════════════════════╗" + Theme.RESET);
            System.out.println(Theme.BORDER_COLOR + "║" + Theme.TITLE_TEXT + "           PROFILE MENU             " + Theme.BORDER_COLOR + "║" + Theme.RESET);
            System.out.println(Theme.BORDER_COLOR + "╠════════════════════════════════════╣" + Theme.RESET);
            System.out.println(Theme.BORDER_COLOR + "║" + Theme.OPTION_TEXT + "  [1] Select Profile                " + Theme.BORDER_COLOR + "║" + Theme.RESET);
            System.out.println(Theme.BORDER_COLOR + "║" + Theme.OPTION_TEXT + "  [2] Create New Profile            " + Theme.BORDER_COLOR + "║" + Theme.RESET);
            System.out.println(Theme.BORDER_COLOR + "╚════════════════════════════════════╝" + Theme.RESET);
            System.out.print(Theme.TITLE_TEXT + "> Choice: " + Theme.RESET);

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                Profile profile = selectProfile(sc);

                if (profile != null) {
                    return profile;
                }
            } else if (choice == 2) {
                Profile profile = createProfile(sc);

                if (profile != null) {
                    return profile;
                }
            }
        }
    }

    private static Profile selectProfile(Scanner sc) {
        Screen.clear();

        List<Profile> profiles = ProfileManager.getAllProfiles();

        if (profiles.isEmpty()) {
            System.out.println(Theme.FEEDBACK_WRONG + "No profiles found" + Theme.RESET);
            Screen.pause(1200);
            return null;
        }

        for (int i = 0; i < profiles.size(); i++) {
            Profile p = profiles.get(i);
            System.out.println("[" + (i + 1) + "] " + p.getName() + " | ELO: " + p.getElo());
        }

        System.out.print(Theme.TITLE_TEXT + "> Select profile: " + Theme.RESET);

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > profiles.size()) {
            return null;
        }

        return profiles.get(choice - 1);
    }

    private static Profile createProfile(Scanner sc) {
        Screen.clear();

        System.out.print(Theme.TITLE_TEXT + "> Enter profile name: " + Theme.RESET);
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            return null;
        }

        ProfileManager.createProfile(name);
        Screen.clear();

        Art.printProfileArt(name);

        System.out.println();

        Animator.centeredTypewriter(
                Theme.FEEDBACK_CORRECT + "Profile Created Successfully" + Theme.RESET,
                15);

        Screen.pause(1200);
        return ProfileManager.getProfile(name);
    }
}