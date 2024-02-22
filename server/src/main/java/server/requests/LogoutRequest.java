package server.requests;

import model.AuthToken;

public class LogoutRequest {
    AuthToken authorization;

    public LogoutRequest(String auth){
        this.authorization = new AuthToken(auth);
    }

    public AuthToken getAuthorization() {
        return authorization;
    }
}
