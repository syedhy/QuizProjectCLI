package com.quizapp.main;

import java.util.Scanner;

import com.quizapp.dashboard.DashboardGenerator;
import com.quizapp.data.AppData;
import com.quizapp.elo.EloMode;
import com.quizapp.modes.llmmode.llm.LLM;
import com.quizapp.modes.playervsplayer.PlayerVsPlayer;
import com.quizapp.modes.suddendeathmode.SuddenDeathMode;
import com.quizapp.modes.survivalmode.SurvivalMode;
import com.quizapp.modes.timedmode.TimedMode;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileMenu;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.ui.Animator;
import com.quizapp.ui.Art;
import com.quizapp.ui.MenuUI;
import com.quizapp.ui.Screen;
import com.quizapp.ui.Terminal;
import com.quizapp.ui.Theme;

public class Main {
    public static void main(String[] args) {
        AppData.ensureGeneratedDataFolder();

        Scanner sc = new Scanner(System.in);

        Screen.clear();

        Art.printBootLogo();

        Terminal.printEmptyLines(1);

        Animator.typewriter(
            Theme.MUTED_TEXT + "Loading Quiz Engine..." + Theme.RESET ,
            12
        );

        Screen.pause(500);
        Screen.clear();

        Profile profile = ProfileMenu.chooseProfile(sc);
        ProfileSession.setCurrentProfile(profile);

        while (true) {
            profile = ProfileSession.getCurrentProfile();

            Screen.clear();

            printTopProfileBar(profile);

            MenuUI.printMainMenu();

            int modeChoice = sc.nextInt();
            sc.nextLine();

            if(modeChoice == 1){
                TimedMode.startTimedQuiz(sc);
            }else if(modeChoice == 2){
                SurvivalMode.startSurvivalQuiz(sc);
            }else if(modeChoice == 3){
                SuddenDeathMode.StartSuddenDeathQuiz(sc);
            }else if(modeChoice == 4){
                PlayerVsPlayer.StartPlayerVsPlayerQuiz(sc);
            }else if(modeChoice == 5){
                LLM.StartLLMQuiz(sc);
            }else if(modeChoice == 6){
                EloMode.startEloQuiz(sc);
            }else if(modeChoice == 7){
                DashboardGenerator.openDashboard(profile);
                System.out.print("\nPress ENTER to return...");
                sc.nextLine();
            }else if(modeChoice == 8){
                profile = ProfileMenu.chooseProfile(sc);
                ProfileSession.setCurrentProfile(profile);
                continue;
            }else if(modeChoice == 9){
                Screen.clear();
                Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "Thanks for playing Quiz Project" + Theme.RESET);
                Terminal.print(Theme.MUTED_TEXT + "Progress saved locally in generated_data/quiz.db" + Theme.RESET);
                break;
            }else{
                Terminal.print(Theme.FEEDBACK_WRONG + "Wrong choice" + Theme.RESET);
                Screen.pause(1000);
            }
        }

        sc.close();
    }

    private static void printTopProfileBar(Profile profile) {
        int width = MenuUI.MENU_WIDTH;
        Terminal.print(Theme.BORDER_COLOR + "╭" + "─".repeat(width) + "╮" + Theme.RESET);
        Terminal.print(
                Theme.BORDER_COLOR + "│" +
                        Theme.TITLE_TEXT
                        + Terminal.centerLine(
                                "Profile : " + profile.getName() + "   │   ELO: " + profile.getElo(), width)
                        +
                        Theme.BORDER_COLOR + "│" +
                        Theme.RESET);

        Terminal.print(
                Theme.BORDER_COLOR + "│" +
                        Terminal.padRight("", width) +
                        Theme.BORDER_COLOR + "│" +
                        Theme.RESET);

        Terminal.print(
                Theme.BORDER_COLOR + "│" +
                        Theme.MUTED_TEXT
                        + Terminal.centerLine(
                                "Ranked: " + profile.getRankedWins() + "W - " + profile.getRankedLosses() + "L", width)
                        +
                        Theme.BORDER_COLOR + "│" +
                        Theme.RESET);

        Terminal.print(Theme.BORDER_COLOR + "╰" + "─".repeat(width) + "╯" + Theme.RESET);
        Terminal.printEmptyLines(1);
    }
}