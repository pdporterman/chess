package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    private final int gameID;
    public JoinObserver(String authToken, int gameId) {
        super(authToken);
        commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameId;
    }

    public int getGameId() {
        return gameID;
    }

}
