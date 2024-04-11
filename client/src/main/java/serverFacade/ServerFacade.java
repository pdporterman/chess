package serverFacade;

import handlers.requests.*;
import handlers.responses.*;

public class ServerFacade {

    private final ClientCommunicator communicator = new ClientCommunicator("http://localhost:3000");

    public LoginResponse login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return communicator.makeRequest("POST", path, request, LoginResponse.class,null);
    }

    public RegisterResponse register(RegisterRequest request) throws ResponseException {
        var path = "/user";
        return communicator.makeRequest("POST", path, request, RegisterResponse.class,null);
    }

    public LogoutResponse logout(LogoutRequest request) throws ResponseException {
        var path = "/session";
        return communicator.makeRequest("DELETE", path, request, LogoutResponse.class, request.getAuthorization());
    }

    public CreateGameResponse createGame(CreateGameRequest request) throws ResponseException {
        var path = "/game";
        return communicator.makeRequest("POST", path, request, CreateGameResponse.class, request.getAuthorization());
    }

    public JoinGameResponse joinGame(JoinGameRequest request) throws ResponseException {
        var path = "/game";
        return communicator.makeRequest("PUT", path, request, JoinGameResponse.class, request.getAuthorization());
    }

    public ListGamesResponse listGame(ListGamesRequest request) throws ResponseException {
        var path = "/game";
        return communicator.makeRequest("GET", path, request, ListGamesResponse.class, request.getAuthorization());
    }

    public void clear() throws ResponseException {
        var path = "/db";
        communicator.makeRequest("DELETE", path, null, ClearResponse.class, null);
    }


}
