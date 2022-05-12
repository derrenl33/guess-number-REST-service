/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dao;
import com.mycompany.mastermind.dto.Game;
import java.util.List;

/**
 *
 * @author Darren
 */
public interface gameDao {
    Game addGame(Game game);
    List<Game> getAllGames();
    Game getGameByID(int gameID);
    Game getGameByIDforReference(int gameID);
}
