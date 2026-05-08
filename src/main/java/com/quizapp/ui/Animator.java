package com.quizapp.ui;

public class Animator {

    public static void typewriter(String text , int delay) {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                System.out.flush();
                Thread.sleep(delay);
            }

            System.out.println();

        } catch (Exception e) {
            System.out.println(text);
        }
    }

    public static void centeredTypewriter(String text , int delay) {
        int width = Terminal.getWidth();
        int padding = Math.max(0 , (width - text.length()) / 2);

        System.out.print(" ".repeat(padding));

        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                System.out.flush();
                Thread.sleep(delay);
            }

            System.out.println();

        } catch (Exception e) {
            System.out.println(text);
        }
    }

    public static void loading(String text , int durationMs) {
        String[] frames = {
            "⠋" ,
            "⠙" ,
            "⠹" ,
            "⠸" ,
            "⠼" ,
            "⠴" ,
            "⠦" ,
            "⠧" ,
            "⠇" ,
            "⠏"
        };

        long start = System.currentTimeMillis();
        int index = 0;

        while (System.currentTimeMillis() - start < durationMs) {
            String frame = frames[index % frames.length];

            System.out.print("\r" + Theme.TITLE_TEXT + frame + " " + text + Theme.RESET);

            try {
                Thread.sleep(80);
            } catch (Exception e) {
            }

            index++;
        }

        System.out.print("\r");
        System.out.print(" ".repeat(text.length() + 10));
        System.out.print("\r");
    }

    public static void dotsLoading(String text , int cycles) {
        try {
            for (int i = 0; i < cycles; i++) {
                for (int dots = 0; dots <= 3; dots++) {
                    System.out.print("\r" + Theme.TITLE_TEXT + text + ".".repeat(dots) + " ".repeat(3 - dots) + Theme.RESET);

                    Thread.sleep(250);
                }
            }

            System.out.print("\r");
            System.out.print(" ".repeat(text.length() + 10));
            System.out.print("\r");

        } catch (Exception e) {
        }
    }
}