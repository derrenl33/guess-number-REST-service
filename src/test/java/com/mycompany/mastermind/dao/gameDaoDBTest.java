/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dao;

import com.mycompany.mastermind.dto.Game;
import com.mycompany.mastermind.dto.Round;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Darren
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class gameDaoDBTest {
    
    @Autowired
    gameDao gameDao;
    
    @Autowired
    roundDao roundDao;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public gameDaoDBTest() {
    }   
    
    @BeforeEach
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        List<Round> rounds = new ArrayList();
        
        for (Game game : games) {
            rounds = Stream.concat(rounds.stream(), roundDao.getRoundsForGameID(game).stream())
                             .collect(Collectors.toList());                       
        }
        
        for (Round round : rounds) {
            final String DELETE_ROUND = "DELETE FROM round WHERE roundID = ?";
            jdbc.update(DELETE_ROUND, round.getRoundID());
        }
        
        for (Game game : games) {           
            final String DELETE_GAME = "DELETE FROM game WHERE gameID = ?";
            jdbc.update(DELETE_GAME, game.getGameID());
                       
        } 
    }
    
    /**
     * Test of addGame/getGameByID methods, of class gameDaoDB.
     */
    @Test
    public void testAddAndGetGameByID() {
        Game game = new Game();
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameByID(game.getGameID());
        
        assertEquals(game, fromDao);
    }
    
    /**
     * Test of getAllGames method, of class gameDaoDB.
     */
    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1 = gameDao.addGame(game1);
        
        Game game2 = new Game();
        game2 = gameDao.addGame(game2);
        
        List<Game> games = gameDao.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

}
