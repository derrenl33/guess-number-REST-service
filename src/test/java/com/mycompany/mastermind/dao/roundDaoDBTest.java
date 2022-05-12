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
public class roundDaoDBTest {
    
    @Autowired
    gameDao gameDao;
    
    @Autowired
    roundDao roundDao;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public roundDaoDBTest() {
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
     * Test of makeGuess method, of class roundDaoDB.
     */
    @Test
    public void testMakeGuess() {
        Game game = new Game();
        game = gameDao.addGame(game);
        
        Round roundBody = new Round();
        roundBody.setGuess("1234");
        
        Round round = roundDao.makeGuess(game, roundBody);
        
        assertEquals(roundBody.getGuess(), round.getGuess());
    }

    /**
     * Test of getRoundsForGameID method, of class roundDaoDB.
     */
    @Test
    public void testGetRoundsForGameID() {
        Game game = new Game();
        game = gameDao.addGame(game);
        
        Round roundBody1 = new Round();
        roundBody1.setGuess("1234");       
        Round round1 = roundDao.makeGuess(game, roundBody1);
        
        Round roundBody2 = new Round();
        roundBody2.setGuess("5678");       
        Round round2 = roundDao.makeGuess(game, roundBody2);
        
        List<Round> roundsForGame = roundDao.getRoundsForGameID(game);   
        
        assertEquals(2, roundsForGame.size());
        
        assertEquals(roundsForGame.get(0).getRoundID(), round1.getRoundID());
        assertEquals(roundsForGame.get(0).getGuess(), round1.getGuess());
      
        assertEquals(roundsForGame.get(1).getRoundID(), round2.getRoundID());
        assertEquals(roundsForGame.get(1).getGuess(), round2.getGuess());
    } 
}
