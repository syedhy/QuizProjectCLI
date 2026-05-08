package com.quizapp.profiles;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.quizapp.ui.Animator;
import com.quizapp.ui.Screen;
import com.quizapp.ui.Terminal;
import com.quizapp.ui.Theme;

public class ProfileMenu {
    private static final int WIDTH = 70;

    public static Profile chooseProfile(Scanner sc) {
        while (true) {
            Screen.clear();

            printProfileHub();

            Terminal.printInline(Theme.TITLE_TEXT + "> Choice: " + Theme.RESET);

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
            } else if (choice == 3) {
                return createGuestProfile();
            } else {
                Terminal.print(Theme.FEEDBACK_WRONG + "Invalid choice" + Theme.RESET);
                Screen.pause(1000);
            }
        }
    }

    private static void printProfileHub() {
        Terminal.printEmptyLines(1);

        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "██████╗ ██████╗  ██████╗ ███████╗██╗██╗     ███████╗" + Theme.RESET);
        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "██╔══██╗██╔══██╗██╔═══██╗██╔════╝██║██║     ██╔════╝" + Theme.RESET);
        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "██████╔╝██████╔╝██║   ██║█████╗  ██║██║     █████╗  " + Theme.RESET);
        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "██╔═══╝ ██╔══██╗██║   ██║██╔══╝  ██║██║     ██╔══╝  " + Theme.RESET);
        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "██║     ██║  ██║╚██████╔╝██║     ██║███████╗███████╗" + Theme.RESET);
        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "╚═╝     ╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚══════╝" + Theme.RESET);

        Terminal.printEmptyLines(1);
        Terminal.print(Theme.MUTED_TEXT + "Choose who is playing today" + Theme.RESET);
        Terminal.printEmptyLines(1);

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(WIDTH) + "╮" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine("PROFILE HUB" , WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(WIDTH) + "┤" + Theme.RESET);

        printMenuLine("[1] Select Existing Profile" , "Load saved player data");
        printMenuLine("[2] Create New Profile" , "Start fresh profile");
        printMenuLine("[3] Play As Guest" , "Temporary session");

        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(WIDTH) + "┤" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine("Profiles store ranked stats and global progress" , WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(WIDTH) + "╯" + Theme.RESET);

        Terminal.printEmptyLines(1);
    }

    private static void printMenuLine(String left , String right) {
        String contentLeft = "  " + left;
        String contentRight = right;

        int spacing = WIDTH - Terminal.removeAnsi(contentLeft).length() - Terminal.removeAnsi(contentRight).length();

        if (spacing < 1) {
            spacing = 1;
        }

        String line = contentLeft + " ".repeat(spacing) + Theme.MUTED_TEXT + contentRight + Theme.OPTION_TEXT;

        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.OPTION_TEXT + line +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );
    }

    private static Profile selectProfile(Scanner sc) {
        while (true) {
            Screen.clear();

            List<Profile> profiles = ProfileManager.getAllProfiles();

            if (profiles.isEmpty()) {
                Terminal.print(Theme.FEEDBACK_WRONG + Theme.BOLD + "No profiles found yet" + Theme.RESET);
                Terminal.print(Theme.MUTED_TEXT + "Create a profile first to start tracking stats" + Theme.RESET);
                Screen.pause(1600);
                return null;
            }

            profiles.sort(Comparator.comparingInt(Profile::getElo).reversed());

            Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "SELECT PROFILE" + Theme.RESET);
            Terminal.print(Theme.MUTED_TEXT + "Choose a profile or go back" + Theme.RESET);
            Terminal.printEmptyLines(1);

            for (int i = 0; i < profiles.size(); i++) {
                Profile p = profiles.get(i);

                Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(WIDTH) + "╮" + Theme.RESET);
                Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Terminal.padRight("  [" + (i + 1) + "] " + p.getName() , WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
                Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.padRight("  ELO: " + p.getElo() + "   Games: " + p.getTotalGames() + "   Wins: " + p.getRankedWins() , WIDTH) + Theme.BORDER_COLOR + "│" + Theme.RESET);
                Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(WIDTH) + "╯" + Theme.RESET);
            }

            Terminal.printEmptyLines(1);
            Terminal.print(Theme.MUTED_TEXT + "[0] Back to profile hub" + Theme.RESET);
            Terminal.printEmptyLines(1);

            Terminal.printInline(Theme.TITLE_TEXT + "> Select profile: " + Theme.RESET);

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                return null;
            }

            if (choice < 1 || choice > profiles.size()) {
                Terminal.print(Theme.FEEDBACK_WRONG + "Invalid profile choice" + Theme.RESET);
                Screen.pause(1200);
                continue;
            }

            Profile selected = profiles.get(choice - 1);

            Screen.clear();

            printProfileCard(
                selected.getName() ,
                selected.getElo() ,
                selected.getTotalGames() ,
                selected.getRankedWins() ,
                selected.getRankedLosses() ,
                choice
            );

            Terminal.printEmptyLines(1);

            Animator.typewriter(
                Theme.FEEDBACK_CORRECT + "Profile loaded successfully" + Theme.RESET ,
                12
            );

            Screen.pause(1200);

            return selected;
        }
    }

    private static Profile createProfile(Scanner sc) {
        Screen.clear();

        Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "CREATE PROFILE" + Theme.RESET);
        Terminal.print(Theme.MUTED_TEXT + "Enter a name for your local player profile" + Theme.RESET);

        Terminal.printEmptyLines(1);

        Terminal.printInline(Theme.TITLE_TEXT + "> Profile name: " + Theme.RESET);

        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            Terminal.print(Theme.FEEDBACK_WRONG + "Profile name cannot be empty" + Theme.RESET);
            Screen.pause(1200);
            return null;
        }

        ProfileManager.createProfile(name);

        Profile profile = ProfileManager.getProfile(name);

        Screen.clear();

        printProfilePlaceholder(name);

        Terminal.printEmptyLines(1);

        Animator.typewriter(
            Theme.FEEDBACK_CORRECT + "Profile created successfully" + Theme.RESET ,
            12
        );

        Screen.pause(1400);

        return profile;
    }

    private static Profile createGuestProfile() {
        Profile guest = new Profile("Guest");

        guest.setElo(1000);

        guest.setRankedGames(0);
        guest.setRankedWins(0);
        guest.setRankedLosses(0);

        guest.setRankedBestScore(0);
        guest.setRankedTotalScore(0);

        guest.setTotalGames(0);
        guest.setTotalQuestions(0);
        guest.setTotalCorrect(0);

        guest.setTimedGames(0);
        guest.setSurvivalGames(0);
        guest.setSuddenDeathGames(0);
        guest.setPvpGames(0);
        guest.setLlmGames(0);
        guest.setEloGames(0);

        Screen.clear();

        printProfilePlaceholder("Guest");

        Terminal.printEmptyLines(1);
        Terminal.print(Theme.TITLE_TEXT + "Guest mode does not save progress" + Theme.RESET);

        Screen.pause(1400);

        return guest;
    }

    private static void printProfileCard(String profileName , int elo , int gamesPlayed , int wins , int losses , int rank) {
        int width = 48;

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(width) + "╮" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine("PLAYER PROFILE" , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(width) + "┤" + Theme.RESET);

        printAvatarLine("       ████████████       " , width);
        printAvatarLine("     ██            ██     " , width);
        printAvatarLine("   ██    ◉      ◉    ██   " , width);
        printAvatarLine("   ██       ██       ██   " , width);
        printAvatarLine("     ██   ▀▀▀▀   ██     " , width);
        printAvatarLine("       ████████████       " , width);

        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine(" " , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine(profileName.toUpperCase() , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);

        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(width) + "┤" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.OPTION_TEXT + Terminal.centerLine("ELO: " + elo + "        Rank: #" + rank , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine("Games: " + gamesPlayed + "   Wins: " + wins + "   Losses: " + losses , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(width) + "╯" + Theme.RESET);
    }

    private static void printProfilePlaceholder(String profileName) {
        int width = 48;

        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(width) + "╮" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine("PROFILE" , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "├" + "─".repeat(width) + "┤" + Theme.RESET);

        printAvatarLine("       ████████████       " , width);
        printAvatarLine("     ██            ██     " , width);
        printAvatarLine("   ██    ◉      ◉    ██   " , width);
        printAvatarLine("   ██       ██       ██   " , width);
        printAvatarLine("     ██   ▀▀▀▀   ██     " , width);
        printAvatarLine("       ████████████       " , width);

        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.MUTED_TEXT + Terminal.centerLine(" " , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Theme.BOLD + Terminal.centerLine(profileName.toUpperCase() , width) + Theme.BORDER_COLOR + "│" + Theme.RESET);
        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(width) + "╯" + Theme.RESET);
    }

    private static void printAvatarLine(String text , int width) {
        Terminal.print(
            Theme.BORDER_COLOR + "│" +
            Theme.OPTION_TEXT + Terminal.centerLine(text , width) +
            Theme.BORDER_COLOR + "│" +
            Theme.RESET
        );
    }
}