package com.quizapp.ui;

public class Terminal {
    private static final int DEFAULT_WIDTH = 90;

    public static int getWidth() {
        String columns = System.getenv("COLUMNS");

        if (columns != null) {
            try {
                int width = Integer.parseInt(columns);

                if (width > 0) {
                    return width;
                }
            } catch (Exception e) {
            }
        }

        return DEFAULT_WIDTH;
    }

    public static void printCentered(String text) {
        int terminalWidth = getWidth();

        String[] lines = text.split("\\R");

        for (String line : lines) {
            String cleanLine = removeAnsi(line);
            int padding = Math.max(0 , (terminalWidth - cleanLine.length()) / 2);

            System.out.println(" ".repeat(padding) + line);
        }
    }

    public static void printlnCentered(String text) {
        printCentered(text);
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