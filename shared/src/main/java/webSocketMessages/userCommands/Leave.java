package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    private int gameID;
    public Leave(String authToken, int gameId) {
        super(authToken);
        commandType = CommandType.LEAVE;
        this.gameID = gameId;
    }

    public int getGameId() {
        return gameID;
    }

}
