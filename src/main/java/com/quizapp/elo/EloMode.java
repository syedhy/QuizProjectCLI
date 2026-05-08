package com.quizapp.elo;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.quizapp.dashboard.DashboardPrompt;
import com.quizapp.helpers.FileChooser;
import com.quizapp.helpers.ListMaker;
import com.quizapp.helpers.Question;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.profiles.ProfileStats;
import com.quizapp.ui.MenuUI;
import com.quizapp.ui.ProgressUI;
import com.quizapp.ui.QuizUI;
import com.quizapp.ui.Screen;

public class EloMode {
    public static void startEloQuiz(Scanner sc) {
        Profile currentProfile = ProfileSession.getCurrentProfile();

        if (currentProfile == null) {
            QuizUI.printError("No profile selected");
            return;
        }

        MenuUI.printModeDescription(
            "ELO Mode" ,
            "Ranked quiz mode where your rating changes after every match" ,
            "Answer 5 questions\nScore 3 or more to win\nWinning increases ELO\nLosing decreases ELO"
        );

        MenuUI.pressEnterToContinue(sc);

        String file = FileChooser.chooseFile(sc);
        Screen.clear();

        List<Question> qs = ListMaker.makeList(file);

        if (qs == null || qs.isEmpty()) return;

        Collections.shuffle(qs);

        int score = 0;
        int answered = 0;
        int questionNumber = 1;

        for (Question q : qs) {
            ProgressUI.printQuestionProgress(questionNumber , 5);

            QuizUI.printQuestionBox(questionNumber , q.question , q.options);

            String input = sc.nextLine();

            char ans = input.isEmpty() ? ' ' : input.toUpperCase().charAt(0);

            answered++;

            if (ans == q.answer.charAt(0)) {
                score++;
            }

            QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

            Screen.pause(1200);
            Screen.clear();

            if (questionNumber >= 5) break;

            questionNumber++;
        }

        boolean won = score >= 3;

        int oldElo = currentProfile.getElo();
        int opponentElo = 1000;

        double actualScore = won ? 1.0 : 0.0;

        int newElo = EloCalculator.calculateNewRating(oldElo , opponentElo , actualScore);

        currentProfile.setElo(newElo);

        ProfileStats.recordRankedMatch(currentProfile , won , score);
        ProfileStats.recordMode(currentProfile , "elo" , answered , score);

        ProfileSession.setCurrentProfile(currentProfile);

        ProgressUI.printResultCard(
            won ? "ELO VICTORY" : "ELO DEFEAT" ,
            score ,
            5 ,
            "ELO: " + oldElo + " → " + newElo
        );

        DashboardPrompt.ask(sc);
    }
}