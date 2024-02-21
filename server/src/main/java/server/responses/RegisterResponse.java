package server.responses;

import model.AuthToken;
import model.User;

public class RegisterResponse {
    String userName;
    String authToken;
    String mess;

    public RegisterResponse(User user, AuthToken auth){
        this.userName = user.getUserName();
        this.authToken = auth.getToken();
    }

    public  RegisterResponse(String mess){
        this.mess = mess;
    }
}
