package server;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import spark.*;

public class Server {

    DataAccess da = new MemoryDataAccess();

    LoginHandler handlerLogin = new LoginHandler(da);
    RegisterHandler registerHandler = new RegisterHandler(da);

    CreateGameHandler createGameHandler = new CreateGameHandler(da);

    LogoutHandler logoutHandler = new LogoutHandler(da);

    ListGamesHandler listGamesHandler = new ListGamesHandler(da);

    public static void main(String[] args){
        try{
            int port = Integer.parseInt(args[0]);
            Server temp = new Server();
            temp.run(port);
        }
        catch (Exception exp){
            System.err.println(exp.getMessage());
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/session",(Request req, Response res) -> handlerLogin.login(req, res));//login
        Spark.post("/user",(Request req, Response res)-> registerHandler.register(req, res));//reg
        Spark.delete("/session",(Request req, Response res)-> logoutHandler.logout(req, res));//log out
        Spark.get("/game",(Request req, Response res)-> listGamesHandler.listGames(req, res));//list game
        Spark.post("/game",(Request req, Response res)-> createGameHandler.createGame(req, res));//create game
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
