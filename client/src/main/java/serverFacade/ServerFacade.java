package serverFacade;

public class ServerFacade {

    private Boolean token = false;

    public String login(){
        System.out.println("logged in");
        token = true;
        return "keep going";
    }
    public String register(){
        System.out.println("user created");
        token = true;
        return "keep going";
    }
    public String logout(){
        System.out.println("logged out");
        token = true;
        return "keep going";
    }
    public String createGame(){
        System.out.println("game created");
        return "keep going";
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
            case "list games" -> listGame();
            case "clear" -> clear();
            case "quit" -> "good bye";
            case "help" -> "just type out the action you wish to take, for example to log in type 'login'";
            default -> "invalid input, please enter response matching available options";
        };
    }

    public String menu() {
        if (!token) {
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
                - list games
                - help
                - quit
                """;
    }

}
