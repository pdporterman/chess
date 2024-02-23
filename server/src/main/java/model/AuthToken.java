package model;

import java.util.Objects;
import java.util.UUID;

public class AuthToken {
    String token;

    String username;

    public AuthToken(String username){
        this.username = username;
        this.token = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public String getToken(){
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
