import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import dataAccess.MySqlDataAccess;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import server.handlers.requests.*;
import server.handlers.responses.*;
import service.ClearService;
import service.GameService;
import service.UserService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {

    DataAccess da;

    {
        try {
            da = new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    UserService userService = new UserService(da);
    GameService gameService = new GameService(da);
    ClearService clearService = new ClearService(da);

    @BeforeEach
    public void cleanUp() throws TestException, DataAccessException {
        da.clearGames();
    }

    @Test
    @Order(1)
    @DisplayName("Clear Success")
    public void clearTest() throws DataAccessException {
        CreateGameRequest request = new CreateGameRequest("game");
        da.addGame(request);
        Assertions.assertEquals(da.getAllGames().size(), 1);
        clearService.clearAll();
        Assertions.assertEquals(da.getAllGames().size(),0, "does not have len of 0 when cleared");
    }

    @Test
    @Order(2)
    @DisplayName("Register Success")
    public void registerSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("user", "pass", "mail");
        RegisterResponse response = userService.register(request);
        Assertions.assertEquals(response.getUsername(), "user");
    }

    @Test
    @Order(3)
    @DisplayName("Register Fail")
    public void registerFail()throws DataAccessException{
        try {
            RegisterRequest request = new RegisterRequest("user", "pass", "mail");
            request.username = null;
            userService.register(request);
        }
        catch (DataAccessException ex){
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Login Success")
    public void loginSuccess() throws DataAccessException {
        User user = new User("user","pass","mail");
        da.addUser(user);
        LoginRequest request = new LoginRequest("user","pass");
        LoginResponse response = userService.login(request);
        Assertions.assertEquals(response.getUsername(), "user");
    }

    @Test
    @Order(5)
    @DisplayName("Login Fail")
    public void loginFail() throws DataAccessException {
        try{
            User user = new User("user","pass","mail");
            da.addUser(user);
            LoginRequest request = new LoginRequest("user","pass");
            request.username = null;
            LoginResponse response = userService.login(request);
        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @Order(6)
    @DisplayName("Logout Success")
    public void logoutSuccess() throws DataAccessException {
        da.addAuth(new AuthToken("user","auth"));
        LogoutRequest request = new LogoutRequest("auth");
        LogoutResponse response = userService.logout(request);
        Assertions.assertNull(response.getMessage(), "error message was filled");
    }

    @Test
    @Order(7)
    @DisplayName("Logout Fail")
    public void logoutFail() throws DataAccessException {
        try {
            da.addAuth(new AuthToken("user", "auth"));
            LogoutRequest request = new LogoutRequest("bummer");
            LogoutResponse response = userService.logout(request);
        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @Order(8)
    @DisplayName("List Games Success")
    public void listGameSuccess() throws DataAccessException {
        da.addAuth(new AuthToken("user","auth"));
        Assertions.assertEquals(da.getAllGames().size(), 0);
        CreateGameRequest temp = new CreateGameRequest("game");
        da.addGame(temp);
        ListGamesRequest request = new ListGamesRequest("auth");
        ListGamesResponse response = gameService.listGames(request);
        Assertions.assertEquals(response.games.size(), 1);
    }

    @Test
    @Order(9)
    @DisplayName("List Games Fail")
    public void listGameFail() throws DataAccessException {
        try {
            da.addAuth(new AuthToken("user","auth"));
            Assertions.assertEquals(da.getAllGames().size(), 0);
            CreateGameRequest temp = new CreateGameRequest("game");
            da.addGame(temp);
            ListGamesRequest request = new ListGamesRequest("user");
            ListGamesResponse response = gameService.listGames(request);
        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @Order(10)
    @DisplayName("Create Game Success")
    public void createGameSuccess() throws DataAccessException{
        da.addAuth(new AuthToken("user","auth"));
        Assertions.assertEquals(da.getAllGames().size(), 0);
        CreateGameRequest request = new CreateGameRequest("game");
        request.setAuth("auth");
        CreateGameResponse response = gameService.createGame(request);
        Assertions.assertEquals(da.getAllGames().size(), 1);
    }

    @Test
    @Order(11)
    @DisplayName("Create Game Fail")
    public void createGameFail() throws DataAccessException{
        try {
            Assertions.assertEquals(da.getAllGames().size(), 0);
            CreateGameRequest request = new CreateGameRequest("game");
            CreateGameResponse response = gameService.createGame(request);
        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @Order(12)
    @DisplayName("Join Game Success")
    public void joinGameSuccess() throws DataAccessException{
        da.addAuth(new AuthToken("user","auth"));
        CreateGameRequest temp = new CreateGameRequest("game");
        da.addGame(temp);
        Assertions.assertEquals(da.getAllGames().size(), 1);
        JoinGameRequest request = new JoinGameRequest(1);
        request.setAuthorization("auth");
        JoinGameResponse response = gameService.joinGame(request);
        Assertions.assertNull(response.getMessage(),"error message is not null");
    }

    @Test
    @Order(13)
    @DisplayName("Join Game Fail")
    public void joinGameFail() throws DataAccessException{
        try {
            da.addAuth(new AuthToken("user","auth"));
            CreateGameRequest temp = new CreateGameRequest("game");
            da.addGame(temp);
            Assertions.assertEquals(da.getAllGames().size(), 1);
            JoinGameRequest request = new JoinGameRequest(1);
            request.setAuthorization("auth");
            request.playerColor = "";
            JoinGameResponse response = gameService.joinGame(request);
        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }


}
