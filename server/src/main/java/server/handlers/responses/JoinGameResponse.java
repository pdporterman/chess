package server.handlers.responses;

public class JoinGameResponse {

    String message;

    public JoinGameResponse(){}

    public JoinGameResponse(String mess){
        this.message = mess;
    }

    public String getMessage() {
        return message;
    }
}
