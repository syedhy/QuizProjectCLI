package com.quizapp.profiles;

public class Profile {
    private String name;

    // Ranked / ELO stats
    private int elo;
    private int rankedGames;
    private int rankedWins;
    private int rankedLosses;

    private int rankedTotalScore;
    private int rankedBestScore;

    // Global stats
    private int totalGames;
    private int totalQuestions;
    private int totalCorrect;

    // Mode counts
    private int timedGames;
    private int survivalGames;
    private int suddenDeathGames;
    private int pvpGames;
    private int llmGames;
    private int eloGames;

    public Profile() {
    }

    public Profile(String name) {
        this.name = name;
        this.elo = 1000;
    }

    public String getName() {
        return name;
    }

    public int getElo() {
        return elo;
    }

    public int getRankedGames() {
        return rankedGames;
    }

    public int getRankedWins() {
        return rankedWins;
    }

    public int getRankedLosses() {
        return rankedLosses;
    }

    public int getRankedTotalScore() {
        return rankedTotalScore;
    }

    public int getRankedBestScore() {
        return rankedBestScore;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public int getTimedGames() {
        return timedGames;
    }

    public int getSurvivalGames() {
        return survivalGames;
    }

    public int getSuddenDeathGames() {
        return suddenDeathGames;
    }

    public int getPvpGames() {
        return pvpGames;
    }

    public int getLlmGames() {
        return llmGames;
    }

    public int getEloGames() {
        return eloGames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void setRankedGames(int rankedGames) {
        this.rankedGames = rankedGames;
    }

    public void setRankedWins(int rankedWins) {
        this.rankedWins = rankedWins;
    }

    public void setRankedLosses(int rankedLosses) {
        this.rankedLosses = rankedLosses;
    }

    public void setRankedTotalScore(int rankedTotalScore) {
        this.rankedTotalScore = rankedTotalScore;
    }

    public void setRankedBestScore(int rankedBestScore) {
        this.rankedBestScore = rankedBestScore;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public void setTimedGames(int timedGames) {
        this.timedGames = timedGames;
    }

    public void setSurvivalGames(int survivalGames) {
        this.survivalGames = survivalGames;
    }

    public void setSuddenDeathGames(int suddenDeathGames) {
        this.suddenDeathGames = suddenDeathGames;
    }

    public void setPvpGames(int pvpGames) {
        this.pvpGames = pvpGames;
    }

    public void setLlmGames(int llmGames) {
        this.llmGames = llmGames;
    }

    public void setEloGames(int eloGames) {
        this.eloGames = eloGames;
    }

    public double getRankedWinRate() {
        if (rankedGames == 0) return 0;

        return ((double) rankedWins / rankedGames) * 100.0;
    }

    public double getRankedAverageScore() {
        if (rankedGames == 0) return 0;

        return (double) rankedTotalScore / rankedGames;
    }

    public double getOverallAccuracy() {
        if (totalQuestions == 0) return 0;

        return ((double) totalCorrect / totalQuestions) * 100.0;
    }

    public String getFavoriteMode() {
        int max = timedGames;
        String favorite = "Timed";

        if (survivalGames > max) {
            max = survivalGames;
            favorite = "Survival";
        }

        if (suddenDeathGames > max) {
            max = suddenDeathGames;
            favorite = "Sudden Death";
        }

        if (pvpGames > max) {
            max = pvpGames;
            favorite = "PvP";
        }

        if (llmGames > max) {
            max = llmGames;
            favorite = "LLM";
        }

        if (eloGames > max) {
            favorite = "ELO";
        }

        return favorite;
    }
}