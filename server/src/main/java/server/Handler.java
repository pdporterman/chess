package server;

import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpResponseException;
import service.UserService;
import spark.*;
import model.User;

public class Handler {
    UserService userService = new UserService();

    public String objectJson(Object object){
        return new Gson().toJson(object);
    }

    public User jsonUser(String body){
        return new Gson().fromJson(body, User.class);
    }
}

class loginHandler extends Handler {
    public Object login(Request req, Response res) throws HttpResponseException {
        User user = jsonUser(req.body());
        Object object = userService.login(user);
        return new Gson().toJson(object);
    }
}
