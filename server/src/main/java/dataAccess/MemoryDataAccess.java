package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import model.*;
import server.requests.CreateGameRequest;
import server.requests.LoginRequest;
import server.requests.RegisterRequest;

public class MemoryDataAccess implements DataAccess {
    ArrayList<Game> games = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    ArrayList<AuthToken> auths = new ArrayList<>();
    private int nextGameID = 1;


    public User addUser(User user){
        for (User pastUser : users){
            if (Objects.equals(user.getUserName(), pastUser.getUserName())){
                return null;
            }
        }
        users.add(user);
        return user;
    }

    public User getUser(LoginRequest request){
        for (User pastUser : users){
            if (Objects.equals(request.getUsername(), pastUser.getUserName()) && Objects.equals(request.getPassword(), pastUser.getPassword())){
                return pastUser;
            }
        }
        return null;
    }

    public User getUser(RegisterRequest request){
        for (User pastUser : users){
            if (Objects.equals(request.getUsername(), pastUser.getUserName()) && Objects.equals(request.getPassword(), pastUser.getPassword())){
                return pastUser;
            }
        }
        return null;
    }

    public Collection<User> getAllUser(){
        return users;
    }

    public void deleteUser(User user){
        users.remove(user);
    }

    public void clearUsers(){
        users.clear();
    }

    public void addAuth(AuthToken authToken){
        auths.add(authToken);
    }

    public boolean checkAuth(AuthToken authToken){
        return auths.contains(authToken);
    }

    public void deleteAuth(AuthToken authToken){
        auths.remove(authToken);
    }

    public void clearAuth(){
        auths.clear();
    }

    public Game getGame(Game game){
        return null;
    }

    public Game addGame(CreateGameRequest request){
        Game newGame = new Game(nextGameID++, request.getGameName());
        games.add(newGame);
        return newGame;
    }

    public boolean checkGame(CreateGameRequest request) throws DataAccessException {
        for (Game game : games){
            if (Objects.equals(request.getGameName(), game.getGameName())){
                return true;
            }
        }
        return false;
    }


    public ArrayList<Game> getAllGames(){
        return games;
    }

    public void deleteGame(Game game){
        games.remove(game);
    }

    public void clearGames(){
        games.clear();
    }
}
