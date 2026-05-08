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

        System.out.println();

        Animator.centeredTypewriter(
            Theme.MUTED_TEXT + "Loading Quiz Engine..." + Theme.RESET ,
            20
        );

        Animator.loading("Initializing systems" , 1800);

        Screen.pause(400);
        Screen.clear();

        Profile profile = ProfileMenu.chooseProfile(sc);
        ProfileSession.setCurrentProfile(profile);

        while (true) {
            Screen.clear();

            Terminal.printCentered(Theme.TITLE_TEXT + "Current Profile: " + profile.getName() + " | ELO: " + profile.getElo() + Theme.RESET);
            System.out.println();

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
                profile = ProfileSession.getCurrentProfile();
            }else if(modeChoice == 7){
                DashboardGenerator.openDashboard(profile);
                System.out.print("\nPress ENTER to return...");
                sc.nextLine();
            }else if(modeChoice == 8){
                break;
            }else{
                System.out.println("Wrong Choice");
            }
        }

        sc.close();
    }
}