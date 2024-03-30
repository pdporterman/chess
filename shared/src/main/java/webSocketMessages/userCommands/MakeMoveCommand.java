package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    private int gameId;
    private ChessMove move;
    public MakeMoveCommand(String authToken, int gameId, ChessMove move) {
        super(authToken);
        this.gameId =gameId;
        this.move = move;
    }

    public int getGameId() {
        return gameId;
    }

    public ChessMove getMove() {
        return move;
    }
}
