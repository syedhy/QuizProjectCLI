package com.quizapp.ui;

public class Animator {

    public static void typewriter(String text , int delayMs) {
        System.out.print("    ");

        for (char c : text.toCharArray()) {
            System.out.print(c);

            try {
                Thread.sleep(delayMs);
            } catch (Exception e) {
            }
        }

        System.out.println();
    }

    public static void loading(String text , int steps , int delayMs) {
        System.out.print("    " + text);

        for (int i = 0; i < steps; i++) {
            try {
                Thread.sleep(delayMs);
            } catch (Exception e) {
            }

            System.out.print(".");
        }

        System.out.println();
    }
}