package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class PrintChess {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;

    private static boolean backgroundBlack;


    public static void main(String[] args){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessPiece[][] board = new ChessGame().getBoard().getBoard();
        String[] header = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] margin = {"1","2","3","4","5","6","7","8"};
        out.print(ERASE_SCREEN);

        drawBoard(out, header, margin, board);


    }

    public static void drawBoard(PrintStream out, String[] headers, String[] margin, ChessPiece[][] board){
        drawHeaders(out, headers);
        drawRows(out, margin, board);
        drawHeaders(out, headers);
    }

    private static void drawRows(PrintStream out, String[] margin, ChessPiece[][] board) {
        for (int row = 0; row <= board.length - 1; row++){
            drawRow(out, row, margin, board[row]);
        }
    }

    private static void drawRow(PrintStream out, int rowNum, String[] margin, ChessPiece[] row) {
        printCheckerBuffer(out, rowNum);
        printCheckerText(out, rowNum, margin, row);
        printCheckerBuffer(out, rowNum);
    }

    private static void printCheckerBuffer(PrintStream out, int rowNum) {
        printBuffer(out, 1);
        for (int i = 0; i < BOARD_SIZE_IN_SQUARES; i++){
            if (rowNum + i % 2 == 0){
                out.print(SET_BG_COLOR_WHITE);
            }
            else{
                out.print(SET_BG_COLOR_BLACK);
            }
            out.print(EMPTY.repeat(3));
        }
        out.print(SET_BG_COLOR_LIGHT_GREY);
        printBuffer(out, 1);
        out.println();
    }

    private static void printCheckerText(PrintStream out, int rowNum, String[] margin, ChessPiece[] row) {
    }

    private static void drawHeaders(PrintStream out, String[] headers) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        printBufferRow(out);
        printBuffer(out, 1);
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }
        out.println();
        printBufferRow(out);
    }

    private static void printBufferRow(PrintStream out) {
        printBuffer(out, BOARD_SIZE_IN_SQUARES);
        out.println();
    }

    private static void printBuffer(PrintStream out, int amount) {
        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS * amount));
    }

    private static void drawHeader(PrintStream out, String header) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, header);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

    }

}
