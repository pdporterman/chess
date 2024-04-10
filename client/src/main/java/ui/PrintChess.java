package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class PrintChess {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;


    public static void main(String[] args){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessPiece[][] board = new ChessGame().getBoard().getBoard();
        ChessPiece[][] other = flip(board);
        out.print(ERASE_SCREEN);

        drawBoard(out, new String[]{"H", "G", "F", "E", "D", "C", "B", "A"}, new String[]{"1", "2", "3", "4", "5", "6", "7", "8"}, board);
        out.println(SET_BG_COLOR_BLACK);
        drawBoard(out, new String[]{"A", "B", "C", "D", "E", "F", "G", "H"}, new String[]{"8", "7", "6", "5", "4", "3", "2", "1"}, other);

    }

    public void displayBoard(ChessGame game, ChessGame.TeamColor color, boolean highlight, ChessPosition pos){

        ChessPiece[][] grid = game.getBoard().getBoard();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        int[][] highlights = null;
        if (highlight){
            highlights = highlightGrid(game,grid, pos);
        }        if (color == ChessGame.TeamColor.BLACK){
            ChessPiece[][] other = flip(grid);
            drawBoard(out, new String[]{"A", "B", "C", "D", "E", "F", "G", "H"}, new String[]{"8", "7", "6", "5", "4", "3", "2", "1"}, other);
            out.print(CLEAR_BACKGROUND);
        }
        else{
            drawBoard(out, new String[]{"H", "G", "F", "E", "D", "C", "B", "A"}, new String[]{"1", "2", "3", "4", "5", "6", "7", "8"}, grid);
            out.print(CLEAR_BACKGROUND);
        }
        out.print(ERASE_SCREEN);
    }

    private int[][] highlightGrid(ChessGame game, ChessPiece[][] grid, ChessPosition pos) {
        int[][] highlights = new int[8][8];
        var moves = game.validMoves(pos);
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                ChessPosition temp = new ChessPosition(i,j);
                for (var move : moves){
                    if (pos.equals(temp)){
                        highlights[i][j] = 2;
                    }
                    else if (move.getEndPosition().equals(temp)){
                        highlights[i][j] = 1;
                    }
                }
            }
        }
        return highlights;
    }

    public static ChessPiece[][] flip(ChessPiece[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        ChessPiece[][] rotatedArray = new ChessPiece[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedArray[i][j] = array[rows - 1 - i][cols - 1 - j];
            }
        }

        return rotatedArray;
    }

    public static void drawBoard(PrintStream out, String[] headers, String[] margin, ChessPiece[][] board){
        drawHeaders(out, headers);
        drawRows(out, margin, board);
        drawHeaders(out, headers);
    }

    private static void drawRows(PrintStream out, String[] margin, ChessPiece[][] board) {
        for (int row = 0; row <= board.length - 1; row++){
            printCheckerText(out, row, margin, board[row]);
        }
    }

    private static void printCheckerText(PrintStream out, int rowNum, String[] margin, ChessPiece[] row) {
        out.print(SET_TEXT_COLOR_WHITE);
        out.print(" ");
        out.print(margin[rowNum]);
        out.print(" ");
        for (int i = 0; i < BOARD_SIZE_IN_SQUARES; i++){
            if ((rowNum + i) % 2 == 0){
                out.print(RESET_BG_COLOR);
                out.print(SET_BG_COLOR_WHITE);
            }
            else{
                out.print(RESET_BG_COLOR);
                out.print(SET_BG_COLOR_BLACK);
            }
            out.print(" ");
            out.print(piece(row[i]));
            out.print(" ");
        }
        out.print(RESET_BG_COLOR);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print(" ");
        out.print(margin[rowNum]);
        out.print(" ");
        out.println();
    }

    private static String piece(ChessPiece chessPiece) {
        if (chessPiece == null) {
            return " ";
        } else {
            ChessGame.TeamColor color = chessPiece.getTeamColor();
            ChessPiece.PieceType type = chessPiece.getPieceType();
            String t = switch (type) {
                case KING -> "K";
                case QUEEN -> "Q";
                case BISHOP -> "B";
                case KNIGHT -> "N";
                case ROOK -> "R";
                case PAWN -> "P";
            };
            String c = switch (color) {
                case WHITE -> SET_TEXT_COLOR_BLUE;
                case BLACK -> SET_TEXT_COLOR_RED;
            };
            return c + t;
        }
    }

    private static void drawHeaders(PrintStream out, String[] headers) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(" ".repeat(3));
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String header) {
        out.print(" ".repeat(1));
        printHeaderText(out, header);
        out.print(" ".repeat(1));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print(player);
    }

}
