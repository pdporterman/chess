package server.handlers.requests;

import model.AuthToken;

public class ListGamesRequest {
    String authorization;

    public ListGamesRequest(String auth){
        this.authorization = auth;
    }

    public String getAuthorization() {
        return authorization;
    }
}
