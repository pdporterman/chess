package ui;

import static ui.EscapeSequences.*;
import java.util.Scanner;
import serverFacade.ServerFacade;

public class ConsoleUI {
    ServerFacade server;

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.contains("quit")){
            String line = scanner.nextLine();
            try{
                input = server.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + input);
            }
            catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

}
