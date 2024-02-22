package model;

public class Game {
    private int gameID;
    private String whiteUserName = "";
    private String blackUserName = "";
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

    public void setBlackUserName(String blackUserName) {
        this.blackUserName = blackUserName;
    }

    public void setWhiteUserName(String whiteUserName) {
        this.whiteUserName = whiteUserName;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public String getBlackUserName() {
        return blackUserName;
    }

    public String getWhiteUserName() {
        return whiteUserName;
    }
}
