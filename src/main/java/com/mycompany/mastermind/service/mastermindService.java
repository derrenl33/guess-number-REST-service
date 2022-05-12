/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.service;

import com.mycompany.mastermind.dto.Game;
import com.mycompany.mastermind.dto.Round;
import java.util.List;

/**
 *
 * @author Darren
 */
public interface mastermindService {
    Game addGame(Game game);
    List<Game> getAllGames();
    Game getGameByID(int gameID);
    Game getGameByIDforReference(int gameID);
    
    Round makeGuess(Game game, Round round);
    List<Round> getRoundsForGameID(Game game);
}
