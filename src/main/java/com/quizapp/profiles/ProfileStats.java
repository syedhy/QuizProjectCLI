package com.quizapp.profiles;

public class ProfileStats {

    public static void recordMode(Profile profile , String modeName , int questions , int correct) {
        if (profile.getName().equalsIgnoreCase("Guest")) {
            return;
        }

        profile.setTotalGames(profile.getTotalGames() + 1);
        profile.setTotalQuestions(profile.getTotalQuestions() + questions);
        profile.setTotalCorrect(profile.getTotalCorrect() + correct);

        switch (modeName.toLowerCase()) {
            case "timed" -> profile.setTimedGames(profile.getTimedGames() + 1);

            case "survival" -> profile.setSurvivalGames(profile.getSurvivalGames() + 1);

            case "suddendeath" -> profile.setSuddenDeathGames(profile.getSuddenDeathGames() + 1);

            case "pvp" -> profile.setPvpGames(profile.getPvpGames() + 1);

            case "llm" -> profile.setLlmGames(profile.getLlmGames() + 1);

            case "elo" -> profile.setEloGames(profile.getEloGames() + 1);
        }

        ProfileManager.saveProfile(profile);
    }

    public static void recordRankedMatch(Profile profile , boolean won , int score) {
        if (profile.getName().equalsIgnoreCase("Guest")) {
            return;
        }

        profile.setRankedGames(profile.getRankedGames() + 1);

        if (won) {
            profile.setRankedWins(profile.getRankedWins() + 1);
        } else {
            profile.setRankedLosses(profile.getRankedLosses() + 1);
        }

        profile.setRankedTotalScore(
            profile.getRankedTotalScore() + score
        );

        if (score > profile.getRankedBestScore()) {
            profile.setRankedBestScore(score);
        }

        ProfileManager.saveProfile(profile);
    }
}