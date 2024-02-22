package dataAccess;


import model.*;
import server.requests.CreateGameRequest;
import server.requests.LoginRequest;
import server.requests.RegisterRequest;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws DataAccessException;

    User getUser(LoginRequest request) throws DataAccessException;

    User getUser(RegisterRequest request) throws DataAccessException;

    Collection<User> getAllUser() throws DataAccessException;

    void deleteUser(User user) throws DataAccessException;

    void clearUsers() throws DataAccessException;

    void addAuth(AuthToken auth) throws DataAccessException;

    void deleteAuth(AuthToken authToken) throws DataAccessException;

    void clearAuth() throws DataAccessException;

    boolean checkAuth(AuthToken auth);

    Game addGame(CreateGameRequest request) throws DataAccessException;

    boolean checkGame(CreateGameRequest request) throws DataAccessException;

    Game getGame(Game game) throws DataAccessException;

    Collection<Game> getAllGames() throws DataAccessException;

    void deleteGame(Game game) throws DataAccessException;

    void clearGames() throws DataAccessException;


}
