package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import dataAccess.MySqlDataAccess;
import model.Game;
import server.handlers.requests.*;
import server.handlers.responses.*;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

import java.util.Collection;

public class Server {

    DataAccess da;
    UserService userService;
    GameService gameService;
    ClearService clearService;

    public Server(){
        try {
            this.da = new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        this.userService = new UserService(da);
        this.gameService = new GameService(da);
        this.clearService = new ClearService(da);
    }
    public static void main(String[] args){
        try{
            int port = 3000;
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
        Spark.post("/session",this::login);//login
        Spark.post("/user",this::register);//reg
        Spark.delete("/session", this::logout);//log out
        Spark.get("/game",this::listGames);//list game
        Spark.post("/game",this::createGame);//create game
        Spark.put("/game",this::joinGame);//join game
        Spark.delete("/db",this::clear);//clear

        Spark.awaitInitialization();
        return Spark.port();
    }

    private int getError(String mess){
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

    private Object login(Request req, Response res)  {
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

    private Object logout(Request req, Response res)  {
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

    private Object register(Request req, Response res)  {
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

    private Object listGames(Request req, Response res)  {
        try {
            ListGamesRequest request = new ListGamesRequest(req.headers("authorization"));
            Object object = gameService.listGames(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new ListGamesResponse(ex.getMessage()));
        }
    }

    private Object createGame(Request req, Response res)  {
        try {
            CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
            request.setAuth(req.headers("authorization"));
            Object object = gameService.createGame(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new CreateGameResponse(ex.getMessage()));
        }
    }

    private Object joinGame(Request req, Response res) {
        try {
            JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
            request.setAuthorization(req.headers("authorization"));
            Object object = gameService.joinGame(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            ex.printStackTrace();
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new JoinGameResponse(ex.getMessage()));
        }
    }

    private Object clear(Request req, Response res){
        try {
            Object object = clearService.clearAll();
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException ex){
            res.status(getError(ex.getMessage()));
            return new Gson().toJson(new ClearResponse(ex.getMessage()));
        }
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
