package server.handlers.requests;

public class CreateGameRequest {
    String gameName;

    public CreateGameRequest(String name){
        this.gameName = name;
    }

    public String getGameName() {
        return gameName;
    }
}
