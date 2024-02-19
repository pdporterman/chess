package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import model.*;

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

    public User getUser(User user){
        for (User pastUser : users){
            if (Objects.equals(user.getUserName(), pastUser.getUserName()) && Objects.equals(user.getPassword(), pastUser.getPassword())){
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

    public boolean getAuth(AuthToken authToken){
        return auths.contains(authToken);
    }

    public Game addGame(Game game){
        Game newGame = new Game(nextGameID++, game.getGameName());
        games.add(newGame);
        return newGame;
    }

    public Game getGameID(Game game){ //get the game using the gameID
        for (Game oldGame : games){
            if (Objects.equals(game.getGameID(), oldGame.getGameID())){
                return oldGame;
            }
        }
        return null;
    }

    public Game getGameName(Game game){ //get the game using the name
        for (Game oldGame : games){
            if (Objects.equals(game.getGameName(), oldGame.getGameName())){
                return oldGame;
            }
        }
        return null;
    }

    public ArrayList<Game> getAllGames(Game game){
        return games;
    }

    public void deleteGame(Game game){
        games.remove(game);
    }

    public void clearGames(){
        games.clear();
    }
}
