package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
    private int gameID;

    public Resign(String authToken, int gameId) {
        super(authToken);
        commandType = CommandType.RESIGN;
        this.gameID = gameId;
    }

    public int getGameId() {
        return gameID;
    }
}
