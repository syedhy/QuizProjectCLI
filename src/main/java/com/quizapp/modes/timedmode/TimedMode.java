package com.quizapp.modes.timedmode;

import java.util.*;

import com.quizapp.dashboard.DashboardPrompt;
import com.quizapp.helpers.*;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.profiles.ProfileStats;
import com.quizapp.ui.*;

public class TimedMode {
    private static int score = 0;

    public static void startTimedQuiz(Scanner sc){
        MenuUI.printModeDescription(
            "Timed Mode" ,
            "Race against the clock and answer before time runs out" ,
            "Easy gives 60 seconds\nMedium gives 80 seconds\nHard gives 100 seconds\nMaximum 10 questions"
        );

        MenuUI.pressEnterToContinue(sc);

        score = 0;
        int answered = 0;
        int timeLimitMs = 0;

        String file = FileChooser.chooseFile(sc);
        Screen.clear();

        if(file.contains("easy")) timeLimitMs = 60000;
        else if(file.contains("medium")) timeLimitMs = 80000;
        else timeLimitMs = 100000;

        List<Question> qs = ListMaker.makeList(file);

        if(qs == null || qs.isEmpty()) return;

        Collections.shuffle(qs);

        long startTime = System.currentTimeMillis();

        int questionNumber = 1;

        for(Question q : qs){
            long elapsed = System.currentTimeMillis() - startTime;

            if (elapsed >= timeLimitMs) {
                printTimeout(sc , answered);
                return;
            }

            int remainingSeconds = (int)((timeLimitMs - elapsed) / 1000);

            ProgressUI.printQuizDashboard(
                questionNumber ,
                10 ,
                "Time Remaining" ,
                remainingSeconds + "s"
            );

            QuizUI.printQuestionBox(questionNumber , q.question , q.options);

            String input = sc.nextLine();

            char ans = ' ';

            if (!input.isEmpty()) {
                ans = input.toUpperCase().charAt(0);
            }

            answered++;

            if(ans == q.answer.charAt(0)){
                score++;
            }

            QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

            Screen.pause(1000);
            Screen.clear();

            if(questionNumber >= 10) break;

            questionNumber++;
        }

        ProgressUI.printResultCard(
            "TIMED MODE COMPLETE" ,
            score ,
            10 ,
            "Fast answers matter"
        );

        saveProfileResult(answered);
        DashboardPrompt.ask(sc);
    }

    private static void printTimeout(Scanner sc , int answered) {
        Screen.clear();

        QuizUI.printError("Time's up! The clock ran out");

        ProgressUI.printResultCard(
            "TIMED MODE FAILED" ,
            score ,
            10 ,
            "You ran out of time"
        );

        saveProfileResult(answered);
        DashboardPrompt.ask(sc);
    }

    private static void saveProfileResult(int answered) {
        Profile profile = ProfileSession.getCurrentProfile();

        if (profile != null) {
            ProfileStats.recordMode(profile , "timed" , answered , score);
            ProfileSession.setCurrentProfile(profile);
        }
    }
}