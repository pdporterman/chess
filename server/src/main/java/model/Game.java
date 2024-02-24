package model;

public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;

    public Game(int id, String name){
        this.gameID = id;
        this.gameName = name;
    }
    public Game(Integer id){
        this.gameID = id;
    }

    public Game(String name){
        this.gameName = name;
    }

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
}
