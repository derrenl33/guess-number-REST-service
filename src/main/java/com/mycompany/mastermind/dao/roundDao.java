/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dao;
import com.mycompany.mastermind.dto.*;
import java.util.List;

/**
 *
 * @author Darren
 */
public interface roundDao {
    Round makeGuess(Game game, Round round);
    List<Round> getRoundsForGameID(Game game);
}
