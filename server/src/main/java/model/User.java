package model;

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
}
