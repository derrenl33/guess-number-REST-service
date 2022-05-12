/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.service;

import com.mycompany.mastermind.dao.gameDao;
import com.mycompany.mastermind.dao.roundDao;
import com.mycompany.mastermind.dto.Game;
import com.mycompany.mastermind.dto.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Darren
 */
@Service
public class mastermindServiceImpl implements mastermindService{
    private gameDao dao1;
    private roundDao dao2;
    
    public mastermindServiceImpl(gameDao dao1, roundDao dao2) {
        this.dao1 = dao1;
        this.dao2 = dao2;
    }
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Game addGame(Game game) {
        Game newGame = dao1.addGame(game);
        final String UPDATE_GAME = "UPDATE game SET answer = ? WHERE gameID = ?";
        jdbc.update(UPDATE_GAME,
                generateAnswer(),
                newGame.getGameID());
        return newGame;
    }
    
    private String generateAnswer(){
        Random generator = new Random();
        String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        
        String firstDigit = digits[generator.nextInt(digits.length)];
        
        String secondDigit;
        do{
            secondDigit = digits[generator.nextInt(digits.length)];
        }while(secondDigit.equals(firstDigit));
        
        String thirdDigit; 
        do{
            thirdDigit= digits[generator.nextInt(digits.length)];
        }while( (thirdDigit.equals(firstDigit)) || (thirdDigit.equals(secondDigit)) );
        
        String fourthDigit;
        do{
            fourthDigit = digits[generator.nextInt(digits.length)];
        }while( (fourthDigit.equals(firstDigit)) || (fourthDigit.equals(secondDigit)) || (fourthDigit.equals(thirdDigit)) );    
            
        String answer = firstDigit + secondDigit + thirdDigit + fourthDigit;
        return answer;
    }

    @Override
    public List<Game> getAllGames() {
        return dao1.getAllGames();
    }

    @Override
    public Game getGameByID(int gameID) {
        return dao1.getGameByID(gameID);
    }
    
    @Override
    public Game getGameByIDforReference(int gameID) {
        return dao1.getGameByIDforReference(gameID);
    }

    @Override
    public Round makeGuess(Game game, Round round) {
        Round newRound = dao2.makeGuess(game, round);
        String newResult = calculateResult(game.getAnswer(), newRound.getGuess());
        final String UPDATE_ROUND = "UPDATE round SET result = ? WHERE roundID = ?";
        jdbc.update(UPDATE_ROUND,
                newResult,
                newRound.getRoundID());
        if(newResult.equals("e:4:p:0")){
                final String UPDATE_GAME = "UPDATE game SET inProgress = false WHERE gameID = ?";
                jdbc.update(UPDATE_GAME,
                game.getGameID());
        }
        newRound.setResult(newResult);
        return newRound;
    }
    
    private String calculateResult(String answer, String guess){
        int exactCount = 0;
        int partialCount = 0;
        
        for(int i = 0; i < answer.length(); i++){
            for(int j = 0; j < guess.length(); j++){
                if( (answer.charAt(i) == guess.charAt(j)) && (i == j) ){
                    exactCount++;
                }else if( (answer.charAt(i) == guess.charAt(j)) && (i != j) ){
                    partialCount++;
                }
            }
        }
        
        String result = "e:" + String.valueOf(exactCount) + ":p:" + String.valueOf(partialCount);
        return result;
    }

    @Override
    public List<Round> getRoundsForGameID(Game game) {
        return dao2.getRoundsForGameID(game);
    }
    
}
