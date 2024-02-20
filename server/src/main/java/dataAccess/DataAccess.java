package dataAccess;


import model.*;
import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws DataAccessException;

    User getUser(User user) throws DataAccessException;

    Collection<User> getAllUser() throws DataAccessException;

    void deleteUser(User user) throws DataAccessException;

    void clearUsers() throws DataAccessException;

    void addAuth(AuthToken auth) throws DataAccessException;

    void deleteAuth(AuthToken authToken) throws DataAccessException;

    void clearAuth() throws DataAccessException;

    boolean getAuth(AuthToken auth);

    Game addGame(Game game) throws DataAccessException;

    Game getGameName(Game game) throws DataAccessException;

    Game getGameID(Game game) throws DataAccessException;

    Collection<Game> getAllGames(Game game) throws DataAccessException;

    void deleteGame(Game game) throws DataAccessException;

    void clearGames() throws DataAccessException;


}
