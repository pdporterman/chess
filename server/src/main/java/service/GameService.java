package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;
import handlers.requests.CreateGameRequest;
import handlers.requests.JoinGameRequest;
import handlers.requests.ListGamesRequest;
import handlers.responses.CreateGameResponse;
import handlers.responses.JoinGameResponse;
import handlers.responses.ListGamesResponse;

import java.util.Objects;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public CreateGameResponse createGame(CreateGameRequest request)throws DataAccessException{
        if(!Objects.equals(request.getAuthorization(), "") && !Objects.equals(request.getGameName(), "")) {
            if (dataAccess.checkAuth(request.getAuthorization())) {
                Game object = dataAccess.addGame(request);
                return new CreateGameResponse(object.getGameID());
            }
            throw new DataAccessException("Error: unauthorized");
        }
        throw new DataAccessException("Error: missing infromation");
    }

    public ListGamesResponse listGames(ListGamesRequest request) throws DataAccessException {
        if (!Objects.equals(request.getAuthorization(), "")) {
            if (dataAccess.checkAuth(request.getAuthorization())) {
                return new ListGamesResponse(dataAccess.getAllGames());
            }
            throw new DataAccessException("Error: unauthorized");
        }
        throw  new DataAccessException("Error: bad request");
    }

    public JoinGameResponse joinGame(JoinGameRequest request) throws DataAccessException{
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
