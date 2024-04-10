package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand{
    private final int gameID;
    private final ChessGame.TeamColor playerColor;
    public JoinPlayer(String authToken, int gameId, ChessGame.TeamColor color) {
        super(authToken);
        commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameId;
        this.playerColor = color;
    }

    public int getGameId() {
        return gameID;
    }

    public ChessGame.TeamColor getColor() {
        return playerColor;
    }
}
