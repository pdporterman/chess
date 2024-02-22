package server.responses;

import model.Game;

import java.util.Collection;

public class ListGamesResponse {

    Collection<Game> games;
    String message;
    public ListGamesResponse(Collection<Game> games){
        this.games = games;
    }

    public ListGamesResponse(String mess){
        this.message = mess;
    }

}
