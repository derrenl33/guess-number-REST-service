/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dao;
import com.mycompany.mastermind.dto.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darren
 */
@Repository
public class gameDaoDB implements gameDao{
    @Autowired
    JdbcTemplate jdbc;
    
    public static final class ActiveGameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setGameID(rs.getInt("gameID"));
            gm.setProgress(rs.getBoolean("inProgress"));
            return gm;
        }
    }
    
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setGameID(rs.getInt("gameID"));
            gm.setProgress(rs.getBoolean("inProgress"));
            gm.setAnswer(rs.getString("answer"));
            return gm;
        }
    }

    @Override
    public Game addGame(Game game) { 
        final String INSERT_GAME = "INSERT INTO game(inProgress) VALUES(?)";
        jdbc.update(INSERT_GAME, 
                true);
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameID(newId);
        game.setProgress(true);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_ACTIVE_GAMES = "SELECT gameID, inProgress FROM game WHERE inProgress = true;";
        List<Game> activeGames = jdbc.query(SELECT_ALL_ACTIVE_GAMES, new ActiveGameMapper());
        
        final String SELECT_ALL_FINISHED_GAMES = "SELECT * FROM game WHERE inProgress = false;";
        List<Game> finishedGames = jdbc.query(SELECT_ALL_FINISHED_GAMES, new GameMapper());
        
        List<Game> allGames = Stream.concat(activeGames.stream(), finishedGames.stream())
                             .collect(Collectors.toList());
        
        return allGames;
    }

    @Override
    public Game getGameByID(int gameID) {
        try {                         
            final String SELECT_FINISHED_GAME_BY_ID = "SELECT * FROM game WHERE gameID = ?;";
            Game game = jdbc.queryForObject(SELECT_FINISHED_GAME_BY_ID, new GameMapper(), gameID);
            
            if (game.getProgress() == false){
                return game;
            }else{
                final String SELECT_ACTIVE_GAME_BY_ID = "SELECT gameID, inProgress FROM game WHERE gameID = ?;";
                Game activeGame = jdbc.queryForObject(SELECT_ACTIVE_GAME_BY_ID, new ActiveGameMapper(), gameID);
                return activeGame;
            }
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public Game getGameByIDforReference(int gameID) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameID = ?;";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameID);
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
}
