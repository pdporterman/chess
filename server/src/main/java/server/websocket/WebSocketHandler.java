package server.websocket;

import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
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

    private void joinGame(JoinPlayerCommand command, Session session) throws IOException, DataAccessException {
        connections.add(command.getAuthString(), session);
        String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
        String message;
        if (command.getColor() == null){
            message = String.format("%s is watching the game!", userName);
        }
        else{
            message = String.format("%s has joined the game as %s!", userName, command.getColor());
        }
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(command.getAuthString(), notification);
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

    private void makeMove(String authtoken, ChessMove move) throws IOException {
        var message = String.format("%s left the shop", authtoken);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authtoken, notification);
    }

    private void HighlightMoves(String authToken, ChessPosition position) throws IOException {
        var message = String.format("%s left the shop", authToken);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, notification);
    }

    public void makeNoise(String petName, String sound) throws Exception {
        try {
            var message = String.format("%s says %s", petName, sound);
            var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast("", notification);
        } catch (Exception ex) {
            throw new Exception();
        }
    }
}
