package com.quizapp.modes.suddendeathmode;

import java.util.*;

import com.quizapp.dashboard.DashboardPrompt;
import com.quizapp.helpers.*;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.profiles.ProfileStats;
import com.quizapp.ui.*;

public class SuddenDeathMode {
    public static void StartSuddenDeathQuiz(Scanner sc){
        MenuUI.printModeDescription(
            "Sudden Death" ,
            "A high stakes mode where one mistake ends the game" ,
            "Answer carefully\nOne wrong answer eliminates you\nThere are no second chances"
        );

        MenuUI.pressEnterToContinue(sc);

        String file = FileChooser.chooseFile(sc);
        Screen.clear();

        List<Question> qs = ListMaker.makeList(file);

        if(qs == null || qs.isEmpty()) return;

        Collections.shuffle(qs);

        int score = 0;
        int answered = 0;
        int questionNumber = 1;

        for(Question q : qs){
            ProgressUI.printQuestionProgress(questionNumber , qs.size());

            QuizUI.printQuestionBox(questionNumber , q.question , q.options);

            String input = sc.nextLine();

            char ans = ' ';

            if (!input.isEmpty()) {
                ans = input.toUpperCase().charAt(0);
            }

            answered++;

            if(ans == q.answer.charAt(0)){
                score++;

                QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

                Screen.pause(800);
                Screen.clear();
            } else {
                QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

                Terminal.print(Theme.FEEDBACK_WRONG + "Eliminated! Game terminated" + Theme.RESET);

                saveProfileResult(answered , score);
                DashboardPrompt.ask(sc);

                return;
            }

            questionNumber++;
        }

        ProgressUI.printResultCard(
            "SUDDEN DEATH COMPLETE" ,
            score ,
            answered ,
            "No mistakes made"
        );

        saveProfileResult(answered , score);
        DashboardPrompt.ask(sc);
    }

    private static void saveProfileResult(int answered , int score) {
        Profile profile = ProfileSession.getCurrentProfile();

        if (profile != null) {
            ProfileStats.recordMode(profile , "suddendeath" , answered , score);
            ProfileSession.setCurrentProfile(profile);
        }
    }
}