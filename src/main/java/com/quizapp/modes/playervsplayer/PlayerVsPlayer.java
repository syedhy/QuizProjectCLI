package com.quizapp.modes.playervsplayer;

import java.util.*;

import com.quizapp.helpers.*;
import com.quizapp.ui.*;

public class PlayerVsPlayer {
    public static void StartPlayerVsPlayerQuiz(Scanner sc){
        MenuUI.printModeDescription("Player vs Player" ,
            "A local two player battle using the same question set" ,
            "Player 1 answers 5 questions first\nPlayer 2 answers 5 questions next\nThe higher score wins");

        MenuUI.pressEnterToContinue(sc);

        String file = FileChooser.chooseFile(sc);
        Screen.clear();

        List<Question> qs = ListMaker.makeList(file);

        if(qs == null || qs.isEmpty()) return;

        Collections.shuffle(qs);

        int[] scores = {0 , 0};

        for(int j = 0; j <= 1; j++){
            int i = 1;

            Screen.clear();

            Terminal.print(Theme.BORDER_COLOR + "╭────────────────────────────────────╮" + Theme.RESET);
            Terminal.print(Theme.BORDER_COLOR + "│" + Theme.TITLE_TEXT + Terminal.centerLine("PLAYER " + (j + 1) + " GET READY" , 36) + Theme.BORDER_COLOR + "│" + Theme.RESET);
            Terminal.print(Theme.BORDER_COLOR + "╰────────────────────────────────────╯" + Theme.RESET);

            Screen.pause(1500);

            for(Question q : qs){
                Screen.clear();

                Terminal.print(Theme.TITLE_TEXT + Theme.BOLD + "PLAYER " + (j + 1) + " TURN" + Theme.RESET);

                ProgressUI.printQuestionProgress(i , 5);

                QuizUI.printQuestionBox(i , q.question , q.options);

                String input = sc.nextLine();

                char ans = ' ';

                if (!input.isEmpty()) {
                    ans = input.toUpperCase().charAt(0);
                }

                if(ans == q.answer.charAt(0)){
                    scores[j]++;
                }

                QuizUI.printAnswerFeedback(ans , q.answer.charAt(0));

                Screen.pause(1200);

                if(i >= 5) break;

                i++;
            }
        }

        Screen.clear();

        String title;

        if(scores[0] > scores[1]){
            title = "PLAYER 1 WINS";
        }else if(scores[0] < scores[1]){
            title = "PLAYER 2 WINS";
        }else{
            title = "DRAW";
        }

        ProgressUI.printResultCard(
            title ,
            Math.max(scores[0] , scores[1]) ,
            5 ,
            "P1: " + scores[0] + "   │   P2: " + scores[1]
        );
    }
}