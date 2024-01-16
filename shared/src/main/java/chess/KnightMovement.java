package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMovement {
    Collection<ChessMove> moves;

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece current = board.getPiece(myPosition);
        if (col + 1 <= 8) {
            if (row + 2 <= 8){
                ChessPosition next = new ChessPosition(row+2, col+1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 2 >= 1){
                ChessPosition next = new ChessPosition(row-2, col+1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col - 1 >= 1) {
            if (row + 2 <= 8){
                ChessPosition next = new ChessPosition(row+2, col-1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 2 >= 1){
                ChessPosition next = new ChessPosition(row-2, col-1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col - 2 >= 1) {
            if (row + 1 <= 8){
                ChessPosition next = new ChessPosition(row+1, col-2);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 1){
                ChessPosition next = new ChessPosition(row-1, col-2);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col + 2 <= 8) {
            if (row + 1 <= 8){
                ChessPosition next = new ChessPosition(row+1, col+2);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 1){
                ChessPosition next = new ChessPosition(row-1, col+2);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        return moves;
    }
}
