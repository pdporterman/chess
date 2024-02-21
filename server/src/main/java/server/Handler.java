package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.requests.LoginRequest;
import server.requests.RegisterRequest;
import server.responses.LoginResponse;
import server.responses.RegisterResponse;
import service.*;
import spark.*;
import model.*;

public class Handler {
    DataAccess dataAccess;
    UserService userService;
    GameService gameService;

    public Handler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
        this.userService = new UserService(dataAccess);
        this.gameService = new GameService(dataAccess);
    }

    public int getError(String mess){
        if (mess.contains("does not exist")){
            return 401;
        }
        else if (mess.contains("unauthorized")){
            return 401;
        }
        else if (mess.contains("already taken")){
            return 403;
        }
        else if (mess.contains("bad request")){
            return 400;
        }
        else {
            return 500;
        }
    }

    public String objectJson(Object object){
        return new Gson().toJson(object);
    }

    public User jsonUser(String body){
        return new Gson().fromJson(body, User.class);
    }

    public Game jsonGame(String body){return new Gson().fromJson(body, Game.class);}
}


class LoginHandler extends Handler {
    public LoginHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object login(Request req, Response res) throws DataAccessException {
        try {
            LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
            Object object = userService.login(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new LoginResponse(ex.getMessage()));
        }
    }
}


class RegisterHandler extends Handler {
    public RegisterHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object register(Request req, Response res) throws DataAccessException {
        try {
            RegisterRequest request = new Gson().fromJson(req.body(), RegisterRequest.class);
            Object object = userService.register(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new RegisterResponse(ex.getMessage()));
        }
    }
}


class LogoutHandler extends Handler {
    public LogoutHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object logout(Request req, Response res) throws DataAccessException {
        try {
            RegisterRequest request = new Gson().fromJson(req.body(), RegisterRequest.class);
            Object object = userService.register(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new RegisterResponse(ex.getMessage()));
        }
    }
}

class CreateGameHandler extends Handler {
    public CreateGameHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object createGame(Request req, Response res) throws DataAccessException {
        Game game = jsonGame(req.body());
        Object object = gameService.createGame(game);
        return new Gson().toJson(object);
    }
}