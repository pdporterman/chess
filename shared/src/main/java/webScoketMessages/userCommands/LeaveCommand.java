package webScoketMessages.userCommands;

public class LeaveCommand extends UserGameCommand{
    private int gameId;
    private boolean resign;
    public LeaveCommand(String authToken,int gameId, boolean resign) {
        super(authToken);
        this.gameId = gameId;
        this.resign = resign;
    }

    @Override
    public CommandType getCommandType() {
        return super.getCommandType();
    }

    @Override
    public String getAuthString() {
        return super.getAuthString();
    }

    public int getGameId() {
        return gameId;
    }

    public boolean isResign() {
        return resign;
    }
}
