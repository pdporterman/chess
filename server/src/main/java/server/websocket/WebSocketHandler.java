package server.websocket;

import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
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
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_OBSERVER, JOIN_PLAYER -> action = new Gson().fromJson(message, JoinPlayerCommand.class);
            case  LEAVE, RESIGN -> action = new Gson().fromJson(message, LeaveCommand.class);
            case MAKE_MOVE -> action = new Gson().fromJson(message, MakeMoveCommand.class);
        }
    }

    private void joinGame(String authtoken, Session session) throws IOException, DataAccessException {
        connections.add(authtoken, session);
        String userName = dataAccess.getAuth(authtoken).getUsername() ;
        var message = String.format("%s is in the shop", userName);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(userName, notification);
    }

    private void observeGame(String visitorName, Session session) throws IOException {
        connections.add(visitorName, session);
        var message = String.format("%s is in the shop", visitorName);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(visitorName, notification);
    }

    private void resignGame(String visitorName, Session session) throws IOException {
        connections.remove(visitorName);
        var message = String.format("%s left the shop", visitorName);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(visitorName, notification);
    }

    private void leaveGame(String visitorName) throws IOException {
        connections.remove(visitorName);
        var message = String.format("%s left the shop", visitorName);
        var notification = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(visitorName, notification);
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
