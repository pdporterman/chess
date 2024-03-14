package ui;

import static ui.EscapeSequences.*;
import java.util.Scanner;
import serverFacade.ServerFacade;

public class ConsoleUI {

    public static void main(String[] args){
        ServerFacade server = new ServerFacade();
        System.out.println("welcome to chess");

        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (!result.contains("bye")){

            try{
                System.out.println(SET_TEXT_COLOR_WHITE + server.menu());
                String input = scanner.nextLine();
                result = server.eval(input);
                System.out.println(SET_TEXT_COLOR_BLUE + result);
            }
            catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

}
