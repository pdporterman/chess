package model;

public class ResponseUser {
    String userName;
    String authToken;
    String mess;

    public ResponseUser(User user, AuthToken auth){
        this.userName = user.getUserName();
        this.authToken = auth.getToken();
    }

    public  ResponseUser(String mess){
        this.mess = mess;
    }
}
