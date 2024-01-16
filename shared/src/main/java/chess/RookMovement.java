package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMovement {
    Collection<ChessMove> moves;


    private void getRows(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction) {
        int border = (direction > 0) ? 8 : 1;
        for (int i = row + direction; i-direction != border; i += direction) {
            ChessPosition next = new ChessPosition(i, col);
            if (board.getPiece(next) != null) {
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
            else{
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
    }

    private void getCols(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction) {
        int border = (direction > 0) ? 8 : 1;
        for (int i = col + direction; i-direction != border; i += direction) {
            ChessPosition next = new ChessPosition(row, i);
            if (board.getPiece(next) != null) {
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
            else{
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>(); //use a hash set
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece current = board.getPiece(myPosition);
        getRows(board, myPosition, current, row, col, 1); //get greater rows
        getRows(board, myPosition, current, row, col, -1); //get lesser rows
        getCols(board, myPosition, current, row, col, 1); // get greater cols
        getCols(board, myPosition, current, row, col, -1); //get lesser cols
        return moves;
        /*
        for (int i = row + 1; i <= 7; i++) {
            ChessPosition next = new ChessPosition(i, col);
            if (board.getPiece(next) != null) {
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(i, col), null));
        }
        ;
        for (int i = row - 1; i >= 0; i--) {
            if (board.getPiece(myPosition) != null) {
                break;
            }
            ;
            moves.add(new ChessMove(myPosition, new ChessPosition(i, col), null));
        }
        for (int i = col + 1; i <= 7; i++) {
            if (board.getPiece(myPosition) != null) {
                break;
            }
            ;
            moves.add(new ChessMove(myPosition, new ChessPosition(row, i), null));
        }
        ;
        for (int i = col - 1; i >= 0; i--) {
            if (board.getPiece(myPosition) != null) {
                break;
            }
            ;
            moves.add(new ChessMove(myPosition, new ChessPosition(row, i), null));

        }
        */
    }
}