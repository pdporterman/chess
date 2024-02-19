package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import model.*;

public class MemoryDataAccess implements DataAccess {
    ArrayList<Game> games = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    private int nextGameID = 0;


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

    public Collection<User> getAllUser() throws DataAccessException{
        return users;
    }

    public void deleteUser(User user) throws DataAccessException{
        users.remove(user);
    }

    public void clearUsers() throws DataAccessException{
        users.clear();
    }

    public String addAuth(String authToken) throws DataAccessException{
        return null;
    }

    public Game addGame(Game game) throws DataAccessException{
        return null;
    }

    public ResponseUser addAuth(AuthToken auth) throws DataAccessException {
        return null;
    }

    public Game getGame(Game game) throws DataAccessException{
        return null;
    }

    public ArrayList<Game> getAllGames(Game game) throws DataAccessException{
        return games;
    }

    public void deleteGame(Game game) throws DataAccessException{
        games.remove(game);
    }

    public void clearGames() throws DataAccessException{}
}
