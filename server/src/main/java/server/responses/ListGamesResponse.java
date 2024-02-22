package server.responses;

import model.Game;

import java.util.Collection;

public class ListGamesResponse {

    Collection<Game> games;
    public ListGamesResponse(Collection<Game> games){
        this.games = games;
    }
}
