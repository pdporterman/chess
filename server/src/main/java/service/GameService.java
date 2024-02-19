package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;

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
}
