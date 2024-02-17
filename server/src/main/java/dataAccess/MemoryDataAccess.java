package dataAccess;

import java.util.Collection;

import dataAccess.model.User;

public class MemoryDataAccess implements DataAccess {


    public User addUser(User user) throws DataAccessException;

    public User getUser(Authentication.User user) throws DataAccessException;

    public Collection<User> getAllUser(User user) throws DataAccessException;

    public void deleteUser(User user) throws DataAccessException;

    public void clearUsers() throws DataAccessException;

    public authToken addAuth(AuthToken auth) throws DataAccessException;

    public Game addGame(Game game) throws DataAccessException;

    public Game getGame(Game game) throws DataAccessException;

    public Collection<Game> getAllUser(Game game) throws DataAccessException;

    public void deleteGame(Game game) throws DataAccessException;

    public void clearGames() throws DataAccessException;
}
