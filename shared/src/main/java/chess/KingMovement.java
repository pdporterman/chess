package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMovement {

    Collection<ChessMove> moves;

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece current = board.getPiece(myPosition);
        if (col + 1 <= 7) {
            moves.add(new ChessMove(myPosition, new ChessPosition(row, col+1),null));
            if (row + 1 <= 7){
                ChessPosition next = new ChessPosition(row+1, col+1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 0){
                ChessPosition next = new ChessPosition(row-1, col+1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col - 1 <= 7) {
            moves.add(new ChessMove(myPosition, new ChessPosition(row, col-1),null));
            if (row + 1 <= 7){
                ChessPosition next = new ChessPosition(row+1, col-1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 0){
                ChessPosition next = new ChessPosition(row-1, col-1);
                if (board.getPiece(next).getTeamColor() != current.getTeamColor() || board.getPiece(next) == null) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (row + 1 <= 7){
            ChessPosition inline = new ChessPosition(row+1, col);
            moves.add(new ChessMove(myPosition, inline, null));
        }
        if (row - 1 >= 0){
            ChessPosition inline = new ChessPosition(row-1, col);
            moves.add(new ChessMove(myPosition, inline, null));
        }
        return moves;
    }
}
