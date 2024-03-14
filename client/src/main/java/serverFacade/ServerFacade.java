package serverFacade;

public class ServerFacade {

    private Boolean token = false;

    public String login(){
        System.out.print("logged in");
        token = true;
        return "keep going";
    }
    public String register(){
        System.out.print("user created");
        token = true;
        return "keep going";
    }
    public String logout(){
        System.out.print("logged out");
        token = true;
        return "keep going";
    }
    public String createGame(){
        System.out.print("game created");
        return "keep going";
    }
    public String joinGame(){
        System.out.print("joined game");
        return "keep going";
    }
    public String listGame(){
        System.out.print("here are the games");
        return "keep going";
    }
    public String clear(){
        System.out.print("cleared");
        return "keep going";
    }


    public String eval(String input){
        String word = input.toLowerCase();
        return switch (word){
            case "login" -> login();
            case "regiser" -> register();
            case "logout" -> logout();
            case "create game" -> createGame();
            case "join game" -> joinGame();
            case "list games" -> listGame();
            case "clear" -> clear();
            case "quit" -> "quit";
            default -> "invalid input";
        };
    }
}
