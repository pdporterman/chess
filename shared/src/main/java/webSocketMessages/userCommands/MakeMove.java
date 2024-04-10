package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    private int gameID;
    private ChessMove move;
    public MakeMove(String authToken, int gameId, ChessMove move, boolean highlight) {
        super(authToken);
        commandType = CommandType.MAKE_MOVE;
        this.gameID =gameId;
        this.move = move;
    }

    public int getGameId() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
