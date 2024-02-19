package service;

import com.sun.net.httpserver.Authenticator;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.AuthToken;
import model.ResponseUser;
import model.User;

public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Object login(User user) throws DataAccessException {
        if (dataAccess.getUser(user) != null) {
            AuthToken auth = new AuthToken();
            dataAccess.addAuth(auth);
            return new ResponseUser(user, auth);
        }
        return null;
    }

    public Object register(User user) throws DataAccessException {
        if (dataAccess.getUser(user) == null) {
            dataAccess.addUser(user);
            AuthToken auth = new AuthToken();
            dataAccess.addAuth(auth);
            return new ResponseUser(user, auth);
        }
        return null;
    }
//    public Object logout(AuthToken auth) throws DataAccessException {
//        if (dataAccess.getAuth(auth)) {
//            dataAccess.deleteAuth(auth);
//            return new null;
//        }
//        return null;
//    }
}
