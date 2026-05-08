package com.quizapp.modes.survivalmode;

import java.util.*;

import com.quizapp.dashboard.DashboardPrompt;
import com.quizapp.helpers.*;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.profiles.ProfileStats;
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
        int answered = 0;

        String file = FileChooser.chooseFile(sc);
        Screen.clear();

        List<Question> qs = ListMaker.makeList(file);

        if(qs == null || qs.isEmpty()) return;

        Collections.shuffle(qs);

        int questionNumber = 1;

        for(Question q : qs){
            ProgressUI.printLiveStats(score , lives , questionNumber , qs.size());

            QuizUI.printQuestionBox(questionNumber , q.question , q.options);

            String input = sc.nextLine();

            char ans = ' ';

            if (!input.isEmpty()) {
                ans = input.toUpperCase().charAt(0);
            }

            answered++;

            if(ans == q.answer.charAt(0)){
                score++;
            } else {
                lives--;
            }

            QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

            if (lives <= 0) {
                Screen.pause(1200);
                Screen.clear();

                ProgressUI.printResultCard(
                    "SURVIVAL OVER" ,
                    score ,
                    answered ,
                    "All lives lost"
                );

                saveProfileResult(answered);
                DashboardPrompt.ask(sc);

                return;
            }

            Screen.pause(1000);
            Screen.clear();

            questionNumber++;
        }

        ProgressUI.printResultCard(
            "SURVIVAL COMPLETE" ,
            score ,
            answered ,
            "Perfect survival"
        );

        saveProfileResult(answered);
        DashboardPrompt.ask(sc);
    }

    private static void saveProfileResult(int answered) {
        Profile profile = ProfileSession.getCurrentProfile();

        if (profile != null) {
            ProfileStats.recordMode(profile , "survival" , answered , score);
            ProfileSession.setCurrentProfile(profile);
        }
    }
}