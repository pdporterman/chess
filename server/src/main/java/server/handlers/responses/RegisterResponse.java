package server.handlers.responses;

import model.AuthToken;
import model.User;

public class RegisterResponse {
    String username;
    String authToken;
    String message;

    public RegisterResponse(User user, AuthToken auth){
        this.username = user.getUserName();
        this.authToken = auth.getToken();
    }

    public  RegisterResponse(String mess){
        this.message = mess;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

}
