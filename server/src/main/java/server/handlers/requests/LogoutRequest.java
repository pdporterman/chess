package server.handlers.requests;

import model.AuthToken;

public class LogoutRequest {
    String authorization;

    public LogoutRequest(String auth){
        this.authorization = auth;
    }

    public String getAuthorization() {
        return authorization;
    }
}
