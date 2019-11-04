package com.example.brainteaser;

public class Average {

    private String username;
    private int rank;
    private int score;

    public Average(String username, int rank, int score) {
        this.username = username;
        this.rank = rank;
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }
}
