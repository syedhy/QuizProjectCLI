package com.quizapp.helpers;

import java.util.Scanner;
import com.quizapp.ui.MenuUI;

public class FileChooser {
    public static String chooseFile(Scanner sc){
        String file = "";

        MenuUI.printSubjectMenu();
        int choice = sc.nextInt();

        System.out.println();

        MenuUI.printDifficultyMenu();
        int level = sc.nextInt();
        sc.nextLine();

        if(choice == 1){
            if(level == 1)
                file = "Data/GeneralKnowledge/easy.txt";
            else if(level == 2)
                file = "Data/GeneralKnowledge/medium.txt";
            else
                file = "Data/GeneralKnowledge/hard.txt";
        }

        else if(choice == 2){
            if(level == 1)
                file = "Data/Math/easy.txt";
            else if(level == 2)
                file = "Data/Math/medium.txt";
            else
                file = "Data/Math/hard.txt";
        }

        else if(choice == 3){
            if(level == 1)
                file = "Data/Science/easy.txt";
            else if(level == 2)
                file = "Data/Science/medium.txt";
            else
                file = "Data/Science/hard.txt";
        }

        return file;
    }
}