package server;

import com.google.gson.Gson;

public class Handler {
    private final Gson translator = new Gson();

    public String objectJson(Object object){
        return translator.toJson(object);

    }

    public
}
