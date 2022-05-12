/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mastermind.controller;

import com.mycompany.mastermind.dto.*;
import com.mycompany.mastermind.service.mastermindService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Darren
 */
@RestController
@RequestMapping("/api/mastermind")
public class mastermindController {
        private final mastermindService service;

    public mastermindController(mastermindService service) {
        this.service = service;
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(@RequestBody Game game) {
        return service.addGame(game);
    }
    
    @GetMapping("/game")
    public List<Game> allGames() {
        return service.getAllGames();
    }
    
    @GetMapping("/game/{gameID}")
    public ResponseEntity<Game> findGameByID(@PathVariable int gameID) {
        Game game = service.getGameByID(gameID);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }
    
    @PostMapping("/guess/{gameID}")
    public Round createRound(@PathVariable int gameID, @RequestBody Round round) {
        Game game = service.getGameByIDforReference(gameID);
        return service.makeGuess(game, round);
    }
    
    @GetMapping("/rounds/{gameID}")
    public ResponseEntity<List<Round>> findRoundsForGame(@PathVariable int gameID) {
        List<Round> rounds = service.getRoundsForGameID(service.getGameByIDforReference(gameID));
        if (rounds == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(rounds);
    }
}
