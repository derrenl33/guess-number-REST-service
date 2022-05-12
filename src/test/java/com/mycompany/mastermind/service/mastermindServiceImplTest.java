/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.service;

import com.mycompany.mastermind.dto.Game;
import com.mycompany.mastermind.dto.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Darren
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class mastermindServiceImplTest {
    
    @Autowired
    mastermindService service;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public mastermindServiceImplTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Game> games = service.getAllGames();
        List<Round> rounds = new ArrayList();
        
        for (Game game : games) {
            rounds = Stream.concat(rounds.stream(), service.getRoundsForGameID(game).stream())
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
    
    public static final class TestAnswerMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setAnswer(rs.getString("answer"));
            return gm;
        }
    }

    /**
     * Test of addGame method, of class mastermindServiceImpl.
     */
    @Test
    public void testAddGameForAnswer() {
        Game game = new Game();
        game = service.addGame(game);
        
        final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameID = ?;";
        Game gameFromDB = jdbc.queryForObject(SELECT_GAME_BY_ID, new TestAnswerMapper(), game.getGameID());
        
        assertNotNull(gameFromDB.getAnswer());
    }


    /**
     * Test of makeGuess method, of class mastermindServiceImpl.
     */
    @Test
    public void testMakeGuessForResult() {
        Game game = new Game();
        game = service.addGame(game);
        
        final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameID = ?;";
        Game gameFromDB = jdbc.queryForObject(SELECT_GAME_BY_ID, new TestAnswerMapper(), game.getGameID());
        
        game.setAnswer(gameFromDB.getAnswer());
        
        Round roundBody = new Round();
        roundBody.setGuess("1234");
        
        Round round = service.makeGuess(game, roundBody);
        
        assertNotNull(round.getResult());
    }
    
}
