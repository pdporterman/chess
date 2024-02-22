package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.ResponseUser;
import model.User;
import server.requests.LoginRequest;
import server.requests.LogoutRequest;
import server.requests.RegisterRequest;
import server.responses.LoginResponse;
import server.responses.LogoutResponse;
import server.responses.RegisterResponse;

public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Object login(LoginRequest request) throws DataAccessException {
        User user = dataAccess.getUser(request);
        if (user != null) {
            AuthToken auth = new AuthToken();
            dataAccess.addAuth(auth);
            return new LoginResponse(user, auth);
        }
        throw new DataAccessException("user does not exist");
    }

    public Object register(RegisterRequest request) throws DataAccessException {
        User prevuser = dataAccess.getUser(request);
        if (prevuser == null) {
            User user = new User(request.getUsername(), request.getPassword(), request.getEmail());
            dataAccess.addUser(user);
            AuthToken auth = new AuthToken();
            dataAccess.addAuth(auth);
            return new RegisterResponse(user, auth);
        }
        throw new DataAccessException("already taken");
    }

    public Object logout(LogoutRequest request) throws DataAccessException {
        if (dataAccess.checkAuth(request.getAuthorization())) {
            dataAccess.deleteAuth(request.getAuthorization());
            return new LogoutResponse();
        }
        throw new DataAccessException("unauthorized");
    }

}
