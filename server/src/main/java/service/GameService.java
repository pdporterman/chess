package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;
import server.handlers.requests.CreateGameRequest;
import server.handlers.requests.JoinGameRequest;
import server.handlers.requests.ListGamesRequest;
import server.handlers.responses.CreateGameResponse;
import server.handlers.responses.JoinGameResponse;

import java.util.Collection;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Object createGame(CreateGameRequest request)throws DataAccessException{
        if (dataAccess.checkAuth(request.getAuthorization())){
            Game object = dataAccess.addGame(request);
            return new CreateGameResponse(object.getGameID());
        }
        throw new DataAccessException("Error: unauthorized");
    }

    public Collection<Game> listGames(ListGamesRequest request) throws DataAccessException {
        if (dataAccess.checkAuth(request.getAuthorization())) {
            return dataAccess.getAllGames();
        }
        throw new DataAccessException("Error: unauthorized");
    }

    public Object joinGame(JoinGameRequest request) throws DataAccessException{
        if (dataAccess.checkAuth(request.getAuthorization())){
            AuthToken authToken = dataAccess.getAuth(request.getAuthorization());
            if (request.getGameID() != null){
                if (dataAccess.setPlayer(authToken.getUsername(), request.getPlayerColor(), request.getGameID())){
                    return new JoinGameResponse();
                }
                throw  new DataAccessException("Error: already taken");
            }
            throw new DataAccessException("Error: bad request");
        }
        throw  new DataAccessException("Error: unauthorized");
    }
}
