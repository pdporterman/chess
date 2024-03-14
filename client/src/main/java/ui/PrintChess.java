package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static ui.EscapeSequences.*;

public class PrintChess {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;


    public static void main(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessGame game = new ChessGame();
        ArrayList<String> header = new String[{"a", "b", "c", "d", "e", "f", "g", "h"}];
        out.print(ERASE_SCREEN);


        drawBoard(, );


    }

    public static void drawBoard(ArrayList<String> headers, ArrayList<String> margin, ChessGame game){
        drawHead(headers);
        drawRow(margin, game);
        drawHead(headers);
    }

    private static void drawRow(ArrayList<String> margin, ChessGame game) {
    }

    private static void drawHead(ArrayList<String> headers) {
    }


}
