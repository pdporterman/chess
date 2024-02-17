package server;

import spark.*;

public class Server {

    loginHandler handlerLogin;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        if (!handlerLogin){
            handlerLogin = new loginHandler();
        }
        Spark.post("/session",handlerLogin.login());

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
