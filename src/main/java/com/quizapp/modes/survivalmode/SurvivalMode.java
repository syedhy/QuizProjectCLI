package com.quizapp.modes.survivalmode;

import java.util.*;

import com.quizapp.dashboard.DashboardGenerator;
import com.quizapp.helpers.*;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileManager;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.ui.*;

public class SurvivalMode {

    private static int score = 0;

    public static void startSurvivalQuiz(Scanner sc){

        MenuUI.printModeDescription(
            "Survival Mode" ,
            "Stay alive for as long as possible" ,
            "You begin with 3 lives\nEach wrong answer removes one life\nSurvive as long as you can"
        );

        MenuUI.pressEnterToContinue(sc);

        score = 0;

        int lives = 3;

        String file = FileChooser.chooseFile(sc);

        Screen.clear();

        List<Question> qs = ListMaker.makeList(file);

        if(qs == null || qs.isEmpty()) {
            return;
        }

        Collections.shuffle(qs);

        int questionNumber = 1;

        for(Question q : qs){

            ProgressUI.printQuestionProgress(
                questionNumber ,
                qs.size()
            );

            ProgressUI.printLiveStats(
                score ,
                lives ,
                questionNumber ,
                qs.size()
            );

            System.out.println();

            QuizUI.printQuestionBox(
                questionNumber ,
                q.question ,
                q.options
            );

            String input = sc.nextLine();

            char ans = ' ';

            if (!input.isEmpty()) {
                ans = input.toUpperCase().charAt(0);
            }

            if(ans == q.answer.charAt(0)){
                score++;
            } else {
                lives--;
            }

            QuizUI.printAnswerFeedback(
                ans ,
                q.answer.charAt(0)
            );

            if (lives <= 0) {

                Screen.pause(1200);

                Screen.clear();

                ProgressUI.printResultCard(
                    "SURVIVAL OVER" ,
                    score ,
                    questionNumber ,
                    "All lives lost"
                );

                saveProfileResult(false);
                askDashboard(sc);

                return;
            }

            Screen.pause(1000);

            Screen.clear();

            questionNumber++;
        }

        ProgressUI.printResultCard(
            "SURVIVAL COMPLETE" ,
            score ,
            questionNumber ,
            "Perfect survival"
        );

        saveProfileResult(true);
        askDashboard(sc);
    }

    private static void saveProfileResult(boolean won) {
        Profile profile = ProfileSession.getCurrentProfile();

        if (profile != null) {
            ProfileManager.addGameResult(profile , score , won);
            ProfileSession.setCurrentProfile(profile);
        }
    }

    private static void askDashboard(Scanner sc) {
        System.out.print("\n" + Theme.MUTED_TEXT + "Open dashboard? [Y/N]: " + Theme.RESET);

        String choice = sc.nextLine().trim();

        if (choice.equalsIgnoreCase("y")) {
            DashboardGenerator.openDashboard(ProfileSession.getCurrentProfile());
            System.out.print("\nPress ENTER to continue...");
            sc.nextLine();
        }
    }
}