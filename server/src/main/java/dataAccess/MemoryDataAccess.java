package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import chess.ChessGame;
import model.*;
import handlers.requests.CreateGameRequest;

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

    public User getUser(String username, String password){
        for (User pastUser : users){
            if (Objects.equals(username, pastUser.getUserName())){
                return pastUser;
            }
        }
        return null;
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

    @Override
    public boolean updateChessGame(int gameId, ChessGame game) {
        return false;
    }

    public Game addGame(CreateGameRequest request){
        Game newGame = new Game(nextGameID++, request.getGameName());
        games.add(newGame);
        return newGame;
    }

    public Collection<Game> getAllGames(){
        return games;
    }

    public boolean setPlayer(String username, String color, Game game){
         if (Objects.equals(color, null)){
            return true;
        }
        else if (Objects.equals(color, "WHITE") && Objects.equals(game.getWhiteUsername(), null)){
            game.setWhiteUsername(username);
            return true;
        }
        else if (Objects.equals(color, "BLACK") && Objects.equals(game.getBlackUsername(), null)) {
            game.setBlackUsername(username);
            return true;
        }
        return false;
    }

    public void clearGames(){
        games.clear();
    }

    @Override
    public boolean removePlayer(int gameId, String userName) {
        return false;
    }
}
