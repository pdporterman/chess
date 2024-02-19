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

    public Handler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
        this.userService = new UserService(dataAccess);
    }

    public String objectJson(Object object){
        return new Gson().toJson(object);
    }

    public User jsonUser(String body){
        return new Gson().fromJson(body, User.class);
    }
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