DROP DATABASE IF EXISTS mastermindTest;

CREATE DATABASE mastermindTest;

USE mastermindTest;

CREATE TABLE game (
    gameID INT AUTO_INCREMENT,
    inProgress BOOLEAN DEFAULT true,
    answer VARCHAR(10),
    CONSTRAINT 
        PRIMARY KEY (gameId)
);

CREATE TABLE round (
    roundID INT AUTO_INCREMENT,
    result VARCHAR(10),
    timeStamp DATETIME,
    guess VARCHAR(10),
    gameID INT,
    CONSTRAINT 
        PRIMARY KEY (roundID),
    CONSTRAINT 
        FOREIGN KEY (gameID)
        REFERENCES game(gameID)
);