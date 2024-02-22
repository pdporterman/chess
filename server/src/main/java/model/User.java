package model;

import java.util.Objects;

public class User {
    String username;
    String password;
    String email;

    public User(String userName, String password, String email){
        this.username = userName;
        this.password = password;
        this.email = email;
    }
    public User(String userName, String password){
        this.username = userName;
        this.password = password;
        this.email = null;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword(){
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }
}
