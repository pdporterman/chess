package model;

import java.util.UUID;

public class AuthToken {
    String token;

    public AuthToken(){
        this.token = UUID.randomUUID().toString();
    }

    public String getToken(){
        return token;
    }
}
