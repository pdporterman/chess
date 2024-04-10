package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.*;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.Notification;


import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final DataAccess dataAccess = new MySqlDataAccess();
    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler() throws DataAccessException {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_OBSERVER, JOIN_PLAYER -> joinGame(new Gson().fromJson(message, JoinPlayerCommand.class), session);
            case LEAVE-> leaveGame(new Gson().fromJson(message, LeaveCommand.class), session);
            case RESIGN -> resignGame(new Gson().fromJson(message, ResignCommand.class), session);
            case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMoveCommand.class), session);
        }
    }

    private void joinGame(JoinPlayerCommand command, Session session) throws IOException, DataAccessException {
        try {
            String username = dataAccess.getAuth(command.getAuthString()).getUsername();
            Game gameContainer = dataAccess.getGame(command.getGameId());
            ChessGame game = gameContainer.getGame();
            LoadGameMessage loadGameMessage = new LoadGameMessage(game);
            connections.add(command.getGameId(), session);
            String message;
            if (command.getColor() == null) {
                message = String.format("%s is watching the game!", username);
            } else {
                message = String.format("%s has joined the game as %s!", username, command.getColor());
            }
            var notification = new Notification(message);
            connections.broadcast(command.getGameId(), session, notification);
            connections.broadcast(command.getGameId(), session, loadGameMessage);
        } catch (Exception e) {
            var message = "failed to join game";
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(message)));

        }
    }

    private void resignGame(ResignCommand command, Session session) throws IOException {
        try {
            String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
            if (dataAccess.removePlayer(command.getGameId(),userName)) {
                connections.remove(command.getGameId(), session);
                String message;
                message = String.format("%s resigned the game is over!", userName);
                Game gameContainer = dataAccess.getGame(command.getGameId());
                ChessGame game = gameContainer.getGame();
                game.fished();
                dataAccess.updateChessGame(command.getGameId(), game);
                var notification = new Notification(message);
                connections.broadcast(command.getGameId(), session, notification);
            }
        } catch (Exception e) {
            var message = "failed to disconnect";
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(message)));

        }

    }

    private void leaveGame(LeaveCommand command, Session session) throws IOException, DataAccessException {
        try {
            String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
            if (dataAccess.removePlayer(command.getGameId(),userName)) {
                var message = String.format("%s left the game", userName);
                var notification = new Notification(message);
                connections.broadcast(command.getGameId(), session, notification);
            }
        } catch (Exception e) {
            var message = "failed to disconnect";
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(message)));
        }

    }

    private void makeMove(MakeMoveCommand command, Session session) throws IOException, DataAccessException, InvalidMoveException {
        try {
            String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
            ChessMove move = command.getMove();
            Game gameContainer = dataAccess.getGame(command.getGameId());
            ChessGame game = gameContainer.getGame();
            if (game.gameState()){
                session.getRemote().sendString(new Gson().toJson(new Notification("game has ended")));
            }
            else{
                game.makeMove(move);
                dataAccess.updateChessGame(command.getGameId(), game);
                var message = String.format("%s made a move", userName);
                var loadGameMessage = new LoadGameMessage(game);
                var notification = new Notification(message);
                connections.broadcast(command.getGameId(), session, notification);
                connections.broadcast(command.getGameId(), session, loadGameMessage);
            }
        } catch (Exception e) {
            var message = "could not make move";
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(message)));
        }
    }



}
