package com.quizapp.profiles;

public class Profile {
    private String name;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int totalScore;
    private int bestScore;
    private int elo;

    public Profile(String name) {
        this.name = name;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.totalScore = 0;
        this.bestScore = 0;
        this.elo = 1000;
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getElo() {
        return elo;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}