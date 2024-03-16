import chess.*;
import serverFacade.ServerFacade;
import ui.*;

import java.net.MalformedURLException;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        System.out.println("welcome to chess");
        Scanner scanner = new Scanner(System.in);
        String result = "";

        while (!result.contains("bye")){

            try{
                System.out.println(SET_TEXT_COLOR_WHITE + consoleUI.menu());
                String input = scanner.nextLine();
                result = consoleUI.eval(input);
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