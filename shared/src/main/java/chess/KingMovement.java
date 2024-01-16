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
        if (col + 1 <= 8) {
            ChessPosition inline = new ChessPosition(row, col+1);
            if (board.getPiece(inline) == null || board.getPiece(inline).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, inline,null));
            }
            if (row + 1 <= 8){
                ChessPosition next = new ChessPosition(row+1, col+1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 1){
                ChessPosition next = new ChessPosition(row-1, col+1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col - 1 <= 8) {
            ChessPosition inline = new ChessPosition(row, col-1);
            if (board.getPiece(inline) == null || board.getPiece(inline).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, inline,null));
            }
            if (row + 1 <= 8){
                ChessPosition next = new ChessPosition(row+1, col-1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if (row - 1 >= 1){
                ChessPosition next = new ChessPosition(row-1, col-1);
                if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (row + 1 <= 8){
            ChessPosition inline = new ChessPosition(row+1, col);
            if (board.getPiece(inline) == null || board.getPiece(inline).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, inline, null));
            }
        }
        if (row - 1 >= 1){
            ChessPosition inline = new ChessPosition(row-1, col);
            if (board.getPiece(inline) == null || board.getPiece(inline).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, inline, null));
            }
        }
        return moves;
    }
}
