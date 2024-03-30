package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand{
    private final int gameId;
    private final ChessGame.TeamColor color;
    public JoinPlayerCommand(String authToken, int gameId, ChessGame.TeamColor color) {
        super(authToken);
        this.gameId = gameId;
        this.color = color;
    }

    public int getGameId() {
        return gameId;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }
}
