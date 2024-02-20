package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;

public class ClearService {

    DataAccess dataAccess;

    public ClearService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    void clearUsers()throws DataAccessException {
        dataAccess.clearUsers();
    }

    void clearGames()throws DataAccessException{
        dataAccess.clearGames();
    }

    void clearAuths()throws DataAccessException{
        dataAccess.clearAuth();
    }
    void clearAll()throws  DataAccessException{
        clearAuths();
        clearGames();
        clearUsers();
    }
}
