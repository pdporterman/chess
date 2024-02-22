package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;
import server.requests.ListGamesRequest;
import server.responses.ListGamesResponse;

import java.util.ArrayList;
import java.util.Collection;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public ResponseCreateGame createGame(Game game)throws DataAccessException{
        if (dataAccess.getGameName(game) == null){
            Game object = dataAccess.addGame(game);
            return new ResponseCreateGame(object.getGameID());
        }
        return null;
    }

    public Collection<Game> listGames(ListGamesRequest request) throws DataAccessException {
        if (dataAccess.checkAuth(request.getAuthorization())) {
            return dataAccess.getAllGames();
        }
        throw new DataAccessException("unauthorized");
    }
}
