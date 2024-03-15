package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import server.handlers.requests.LoginRequest;
import server.handlers.responses.LoginResponse;
import serverFacade.ServerFacade;

import java.io.IOException;
import java.net.MalformedURLException;


public class ServerFacadeTests {

    private static Server server;
    private ServerFacade facade = new ServerFacade();

    public ServerFacadeTests() throws MalformedURLException {
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
    public void loginSuccess() throws IOException {
        try {
            LoginResponse response = facade.login(new LoginRequest("user", "pass"));
            Assertions.assertTrue(true);
        } catch (IOException e) {
            Assertions.fail();
        }
    }

}
