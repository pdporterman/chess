package server.handlers.requests;

import model.AuthToken;

public class JoinGameRequest {

    public String playerColor;
    Integer gameID;
    String authorization;

    public JoinGameRequest(Integer id){
        this.gameID = id;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }
}
