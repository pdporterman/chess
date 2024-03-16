package clientTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;
import server.handlers.requests.*;
import server.handlers.responses.*;
import serverFacade.ResponseException;
import serverFacade.ServerFacade;

import java.io.IOException;
import java.net.MalformedURLException;


public class ServerFacadeTests {

    private static Server server;
    private ServerFacade facade = new ServerFacade();

    public ServerFacadeTests() throws MalformedURLException {
    }

    @BeforeEach
    public void cleanUp() throws ResponseException {
        facade.clear();
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(3000);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void loginSuccess() {
        try {
            facade.register(new RegisterRequest("user","pass","email"));
            LoginResponse response = facade.login(new LoginRequest("user", "pass"));
            Assertions.assertNotNull(response);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void loginFail(){
        try {
            LoginResponse response = facade.login(new LoginRequest("user", "pass"));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void logoutSuccess(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            LogoutResponse response = facade.logout(new LogoutRequest(temp.getAuthToken()));
            Assertions.assertNotNull(response);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
    @Test
    public void logoutFail(){
        try {
            LogoutResponse response = facade.logout(new LogoutRequest("auth"));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("401"));
        }
    }

    @Test
    public void registerSuccess() {
        try {
            RegisterResponse response = facade.register(new RegisterRequest("user","pass","email"));
            Assertions.assertNotNull(response);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void registerFail() {
        try {
            RegisterResponse response = facade.register(new RegisterRequest("user","pass","email"));
            facade.register(new RegisterRequest("user","pass","email"));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(true);

        }
    }

    @Test
    public void createGameSuccess(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            CreateGameRequest request = new CreateGameRequest("game");
            request.setAuth(temp.getAuthToken());
            CreateGameResponse response = facade.createGame(request);
            Assertions.assertNotNull(response);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void createGameFail(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            CreateGameRequest request = new CreateGameRequest("game");
            CreateGameResponse response = facade.createGame(request);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void joinGameSuccess(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            CreateGameRequest request = new CreateGameRequest("game");
            request.setAuth(temp.getAuthToken());
            CreateGameResponse temp2 = facade.createGame(request);
            JoinGameResponse response = facade.joinGame(new JoinGameRequest(temp2.getGameID()));
            Assertions.assertNotNull(response);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void joinGameFail(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            CreateGameRequest request = new CreateGameRequest("game");
            request.setAuth(temp.getAuthToken());
            CreateGameResponse temp2 = facade.createGame(request);
            JoinGameResponse response = facade.joinGame(new JoinGameRequest(100));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void listGameSuccess(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            CreateGameRequest game1 = new CreateGameRequest("game");
            game1.setAuth(temp.getAuthToken());
            CreateGameRequest game2 = new CreateGameRequest("game");
            game2.setAuth(temp.getAuthToken());
            CreateGameResponse temp2 = facade.createGame(game1);
            CreateGameResponse temp3 = facade.createGame(game2);
            ListGamesResponse response = facade.listGame(new ListGamesRequest(temp.getAuthToken()));
            Assertions.assertEquals(response.getGames().size(),2);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void listGameFail(){
        try {
            RegisterResponse temp = facade.register(new RegisterRequest("user","pass","email"));
            CreateGameRequest game1 = new CreateGameRequest("game");
            game1.setAuth(temp.getAuthToken());
            CreateGameRequest game2 = new CreateGameRequest("game");
            game2.setAuth(temp.getAuthToken());
            CreateGameResponse temp2 = facade.createGame(game1);
            CreateGameResponse temp3 = facade.createGame(game2);
            ListGamesResponse response = facade.listGame(new ListGamesRequest("bad auth"));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }
}
