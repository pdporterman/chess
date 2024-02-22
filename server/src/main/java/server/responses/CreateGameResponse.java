package server.responses;

public class CreateGameResponse {
    Integer gameID;

    String message;

    public CreateGameResponse(Integer id){
        this.gameID = id;
    }

    public CreateGameResponse(String mess){
        this.message = mess;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getMessage() {
        return message;
    }
}
