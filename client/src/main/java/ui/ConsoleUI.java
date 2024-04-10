package ui;

import static ui.EscapeSequences.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

import chess.*;
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

    private String role = null;
    private Integer gameNumber = null;



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
            gameNumber = Integer.parseInt(gameid);
            role = "observer";
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
            gameNumber = Integer.parseInt(gameid);
            role = "player";
            return SET_BG_COLOR_BLACK + "joined game as " + color;
        } catch (Exception e) {
            return "failed to join game (" + e.toString() + ")";
        }
    }
    public String leaveGame(){
        try {
            System.out.print("Leave the game (Y/N): ");
            String confirm = scanner.next();
            if (Objects.equals(confirm, "Y")){
                websocket.leaveGame(token, gameNumber);
                return "left game";
            }
            else {
                return "canceled";
            }
        } catch (exception.ResponseException e) {
            return "failed to leave game";
        }
    }
    public String resignGame(){
        try {
            System.out.print("Resign the game (Y/N): ");
            String confirm = scanner.next();
            if (Objects.equals(confirm, "Y")){
                websocket.resign(token, gameNumber);
                return "left game";
            }
            else {
                return "canceled";
            }
        } catch (exception.ResponseException e) {
            return "failed to leave game";
        }
    }

    public String redraw(){
        printer.displayBoard(game, boardside, false);
        return "here is the board";
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



    public String makeMove(){
        try {
            System.out.print("enter position 'row col'");
            System.out.print("start position: ");
            String startString = scanner.next();
            var list1 = startString.split(" ");
            if (list1.length != 2){
                return "invalid start position";
            }
            ChessPosition start = new ChessPosition(list1[0].charAt(0) - 'a', Integer.parseInt(list1[1]));
            ChessPiece peice = game.getBoard().getPiece(start);
            if (peice == null || peice.getTeamColor() != boardside){
                return "no team peice";
            }
            System.out.print("end position: ");
            String endString = scanner.next();
            var list2 = endString.split(" ");
            if (list2.length != 2){
                return "invalid end position";
            }
            ChessPiece.PieceType promotion = null;
            if (peice.getPieceType() == ChessPiece.PieceType.PAWN && list2[0].charAt(0) - 'a' == 8){
                System.out.print("promote to which piece?\nQ : queen\nK : kight\nB : bishop\nR : rook");
                String pro = scanner.next();
                promotion = proType(pro);
                if (promotion == null){
                    return "cannot promote to" + pro;
                }
            }
            ChessPosition end = new ChessPosition(list2[0].charAt(0) - 'a', Integer.parseInt(list2[1]));
            ChessMove move = new ChessMove(start, end, promotion);
            game.makeMove(move);
            websocket.makeMove(token, gameNumber, move);
            return "move successful";
        } catch (NumberFormatException e) {
            return "could not use position";
        } catch (InvalidMoveException e) {
            return "not a valid move";
        } catch (exception.ResponseException e) {
            return "error reaching server";
        }
    }
    private ChessPiece.PieceType proType(String pro) {
        return switch (pro) {
            case "Q" -> ChessPiece.PieceType.QUEEN;
            case "R" -> ChessPiece.PieceType.ROOK;
            case "B" -> ChessPiece.PieceType.BISHOP;
            case "K" -> ChessPiece.PieceType.KNIGHT;
            default -> null;
        };
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
            case "redraw board" -> redraw();
            case "make move" -> makeMove();
            case "leave" -> leaveGame();
            case "resign" -> resignGame();
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
        else if (game != null && Objects.equals(role, "player")){
            return SET_BG_COLOR_DARK_GREY + """
                - make move
                - highlight moves
                - redraw board
                - resign
                - leave
                - help
                """;
        }
        else if (game != null && Objects.equals(role, "observer")){
            return SET_BG_COLOR_DARK_GREY + """
                - redraw board
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
