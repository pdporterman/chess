package dataAccess;


import model.*;
import server.handlers.requests.CreateGameRequest;
import server.handlers.requests.LoginRequest;
import server.handlers.requests.RegisterRequest;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user); // user

    User getUser(LoginRequest request);

    User getUser(RegisterRequest request);

    Collection<User> getAllUser();

    void deleteUser(User user);

    void clearUsers();

    void addAuth(AuthToken auth); // auths

    void deleteAuth(String authToken);

    AuthToken getAuth(String authToken);

    void clearAuth();

    boolean checkAuth(String auth);

    Game addGame(CreateGameRequest request); // games

    boolean checkGame(CreateGameRequest request);

    Game getGame(Integer gameID);

    Collection<Game> getAllGames();

    boolean setPlayer(String username, String color, Game game);

    void deleteGame(Game game);

    void clearGames();


}
