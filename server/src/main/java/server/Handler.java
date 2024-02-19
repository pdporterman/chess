package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
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
        User user = jsonUser(req.body());
        Object object = userService.login(user);
        return new Gson().toJson(object);
    }
}

class RegisterHandler extends Handler {
    public RegisterHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object register(Request req, Response res) throws DataAccessException {
        User user = jsonUser(req.body());
        Object object = userService.register(user);
        return new Gson().toJson(object);
    }
}

class LogoutHandler extends Handler {
    public LogoutHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object register(Request req, Response res) throws DataAccessException {
        User user = jsonUser(req.body());
        Object object = userService.register(user);
        return new Gson().toJson(object);
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