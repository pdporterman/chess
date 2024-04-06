package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.*;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.NotificationMessages;
import webSocketMessages.serverMessages.ServerMessage;


import java.io.IOException;
import java.util.Timer;

@WebSocket
public class WebSocketHandler {
    private final DataAccess dataAccess = new MySqlDataAccess();
    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler() throws DataAccessException {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_OBSERVER, JOIN_PLAYER -> joinGame(new Gson().fromJson(message, JoinPlayerCommand.class), session);
            case  LEAVE, RESIGN -> leaveGame(new Gson().fromJson(message, LeaveCommand.class));
            case MAKE_MOVE -> action = new Gson().fromJson(message, MakeMoveCommand.class);
        }
    }

    private LoadGameMessage joinGame(JoinPlayerCommand command, Session session) throws IOException, DataAccessException {
        String username = dataAccess.getAuth(command.getAuthString()).getUsername();
        Game gameContainer = dataAccess.getGame(command.getGameId());
        ChessGame game =  gameContainer.getGame();
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.add(command.getAuthString(), username, session);
        String message;
        if (command.getColor() == null){
            message = String.format("%s is watching the game!", username);
        }
        else{
            message = String.format("%s has joined the game as %s!", username, command.getColor());
        }
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(command.getAuthString(), notification);
        return loadGameMessage;
    }

    private void leaveGame(LeaveCommand command) throws IOException, DataAccessException {
        String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
        connections.remove(command.getAuthString());
        String message;
        if (command.isResign()){
            message = String.format("%s resigns!", userName);
        }
        else{
            message = String.format("%s left the game, waiting for another player", userName);
        }
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(command.getAuthString(), notification);
    }

    private LoadGameMessage makeMove(MakeMoveCommand command, ChessMove move) throws IOException, DataAccessException, InvalidMoveException {
        String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
        Game gameContainer = dataAccess.getGame(command.getGameId());
        ChessGame game =  gameContainer.getGame();
        game.makeMove(move);
        dataAccess.updateChessGame(command.getGameId(), game);
        var message = String.format("%s made a move", userName);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(command.getAuthString(), notification);
        return new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
    }

    private void HighlightMoves(String authToken, ChessPosition position) throws IOException {
        var message = String.format("%s left the shop", authToken);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, notification);
    }


}
