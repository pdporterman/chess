package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import model.User;
import chess.ChessGame;

public class MemoryDataAccess implements DataAccess {
    HashMap<Integer, ChessGame> games = new HashMap<>();
    ArrayList<User> users = new ArrayList<>();
    private int nextGameID = 1;


    public User addUser(User user) throws DataAccessException{
        for (User pastUser : users){
            if (Objects.equals(user.getUserName(), pastUser.getUserName())){
                return null;
            }
        }
        users.add(user);
        return user;
    }

    public User getUser(User user) throws DataAccessException{
        for (User pastUser : users){
            if (Objects.equals(user.getUserName(), pastUser.getUserName()) && Objects.equals(user.getPassword(), pastUser.getPassword())){
                return user;
            }
        }
        return null;
    }

    public Collection<User> getAllUser(User user) throws DataAccessException{
        return users;
    }

    public void deleteUser(User user) throws DataAccessException;

    public void clearUsers() throws DataAccessException;

//    public authToken addAuth(AuthToken auth) throws DataAccessException;

//    public Game addGame(Game game) throws DataAccessException;
//
//    public Game getGame(Game game) throws DataAccessException;
//
//    public Collection<Game> getAllGames(Game game) throws DataAccessException;
//
//    public void deleteGame(Game game) throws DataAccessException;
//
//    public void clearGames() throws DataAccessException;
}
