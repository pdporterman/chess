package ui;

import static ui.EscapeSequences.*;

import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Scanner;

import model.AuthToken;
import server.handlers.requests.LoginRequest;
import server.handlers.responses.LoginResponse;
import server.handlers.responses.LogoutResponse;
import serverFacade.ServerFacade;

public class ConsoleUI {
    private String token = null;
    private final ServerFacade server = new ServerFacade();

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI() throws MalformedURLException {
    }

    public String login(){
        try {
            System.out.print("please enter username: ");
            String user = scanner.next();
            System.out.print("please enter password: ");
            String pass = scanner.next();
            LoginResponse response = server.login(new LoginRequest(user, pass));
            token = response.getAuthToken();
            return "logged in";
        } catch (Exception e) {
            return "failed to log in";
        }
    }
    public String register(){
        System.out.println("user created");

        return "keep going";
    }
    public String logout(){
        System.out.println("logged out");

        return "keep going";
    }
    public String createGame(){
        System.out.println("game created");
        return "keep going";
    }
    private String observeGame() {
        return "aahhh";
    }
    public String joinGame(){
        System.out.println("joined game");
        return "keep going";
    }
    public String listGame(){
        System.out.println("here are the games");
        return "keep going";
    }
    public String clear(){
        System.out.println("cleared");
        return "keep going";
    }


    public String eval(String input){
        String word = input.toLowerCase();
        return switch (word){
            case "login" -> login();
            case "register" -> register();
            case "logout" -> logout();
            case "create game" -> createGame();
            case "join game" -> joinGame();
            case "observe game" -> observeGame();
            case "list games" -> listGame();
            case "clear" -> clear();
            case "quit" -> "good bye";
            case "help" -> "just type out the action you wish to take, for example to log in type 'login'";
            default -> "invalid input, please enter response matching available options";
        };
    }



    public String menu() {
        if (!Objects.equals(token, "no good")) {
            return """
                    - login
                    - register
                    - help
                    - quit
                    """;
        }
        return """
                - logout
                - create game
                - join game
                - observe game
                - list games
                - help
                - quit
                """;
    }

}
