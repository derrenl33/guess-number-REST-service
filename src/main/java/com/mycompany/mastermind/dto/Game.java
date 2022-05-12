/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dto;

import java.util.Objects;

/**
 *
 * @author Darren
 */
public class Game {

    private int gameID;
    private boolean inProgress;
    private String answer;
    
    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public boolean getProgress() {
        return inProgress;
    }

    public void setProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.gameID;
        hash = 67 * hash + (this.inProgress ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.answer);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.gameID != other.gameID) {
            return false;
        }
        if (this.inProgress != other.inProgress) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return true;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}