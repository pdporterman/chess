package dataAccess;


import chess.ChessGame;
import model.*;
import server.handlers.requests.CreateGameRequest;
import server.handlers.requests.LoginRequest;
import server.handlers.requests.RegisterRequest;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws DataAccessException; // user

    User getUser(String username, String password) throws DataAccessException;

    void clearUsers() throws DataAccessException;

    void addAuth(AuthToken auth) throws DataAccessException; // auths

    void deleteAuth(String authToken) throws DataAccessException;

    AuthToken getAuth(String authToken) throws DataAccessException;

    void clearAuth() throws DataAccessException;

    boolean checkAuth(String auth) throws DataAccessException;

    Game addGame(CreateGameRequest request) throws DataAccessException; // games

    Game getGame(Integer gameID) throws DataAccessException;

    public boolean updateChessGame(int gameId, ChessGame game);

    Collection<Game> getAllGames() throws DataAccessException;

    boolean setPlayer(String username, String color, Game game) throws DataAccessException;

    void clearGames() throws DataAccessException;


}
