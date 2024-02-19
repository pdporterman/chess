package model;

public class ResponseUser {
    String userName;
    String authToken;

    public ResponseUser(User user, AuthToken auth){
        this.userName = user.getUserName();
        this.authToken = auth.getToken();
    }
}
