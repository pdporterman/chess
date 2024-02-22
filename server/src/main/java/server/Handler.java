package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.handlers.requests.*;
import server.handlers.responses.ListGamesResponse;
import server.handlers.responses.LoginResponse;
import server.handlers.responses.LogoutResponse;
import server.handlers.responses.RegisterResponse;
import server.handlers.responses.*;
import server.handlers.responses.*;
import service.*;
import spark.*;
import model.*;

import java.util.Collection;

public class Handler {
    DataAccess dataAccess;
    UserService userService;
    GameService gameService;

    ClearService clearService;

    public Handler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
        this.userService = new UserService(dataAccess);
        this.gameService = new GameService(dataAccess);
        this.clearService = new ClearService(dataAccess);
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
            LogoutRequest request = new LogoutRequest(req.headers("authorization"));
            Object object = userService.logout(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new LogoutResponse(ex.getMessage()));
        }
    }
} // how do you get the HTTP head to access the auth token

class ListGamesHandler extends Handler {
    public ListGamesHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object listGames(Request req, Response res) throws DataAccessException {
        try {
            ListGamesRequest request = new Gson().fromJson(req.body(), ListGamesRequest.class);
            Collection<Game> object = gameService.listGames(request);
            ListGamesResponse response = new ListGamesResponse(object);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new ListGamesResponse(ex.getMessage()));
        }
    }
}

class CreateGameHandler extends Handler {
    public CreateGameHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object createGame(Request req, Response res) throws DataAccessException {
        try {
            CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
            Object object = gameService.createGame(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new RegisterResponse(ex.getMessage()));
        }
    }
}

class ClearHandler extends Handler {
    public ClearHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

//    public Object clear(Request req, Response res) throws DataAccessException {
//        try {
//            ClearRequest request = new Gson().fromJson(req.body(), ClearRequest.class);
//            Object object = clearService.clearAll(request);
//            res.status(200);
//            return new Gson().toJson(object);
//        }
//        catch (DataAccessException ex){
//            res.status(getError(ex.getMessage()));
//            return new Gson().toJson(new RegisterResponse(ex.getMessage()));
//        }
//    }
}
