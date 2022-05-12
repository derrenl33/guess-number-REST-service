/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dto;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Darren
 */
public class Round {
    
    private int roundID;
    private String result;
    private LocalDateTime timeStamp;
    private String guess;
    private Game game;
    
    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
    
    public Game getGame() {
        return game;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.roundID;
        hash = 11 * hash + Objects.hashCode(this.result);
        hash = 11 * hash + Objects.hashCode(this.timeStamp);
        hash = 11 * hash + Objects.hashCode(this.guess);
        hash = 11 * hash + Objects.hashCode(this.game);
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
        final Round other = (Round) obj;
        if (this.roundID != other.roundID) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.timeStamp, other.timeStamp)) {
            return false;
        }
        if (!Objects.equals(this.game, other.game)) {
            return false;
        }
        return true;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
