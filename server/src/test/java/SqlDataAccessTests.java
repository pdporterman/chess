import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import server.handlers.requests.CreateGameRequest;
import service.ClearService;
import service.GameService;
import service.UserService;

public class SqlDataAccessTests {

    DataAccess da;

    {
        try {
            da = new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
        da.clearGames();
        da.clearAuth();
        da.clearUsers();
        Assertions.assertEquals(da.getAllGames().size(),0, "does not have len of 0 when cleared");
    }

    @Test
    @Order(2)
    @DisplayName("add and get user success")
    public void userSuccessTest() throws DataAccessException {
        da.addUser(new User("user", "pass", "mail"));
        User user = da.getUser("user", "pass");
        Assertions.assertEquals(user.getUserName(), "user");
    }

    @Test
    @Order(3)
    @DisplayName("add and get user fail")
    public void userFailTest() throws DataAccessException {
        try {
            da.addUser(new User("user", "pass", "mail"));
            User user = da.getUser("username", "pass");
        }
        catch (DataAccessException ex){
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @Order(4)
    @DisplayName("add check and delete success")
    public void authSuccessTest() throws DataAccessException {
        da.addAuth(new AuthToken("user", "token"));
        if (da.checkAuth("token")){
            AuthToken auth = da.getAuth("token");
            Assertions.assertEquals(auth.getUsername(), "user");
            da.deleteAuth(auth.getToken());
            Assertions.assertFalse(da.checkAuth("token"));
        }
    }

    @Test
    @Order(4)
    @DisplayName("add check and delete auth fail")
    public void authSuccessFail() throws DataAccessException {
        try {
            da.addAuth(new AuthToken("user", "token"));
            if (da.checkAuth("auth")) {
                AuthToken auth = da.getAuth("token");
                da.deleteAuth(auth.getToken());
            }
        } catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"), "needs to throw error");
        }
    }



}
