package serverFacade;

import com.google.gson.Gson;
import model.AuthToken;
import model.User;
import server.handlers.requests.*;
import server.handlers.responses.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class ServerFacade {

    private String token = "no good";
    private ClientCommunicator communicator = new ClientCommunicator("http://localhost:3000");

    public LoginResponse login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return communicator.makeRequest("POST", path, request, LoginResponse.class);
    }

    public RegisterResponse register(RegisterRequest request) throws ResponseException {
        var path = "/user";
        return communicator.makeRequest("POST", path, request, RegisterResponse.class);
    }
    public LogoutResponse logout(LogoutRequest request) throws ResponseException {
        var path = "/session";
        return communicator.makeRequest("DELETE", path, request, LogoutResponse.class);
    }
    public CreateGameResponse createGame(CreateGameRequest request) throws ResponseException {
        var path = "/game";
        return communicator.makeRequest("POST", path, request, CreateGameResponse.class);
    }
    public JoinGameResponse joinGame(JoinGameRequest request) throws ResponseException {
        var path = "/game";
        return communicator.makeRequest("PUT", path, request, JoinGameResponse.class);
    }
    public ListGamesResponse listGame(ListGamesRequest  request) throws ResponseException {
        var path = "/session";
        return communicator.makeRequest("GET", path, request, ListGamesResponse.class);
    }
    public ClearResponse clear() throws ResponseException {
        var path = "/db";
        return communicator.makeRequest("DELETE", path, null, ClearResponse.class);
    }


}
