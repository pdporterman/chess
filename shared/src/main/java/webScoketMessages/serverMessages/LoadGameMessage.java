package webScoketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage{
    private final ChessGame game;
    public LoadGameMessage(ServerMessageType type, ChessGame game) {
        super(type);
        this.game = game;
    }

    @Override
    public ServerMessageType getServerMessageType() {
        return super.getServerMessageType();
    }

    public ChessGame getGame() {
        return game;
    }
}
