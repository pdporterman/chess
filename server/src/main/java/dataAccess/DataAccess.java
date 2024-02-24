package dataAccess;


import model.*;
import server.handlers.requests.CreateGameRequest;
import server.handlers.requests.LoginRequest;
import server.handlers.requests.RegisterRequest;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user); // user

    User getUser(String username, String password);

    void clearUsers();

    void addAuth(AuthToken auth); // auths

    void deleteAuth(String authToken);

    AuthToken getAuth(String authToken);

    void clearAuth();

    boolean checkAuth(String auth);

    Game addGame(CreateGameRequest request); // games

    Game getGame(Integer gameID);

    Collection<Game> getAllGames();

    boolean setPlayer(String username, String color, Game game);

    void clearGames();


}
