package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMovement {
    Collection<ChessMove> moves;
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece current = board.getPiece(myPosition);
        int direction = (current.getTeamColor() == ChessGame.TeamColor.WHITE) ? 1 :-1;
        ChessPosition next = new ChessPosition(row + direction, col);
        ChessPosition two = new ChessPosition(row + direction*2, col);
        ChessPiece.PieceType promote = (row + direction == 0 || row + direction == 7) ? ChessPiece.PieceType.QUEEN : null;
        if (board.getPiece(next) == null){
            moves.add(new ChessMove(myPosition, next, promote));
            if ((direction == 1 && row == 2) || (direction == -1 && row == 6)){
                if (board.getPiece(two) == null){
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if (col < 7){
            ChessPosition cap = new ChessPosition(row + direction, col+1);
            if (board.getPiece(cap).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(row + direction, col + 1), promote));
            }
        }
        if (col > 0){
            ChessPosition cap = new ChessPosition(row + direction, col - 1);
            if (board.getPiece(cap).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(row + direction, col + 1), promote));
            }
        }
        return moves;
    }
}

