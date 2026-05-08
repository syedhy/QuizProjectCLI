package com.quizapp.helpers;

import java.io.*;
import java.util.*;

public class ListMaker {
    public static List<Question> makeList(String filePath) {
        List<Question> questions = new ArrayList<>();

        File file = new File(filePath);

        try (BufferedReader br = file.exists()
                ? new BufferedReader(new FileReader(file))
                : new BufferedReader(new InputStreamReader(ListMaker.class.getClassLoader().getResourceAsStream(filePath)))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("@");

                if (parts.length >= 6) {
                    questions.add(new Question(parts[0] , Arrays.copyOfRange(parts , 1 , 5) , parts[5]));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading questions: " + e.getMessage());
        }

        return questions;
    }
}