package server.requests;

import model.AuthToken;

public class ListGamesRequest {
    AuthToken authorization;

    public ListGamesRequest(String auth){
        this.authorization = new AuthToken(auth);
    }

    public AuthToken getAuthorization() {
        return authorization;
    }
}
