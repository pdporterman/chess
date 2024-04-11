package handlers.responses;

import model.Game;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ListGamesResponse {

    public Collection<Game> games;
    String message;
    public ListGamesResponse(Collection<Game> games){
        this.games = games;
    }

    public ListGamesResponse(String mess){
        this.message = mess;
    }

    public Collection<Game> getGames() {
        return games;
    }

    public String gamesToString(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        for (Game game: games){
            out.println(game.getGameID()+ " : " + game.getGameName());
        }
        return out.toString();
    }
}
