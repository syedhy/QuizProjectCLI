package com.quizapp.ui;

public class Terminal {
    private static final int LEFT_MARGIN = 4;

    public static void print(String text) {
        System.out.println(" ".repeat(LEFT_MARGIN) + text);
    }

    public static void printInline(String text) {
        System.out.print(" ".repeat(LEFT_MARGIN) + text);
    }

    public static void printRaw(String text) {
        System.out.println(text);
    }

    public static void printEmptyLines(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println();
        }
    }

    public static String centerLine(String text , int width) {
        String cleanText = removeAnsi(text);

        if (cleanText.length() >= width) {
            return text;
        }

        int leftPadding = (width - cleanText.length()) / 2;
        int rightPadding = width - cleanText.length() - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    public static String padRight(String text , int width) {
        String cleanText = removeAnsi(text);

        if (cleanText.length() >= width) {
            return text;
        }

        return text + " ".repeat(width - cleanText.length());
    }

    public static String removeAnsi(String text) {
        return text.replaceAll("\\u001B\\[[;\\d]*m" , "");
    }
}