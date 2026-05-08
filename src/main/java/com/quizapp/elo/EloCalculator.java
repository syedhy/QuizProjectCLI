package com.quizapp.elo;

public class EloCalculator {
    private static final int K = 32;

    public static int calculateNewRating(int playerRating , int opponentRating , double actualScore) {
        double expectedScore = 1.0 / (1.0 + Math.pow(10 , (opponentRating - playerRating) / 400.0));

        return (int) Math.round(playerRating + K * (actualScore - expectedScore));
    }
}