package clientTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;
import server.handlers.requests.LoginRequest;
import server.handlers.requests.RegisterRequest;
import server.handlers.responses.LoginResponse;
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

}
