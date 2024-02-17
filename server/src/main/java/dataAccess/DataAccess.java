package dataAccess;


import model.User;
import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws DataAccessException;

    User getUser(User user) throws DataAccessException;

    Collection<User> getAllUser(User user) throws DataAccessException;

    void deleteUser(User user) throws DataAccessException;

    void clearUsers() throws DataAccessException;

    authToken addAuth(AuthToken auth) throws DataAccessException;

    Game addGame(Game game) throws DataAccessException;

    Game getGame(Game game) throws DataAccessException;

    Collection<Game> getAllUser(Game game) throws DataAccessException;

    void deleteGame(Game game) throws DataAccessException;

    void clearGames() throws DataAccessException;

}
