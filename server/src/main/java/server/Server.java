package server;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import spark.*;

public class Server {

    DataAccess da = new MemoryDataAccess();

    loginHandler handlerLogin = new loginHandler(da);

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/session",(Request req, Response res) -> handlerLogin.login(req, res));//login
//        Spark.post("/user",);//reg
//        Spark.delete("/session",);//log out
//        Spark.get("/game",);//list game
//        Spark.post("/game",);//create game
//        Spark.put("/game",);//join game
//        Spark.delete("/db",);//clear

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
