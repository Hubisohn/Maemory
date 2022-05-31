package com.example.maemory;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private int wins;
    private int score;

    public User(String username, int wins, int score) {
        this.username = username;
        this.wins = wins;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public String getUsername() {
        return username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

}
