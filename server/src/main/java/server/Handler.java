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
}

class loginHandler extends Handler {
    public Object login(Request req, Response res) throws HttpResponseException {
        User user = new Gson().fromJson(req.body(), User.class);
        Object object = userService.login(user);
        return new Gson().toJson(object);
    }
}
