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
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.Notification;


import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    private final DataAccess dataAccess;
    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler(DataAccess data) throws DataAccessException {
        this.dataAccess = data;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case JOIN_PLAYER -> joinGamePlayer(new Gson().fromJson(message, JoinPlayer.class), session);
            case JOIN_OBSERVER -> joinGameObserver(new Gson().fromJson(message, JoinObserver.class), session);
            case LEAVE-> leaveGame(new Gson().fromJson(message, Leave.class), session);
            case RESIGN -> resignGame(new Gson().fromJson(message, Resign.class), session);
            case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMove.class), session);
        }
    }

    private void joinGameObserver(JoinObserver command, Session session) throws IOException, DataAccessException {
        try {
            String username = dataAccess.getAuth(command.getAuthString()).getUsername();
            Game gameContainer = dataAccess.getGame(command.getGameId());
            ChessGame game = gameContainer.getGame();
            LoadGame loadGameMessage = new LoadGame(game);
            connections.add(command.getGameId(), session);
            String message;
            message = String.format("%s is watching the game!", username);
            var notification = new Notification(message);
            connections.broadcast(command.getGameId(), session, new Gson().toJson(notification));
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));
        } catch (Exception e) {
            var message = "failed to join game";
            session.getRemote().sendString(new Gson().toJson(new Error(message)));

        }
    }

    private void joinGamePlayer(JoinPlayer command, Session session) throws IOException, DataAccessException {
        try {
            String username = dataAccess.getAuth(command.getAuthString()).getUsername();
            Game gameContainer = dataAccess.getGame(command.getGameId());
            if (command.getColor() == ChessGame.TeamColor.BLACK && !Objects.equals(gameContainer.getBlackUsername(), username)){
                throw new Exception();
            }
            if (command.getColor() == ChessGame.TeamColor.WHITE && !Objects.equals(gameContainer.getWhiteUsername(), username)){
                throw new Exception();
            }
            ChessGame game = gameContainer.getGame();
            LoadGame loadGameMessage = new LoadGame(game);
            connections.add(command.getGameId(), session);
            String message;
            message = String.format("%s has joined the game as %s!", username, command.getColor().toString());
            var notification = new Notification(message);
            connections.broadcast(command.getGameId(), session, new Gson().toJson(notification));
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));
        } catch (Exception e) {
            var message = "failed to join game";
            session.getRemote().sendString(new Gson().toJson(new Error(message)));

        }
    }

    private void resignGame(Resign command, Session session) throws IOException {
        try {
            String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
            Game gameContainer = dataAccess.getGame(command.getGameId());
            ChessGame game = gameContainer.getGame();
            if (!game.gameState()) {
                if (Objects.equals(userName, gameContainer.getWhiteUsername()) || Objects.equals(userName, gameContainer.getBlackUsername())) {
                    if (dataAccess.removePlayer(command.getGameId(), userName)) {
                        String message;
                        message = String.format("%s resigned the game is over!", userName);
                        game.fished();
                        dataAccess.updateChessGame(command.getGameId(), game);
                        var notification = new Notification(message);
                        connections.broadcast(command.getGameId(), null, new Gson().toJson(notification));
                        connections.remove(command.getGameId(), session);
                    }
                } else {
                    session.getRemote().sendString(new Gson().toJson(new Error("observers can not resign")));
                }
            }
            else {
                session.getRemote().sendString(new Gson().toJson(new Error("game has already ended")));
            }
        } catch (Exception e) {
            var message = "failed to disconnect";
            session.getRemote().sendString(new Gson().toJson(new Error(message)));

        }

    }

    private void leaveGame(Leave command, Session session) throws IOException, DataAccessException {
        try {
            String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
            if (dataAccess.removePlayer(command.getGameId(),userName)) {
                var message = String.format("%s left the game", userName);
                var notification = new Notification(message);
                connections.broadcast(command.getGameId(), null, new Gson().toJson(notification));
                connections.remove(command.getGameId(), session);
            }
        } catch (Exception e) {
            var message = "failed to disconnect";
            session.getRemote().sendString(new Gson().toJson(new Error(message)));
        }

    }

    private void makeMove(MakeMove command, Session session) throws IOException, DataAccessException, InvalidMoveException {
        try {
            String userName = dataAccess.getAuth(command.getAuthString()).getUsername();
            ChessMove move = command.getMove();
            Game gameContainer = dataAccess.getGame(command.getGameId());
            ChessGame game = gameContainer.getGame();
            if (!Objects.equals(gameContainer.getWhiteUsername(), userName) && !Objects.equals(gameContainer.getBlackUsername(), userName)){
                session.getRemote().sendString(new Gson().toJson(new Error("observers can not make moves")));
            }
            else if (game.gameState()){
                session.getRemote().sendString(new Gson().toJson(new Error("game has ended")));
            }
            else if (game.getTeamTurn() == ChessGame.TeamColor.WHITE && !Objects.equals(userName, gameContainer.getWhiteUsername())){
                session.getRemote().sendString(new Gson().toJson(new Error("not WHITE's turn")));
            }
            else if (game.getTeamTurn() == ChessGame.TeamColor.BLACK && !Objects.equals(userName, gameContainer.getBlackUsername())){
                session.getRemote().sendString(new Gson().toJson(new Error("not BLACK's turn")));
            }
            else {
                var winner = (game.getTeamTurn() == ChessGame.TeamColor.WHITE) ? "White" : "Black";
                game.makeMove(move);
                dataAccess.updateChessGame(command.getGameId(), game);
                if (game.isInCheck(game.getTeamTurn())){
                    var check = new Notification(String.format("%s has won! the game is over ", userName));
                    connections.broadcast(command.getGameId(), null, new Gson().toJson(check));
                }
                if (game.isInStalemate(game.getTeamTurn())){
                    var check = new Notification("the game has reached a stailmate and is over");
                    connections.broadcast(command.getGameId(), null, new Gson().toJson(check));
                }
                var message = String.format("%s made a move", userName);
                var loadGameMessage = new LoadGame(game);
                var notification = new Notification(message);
                connections.broadcast(command.getGameId(), session, new Gson().toJson(notification));
                connections.broadcast(command.getGameId(), null, new Gson().toJson(loadGameMessage));
            }
        } catch (Exception e) {
            var message = "could not make move";
            session.getRemote().sendString(new Gson().toJson(new Error(message)));
        }
    }



}
