package model;

import chess.ChessGame;

public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGame game;

    public Game(int id, String name){
        this.gameID = id;
        this.gameName = name;
        this.game = new ChessGame();
    }

    public Game(int id, String name, ChessGame game) {
        this.gameID = id;
        this.gameName = name;
        this.game = game;
    }

//    public Game(Integer id){
//        this.gameID = id;
//    }

//    public Game(String name){
//        this.gameName = name;
//    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
