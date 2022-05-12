/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.dao;
import com.mycompany.mastermind.dto.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darren
 */
@Repository
public class roundDaoDB implements roundDao{
    @Autowired
    JdbcTemplate jdbc;
    
    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rd = new Round();
            rd.setRoundID(rs.getInt("roundID"));
            rd.setResult(rs.getString("result"));
            rd.setTimeStamp(rs.getTimestamp("timeStamp").toLocalDateTime());
            rd.setGuess(rs.getString("guess"));
            return rd;
        }
    }

    @Override
    public Round makeGuess(Game game, Round round) {
        final String INSERT_ROUND = "INSERT INTO round(timeStamp, guess, gameID) VALUES(?,?,?)";
        LocalDateTime currentTime = LocalDateTime.now();
        jdbc.update(INSERT_ROUND,
                currentTime, 
                round.getGuess(),
                game.getGameID());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundID(newId);    
        round.setTimeStamp(currentTime);
        return round;
    }

    @Override
    public List<Round> getRoundsForGameID(Game game) {
        final String SELECT_ROUNDS_FOR_GAME = "SELECT * FROM round WHERE gameID = ?";
        List<Round> rounds = jdbc.query(SELECT_ROUNDS_FOR_GAME, 
                new RoundMapper(), game.getGameID());
        
        return rounds;
    }
    
}
