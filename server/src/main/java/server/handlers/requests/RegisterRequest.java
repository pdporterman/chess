package server.handlers.requests;

public class RegisterRequest {
    public String username;
    String password;
    String email;

    public RegisterRequest(String userName, String password, String email){
        this.username = userName;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
