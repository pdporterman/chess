package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import server.handlers.requests.LoginRequest;
import server.handlers.requests.LogoutRequest;
import server.handlers.requests.RegisterRequest;
import server.handlers.responses.LoginResponse;
import server.handlers.responses.LogoutResponse;
import server.handlers.responses.RegisterResponse;

import java.util.Objects;

public class UserService {

    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public LoginResponse login(LoginRequest request) throws DataAccessException {
        User user = dataAccess.getUser(request.getUsername(),request.getPassword());
        if (!Objects.equals(request.getUsername(), "") && !Objects.equals(request.getPassword(), "")) {
            if (user != null && Objects.equals(user.getPassword(), request.getPassword())) {
                AuthToken auth = new AuthToken(request.getUsername());
                dataAccess.addAuth(auth);
                return new LoginResponse(user, auth);
            }
            throw new DataAccessException("Error: unauthorized");
        }
        throw new DataAccessException("Error: bad requests");

    }

    public RegisterResponse register(RegisterRequest request) throws DataAccessException {
        if (!Objects.equals(request.getUsername(), null) && !Objects.equals(request.getPassword(), null) && !Objects.equals(request.getEmail(), null)) {
            User prevuser = dataAccess.getUser(request.getUsername(), request.getPassword());
            if (prevuser == null) {
                User user = new User(request.getUsername(), request.getPassword(), request.getEmail());
                dataAccess.addUser(user);
                AuthToken auth = new AuthToken(request.getUsername());
                dataAccess.addAuth(auth);
                return new RegisterResponse(user, auth);
            }
            throw new DataAccessException("Error: already taken");
        }
        throw new DataAccessException("Error: bad request");
    }

    public LogoutResponse logout(LogoutRequest request) throws DataAccessException {
        if(!Objects.equals(request.getAuthorization(), "")){
            if (dataAccess.checkAuth(request.getAuthorization())) {
                dataAccess.deleteAuth(request.getAuthorization());
                return new LogoutResponse();
            }
            throw new DataAccessException("Error: unauthorized");
        }
        throw new DataAccessException("Error: bad request");
    }

}
