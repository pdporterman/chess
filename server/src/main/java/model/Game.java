package model;

public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;

    public Game(int ID, String name){
        this.gameID = ID;
        this.gameName = name;
    }
    public Game(int ID){
        this.gameID = ID;
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
