package server.responses;

import model.AuthToken;
import model.User;

public class LoginResponse {
    String userName;
    String authToken;
    String message;

    public LoginResponse(User user, AuthToken auth){
        this.userName = user.getUserName();
        this.authToken = auth.getToken();
    }

    public  LoginResponse(String mess){
        this.message = mess;
    }
}
