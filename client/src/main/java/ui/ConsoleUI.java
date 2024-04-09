package ui;

import static ui.EscapeSequences.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import server.handlers.requests.*;
import server.handlers.responses.*;
import serverFacade.ResponseException;
import serverFacade.ServerFacade;
import webSocket.NotificationHandler;
import webSocket.WebSocketFacade;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

public class ConsoleUI implements NotificationHandler {
    private String token = null;

    private ChessGame.TeamColor boardside = null;
    private ChessGame game = null;
    private final PrintChess printer = new PrintChess();
    private final ServerFacade server = new ServerFacade();
    private final Scanner scanner = new Scanner(System.in);

    private final WebSocketFacade websocket = new WebSocketFacade("http://localhost:3000", this);

    public ConsoleUI() throws exception.ResponseException {
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
        try {
            System.out.print("please enter username: ");
            String user = scanner.next();
            System.out.print("please enter password: ");
            String pass = scanner.next();
            System.out.print("please enter email: ");
            String email = scanner.next();
            RegisterResponse response = server.register(new RegisterRequest(user, pass, email));
            token = response.getAuthToken();
            return "user created and logged in";
        } catch (Exception e) {
            return "failed to create user (" + e.toString() + ")";
        }
    }
    public String logout(){
        try {
            LogoutResponse response = server.logout(new LogoutRequest(token));
            token = null;
            return "logged out";
        } catch (ResponseException e) {
            return "failed to log out (" + e.toString() + ")";
        }
    }
    public String createGame(){
        try {
            System.out.print("please enter a game name: ");
            String name = scanner.next();
            CreateGameRequest request = new CreateGameRequest(name);
            request.setAuth(token);
            CreateGameResponse response = server.createGame(request);
            return "game created";
        } catch (Exception e) {
            return "failed to create game (" + e.toString() + ")";
        }
    }
    private String observeGame() {
        try {
            System.out.print("please enter a gameID number: ");
            String gameid = scanner.next();
            JoinGameRequest request = new JoinGameRequest(Integer.parseInt(gameid));
            request.playerColor = null;
            request.setAuthorization(token);
            JoinGameResponse response = server.joinGame(request);
            boardside = ChessGame.TeamColor.WHITE;
            websocket.joinGame(token, Integer.parseInt(gameid), null);
            return SET_BG_COLOR_BLACK + "observing game";
        } catch (Exception e) {
            return "failed to join game (" + e.toString() + ")";
        }
    }

    public String joinGame(){
        try {
            System.out.print("please enter a gameID number: ");
            String gameid = scanner.next();
            System.out.print("please enter color (WHITE / BLACK): ");
            String color = scanner.next();
            JoinGameRequest request = new JoinGameRequest(Integer.parseInt(gameid));
            request.playerColor = color;
            request.setAuthorization(token);
            boardside = (Objects.equals(color, "BLACK")) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
            JoinGameResponse response = server.joinGame(request);
            websocket.joinGame(token, Integer.parseInt(gameid), boardside);
            return SET_BG_COLOR_BLACK + "joined game as " + color;
        } catch (Exception e) {
            return "failed to join game (" + e.toString() + ")";
        }
    }

    public String listGame(){
        try {
            ListGamesResponse response = server.listGame(new ListGamesRequest(token));
            return "these are the games\n" + response.gamesToString();
        } catch (Exception e) {
            return "failed to get games (" + e.getMessage() + ")";
        }
    }
    public String clear(){
        try {
            System.out.print("please enter password to clear: ");
            String pass = scanner.next();
            if (Objects.equals(pass, "doTheThing")){
                server.clear();
                return "cleared DB";
            }
            return "incorrect Password";
        } catch (Exception e) {
            return "failed clear DB (" + e.toString() + ")";
        }
    }

    public void makeMove(){
        System.out.print("enter positions with like 'row col'");
        System.out.print("start position: ");
        String startString = scanner.next();
        System.out.print("end position: ");
        String endString = scanner.next();
        var list1 = startString.split(" ");
        var list2 = endString.split(" ");

        ChessPosition start = new ChessPosition(list1[0].charAt(0) - 'a', Integer.parseInt(list1[1]));
        ChessPosition end = new ChessPosition(list2[0].charAt(0) - 'a', Integer.parseInt(list2[1]));
        ChessMove move = new ChessMove(start, end);
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
        if (Objects.equals(token, null)) {
            return SET_BG_COLOR_DARK_GREY + """
                    - login
                    - register
                    - help
                    - quit
                    """;
        }
        else if (game != null){
            return SET_BG_COLOR_DARK_GREY + """
                - make move
                - highlight moves
                - redraw board
                - resign
                - leave
                - help
                """;
        }
        else{
            return SET_BG_COLOR_DARK_GREY + """
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

    @Override
    public void notify(String message){
        ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
        switch (notification.getServerMessageType()){
            case LOAD_GAME -> loadGame((new Gson().fromJson(message, LoadGameMessage.class)));
            case ERROR -> handleError((new Gson().fromJson(message, ErrorMessage.class)));
            case NOTIFICATION -> note((new Gson().fromJson(message, Notification.class)));
        }
    }

    private void note(Notification message) {
        System.out.print(message.getMessage());
    }

    private void handleError(ErrorMessage message) {
        System.out.print(message.getErrorMessage());
    }

    private void loadGame(LoadGameMessage message) {
        game = message.getGame();
        printer.displayBoard(game, boardside, false);
    }
}
