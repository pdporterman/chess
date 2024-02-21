package server.responses;

import model.AuthToken;
import model.User;

public class LoginResponse {
    String userName;
    String authToken;
    String mess;

    public LoginResponse(User user, AuthToken auth){
        this.userName = user.getUserName();
        this.authToken = auth.getToken();
    }

    public  LoginResponse(String mess){
        this.mess = mess;
    }
}
