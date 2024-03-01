package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;
import server.handlers.requests.CreateGameRequest;

import java.util.Collection;

public class MySqlDataAccess implements DataAccess{

    public User addUser(User user) {
        return null;
    }

    public User getUser(String username, String password) {
        return null;
    }

    public void clearUsers() {

    }

    public void addAuth(AuthToken auth) {

    }

    public void deleteAuth(String authToken) {

    }

    public AuthToken getAuth(String authToken) {
        return null;
    }

    public void clearAuth() {

    }

    public boolean checkAuth(String auth) {
        return false;
    }

    public Game addGame(CreateGameRequest request) {
        return null;
    }

    public Game getGame(Integer gameID) {
        return null;
    }

    public Collection<Game> getAllGames() {
        return null;
    }

    public boolean setPlayer(String username, String color, Game game) {
        return false;
    }

    public void clearGames() {

    }
}
