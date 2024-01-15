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
        if (col + 1 <= 7) {
            if (row + 2 <= 7){
                ChessPosition next = new ChessPosition(row+2, col+1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 2 >= 0){
                ChessPosition next = new ChessPosition(row-2, col+1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col - 1 >= 0) {
            if (row + 2 <= 7){
                ChessPosition next = new ChessPosition(row+2, col-1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 2 >= 0){
                ChessPosition next = new ChessPosition(row-2, col-1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col - 2 >= 0) {
            if (row + 1 <= 7){
                ChessPosition next = new ChessPosition(row+1, col-2);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 0){
                ChessPosition next = new ChessPosition(row-1, col-2);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col + 2 <= 7) {
            if (row + 1 <= 7){
                ChessPosition next = new ChessPosition(row+1, col-2);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 0){
                ChessPosition next = new ChessPosition(row-1, col-2);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        return moves;
    }
}
