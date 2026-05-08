package com.quizapp.modes.suddendeathmode;

import java.util.*;

import com.quizapp.helpers.*;
import com.quizapp.ui.*;

public class SuddenDeathMode {
    public static void StartSuddenDeathQuiz(Scanner sc){
        MenuUI.printModeDescription("Sudden Death" ,
            "A high stakes mode where one mistake ends the game" ,
            "Answer carefully\nOne wrong answer eliminates you\nThere are no second chances");

        MenuUI.pressEnterToContinue(sc);

        String file = FileChooser.chooseFile(sc);
        Screen.clear();

        List<Question> qs = ListMaker.makeList(file);

        if(qs == null || qs.isEmpty()) return;

        Collections.shuffle(qs);

        int i = 1;

        for(Question q : qs){
            QuizUI.printQuestionBox(i , q.question , q.options);
            String input = sc.nextLine();

            char ans = ' ';

            if (!input.isEmpty()) {
                ans = input.toUpperCase().charAt(0);
            }

            if(ans == q.answer.charAt(0)){
                QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));
                Screen.pause(800);
                Screen.clear();
            } else {
                QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));
                Terminal.printCentered(Theme.FEEDBACK_WRONG + "Eliminated! Game terminated" + Theme.RESET);
                Screen.pause(1200);
                return;
            }

            i++;
        }
    }
}