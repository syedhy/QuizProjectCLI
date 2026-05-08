package com.quizapp.modes.llmmode.helpers;

import java.util.*;

import com.quizapp.dashboard.DashboardPrompt;
import com.quizapp.helpers.*;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileSession;
import com.quizapp.profiles.ProfileStats;
import com.quizapp.ui.*;

public class StartQuiz {
    public static void Start(Scanner sc) {
        List<Question> qs = ListMaker.makeList("generated_data/Questions.txt");

        if (qs == null || qs.isEmpty()) {
            QuizUI.printError("No questions found in generated_data/Questions.txt");
            return;
        }

        Collections.shuffle(qs);

        int score = 0;
        int answered = 0;
        int currentNum = 1;

        Screen.clear();

        for (Question q : qs) {
            ProgressUI.printQuestionProgress(currentNum , 5);

            QuizUI.printQuestionBox(currentNum , q.question , q.options);

            String input = sc.nextLine();

            char ans = input.isEmpty() ? ' ' : input.toUpperCase().charAt(0);

            answered++;

            if (ans == q.answer.charAt(0)) {
                score++;
            }

            QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

            Screen.pause(1200);
            Screen.clear();

            if (currentNum >= 5) break;

            currentNum++;
        }

        ProgressUI.printResultCard(
            "LLM QUIZ COMPLETE" ,
            score ,
            5 ,
            "AI generated challenge"
        );

        saveProfileResult(answered , score);
        DashboardPrompt.ask(sc);
    }

    private static void saveProfileResult(int answered , int score) {
        Profile profile = ProfileSession.getCurrentProfile();

        if (profile != null) {
            ProfileStats.recordMode(profile , "llm" , answered , score);
            ProfileSession.setCurrentProfile(profile);
        }
    }
}