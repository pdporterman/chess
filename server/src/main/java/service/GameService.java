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
import java.util.Objects;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Object createGame(CreateGameRequest request)throws DataAccessException{
        if(!Objects.equals(request.getAuthorization(), "") && !Objects.equals(request.getGameName(), "")) {
            if (dataAccess.checkAuth(request.getAuthorization())) {
                Game object = dataAccess.addGame(request);
                return new CreateGameResponse(object.getGameID());
            }
            throw new DataAccessException("Error: unauthorized");
        }
        throw new DataAccessException("Error: missing infromation");
    }

    public Collection<Game> listGames(ListGamesRequest request) throws DataAccessException {
        if (!Objects.equals(request.getAuthorization(), "")) {
            if (dataAccess.checkAuth(request.getAuthorization())) {
                return dataAccess.getAllGames();
            }
            throw new DataAccessException("Error: unauthorized");
        }
        throw  new DataAccessException("Error: bad request");
    }

    public Object joinGame(JoinGameRequest request) throws DataAccessException{
        if (dataAccess.checkAuth(request.getAuthorization())){
            AuthToken authToken = dataAccess.getAuth(request.getAuthorization());
            Game game = dataAccess.getGame(request.getGameID());
            if (request.getGameID() != null && game != null){
                if (dataAccess.setPlayer(authToken.getUsername(), request.getPlayerColor(), game)){
                    return new JoinGameResponse();
                }
                throw  new DataAccessException("Error: already taken");
            }
            throw new DataAccessException("Error: bad request");
        }
        throw  new DataAccessException("Error: unauthorized");
    }
}
