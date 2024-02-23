package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import model.*;
import server.handlers.requests.CreateGameRequest;
import server.handlers.requests.LoginRequest;
import server.handlers.requests.RegisterRequest;

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
    }  //auths

    public AuthToken getAuth(String authToken){
        for (AuthToken token : auths){
            if (Objects.equals(token.getToken(), authToken)){
                return token;
            }
        }
        return null;
    }

    public boolean checkAuth(String authToken){
        for (AuthToken token : auths){
            if (Objects.equals(token.getToken(), authToken)){
                return true;
            }
        }
        return false;
    }

    public void deleteAuth(String authToken){
        auths.removeIf(token -> Objects.equals(authToken, token.getToken()));
    }

    public void clearAuth(){
        auths.clear();
    }

    public Game getGame(Integer gameID){
        for (Game game : games){
            if (game.getGameID() == gameID){
                return game;
            }
        }
        return null;
    }

    public Game addGame(CreateGameRequest request){
        Game newGame = new Game(nextGameID++, request.getGameName());
        games.add(newGame);
        return newGame;
    }

    public boolean checkGame(CreateGameRequest request)  {
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

    public boolean setPlayer(String username, String color, Integer gameID){
        Game game = getGame(gameID);
        if (Objects.equals(color, "")){
            return true;
        }
        else if (Objects.equals(color, "WHITE") && Objects.equals(game.getWhiteUserName(), "")){
            game.setWhiteUserName(username);
            return true;
        }
        else if (Objects.equals(color, "BLACK") && Objects.equals(game.getBlackUserName(), "")) {
            game.setBlackUserName(username);
            return true;
        }
        return false;
    }

    public void deleteGame(Game game){
        games.remove(game);
    }

    public void clearGames(){
        games.clear();
    }
}
