package webScoketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand{
    private final int gameId;
    private final ChessGame.TeamColor color;
    public JoinPlayerCommand(String authToken, int gameId, ChessGame.TeamColor color) {
        super(authToken);
        this.gameId = gameId;
        this.color = color;
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

    public ChessGame.TeamColor getColor() {
        return color;
    }
}
