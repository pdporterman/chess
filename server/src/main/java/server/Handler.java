package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import org.eclipse.jetty.client.HttpResponseException;
import service.UserService;
import spark.*;
import model.User;

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

class loginHandler extends Handler {
    public loginHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    public Object login(Request req, Response res) throws DataAccessException {
        User user = jsonUser(req.body());
        Object object = userService.login(user);
        return new Gson().toJson(object);
    }
}

