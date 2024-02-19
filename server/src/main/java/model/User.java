package model;

public class User {
    String userName;
    String password;
    String email;

    public User(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.email = null;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){
        return password;
    }
}
