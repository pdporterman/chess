package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;
import server.handlers.requests.CreateGameRequest;
import server.handlers.requests.ListGamesRequest;
import server.handlers.responses.CreateGameResponse;

import java.util.Collection;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Object createGame(CreateGameRequest request)throws DataAccessException{
        if (!dataAccess.checkGame(request)){
            Game object = dataAccess.addGame(request);
            return new CreateGameResponse(object.getGameID());
        }
        throw new DataAccessException("bad request");
    }

    public Collection<Game> listGames(ListGamesRequest request) throws DataAccessException {
        if (dataAccess.checkAuth(request.getAuthorization())) {
            return dataAccess.getAllGames();
        }
        throw new DataAccessException("unauthorized");
    }
}
