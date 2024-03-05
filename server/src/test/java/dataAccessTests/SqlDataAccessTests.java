package dataAccessTests;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MySqlDataAccess;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import server.handlers.requests.CreateGameRequest;

import java.util.Collection;

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
        da.clearAuth();
        da.clearUsers();
    }

    @Test
    @DisplayName("Clear Success")
    public void clearGameTest() throws DataAccessException {
        CreateGameRequest request = new CreateGameRequest("game");
        da.addGame(request);
        Assertions.assertEquals(da.getAllGames().size(), 1);
        da.clearGames();
        Assertions.assertEquals(da.getAllGames().size(),0, "does not have len of 0 when cleared");
    }

    @Test
    @DisplayName("Clear User Success")
    public void clearUserTest() throws DataAccessException {
        da.addUser(new User("user", "password", "email"));
        da.clearUsers();
        User user = da.getUser("user", "password");
        Assertions.assertNull(user);
    }

    @Test
    @DisplayName("Clear Success")
    public void clearAuthTest() throws DataAccessException {
        da.addAuth(new AuthToken("user", "token"));
        da.clearAuth();
        Assertions.assertFalse(da.checkAuth("token"));
    }

    @Test
    @DisplayName("add and get user success")
    public void userSuccessTest() throws DataAccessException {
        da.addUser(new User("user", "pass", "mail"));
        User user = da.getUser("user", "pass");
        Assertions.assertEquals(user.getUserName(), "user");
    }

    @Test
    @DisplayName("add and get user fail")
    public void userFailTest() throws DataAccessException {
        try {
            da.addUser(new User("user", "pass", "mail"));
            User user = da.getUser("username", "pass");
            if (user != null){
                Assertions.fail();
            }
        }
        catch (DataAccessException ex){
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @DisplayName("check success")
    public void authCheckSuccessTest() throws DataAccessException {
        da.addAuth(new AuthToken("user", "token"));
        Assertions.assertTrue(da.checkAuth("token"));
    }

    @Test
    @DisplayName("check fail")
    public void authCheckFailTest() throws DataAccessException {
        try {
            if (da.checkAuth("token")){
                Assertions.fail();
            }
        }
        catch (DataAccessException ex){
            Assertions.assertTrue(ex.getMessage().contains("Error"),"needs to throw error");
        }
    }

    @Test
    @DisplayName("auth get success")
    public void authGetSuccessTest() throws DataAccessException {
        da.addAuth(new AuthToken("user", "token"));
        AuthToken auth = da.getAuth("token");
        Assertions.assertEquals(auth.getUsername(), "user");
    }

    @Test
    @DisplayName("auth delete success")
    public void authDeleteSuccessTest() throws DataAccessException {
        da.addAuth(new AuthToken("user", "token"));
        AuthToken auth = da.getAuth("token");
        Assertions.assertEquals(auth.getUsername(), "user");
        da.deleteAuth(auth.getToken());
        Assertions.assertFalse(da.checkAuth("token"));
    }




    @Test
    @DisplayName("add check and delete auth fail")
    public void authFailTest() throws DataAccessException {
        try {
            da.addAuth(new AuthToken("user", "token"));
            if (da.checkAuth("auth")) {
                AuthToken auth = da.getAuth("token");
                da.deleteAuth(auth.getToken());
            }
        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"), "needs to throw error");
        }
    }

    @Test
    @DisplayName("add list get and set player for game success")
    public void gameSuccessTest() throws DataAccessException {
        Game game = da.addGame(new CreateGameRequest("game"));
        Collection<Game> list = da.getAllGames();
        Assertions.assertEquals(list.size(), 1);
        da.setPlayer("user", "BLACK",game);
        Game temp = da.getGame(game.getGameID());
        Assertions.assertEquals(temp.getBlackUsername(), "user");
    }

    @Test
    @DisplayName("add list get and set player for game fail")
    public void gameFailTest() throws DataAccessException {
        try {
            Game game = da.addGame(new CreateGameRequest("game"));
            Collection<Game> list = da.getAllGames();
            Assertions.assertEquals(list.size(), 1);
            da.setPlayer("user", "BLACK", game);
            Game temp = da.getGame(5);

        }
        catch (DataAccessException ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error"), "needs to throw error");
        }
    }




}
