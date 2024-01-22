package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMove {
    Collection<ChessMove> moves;

    public void move(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col){
        if (board.OnBoard(row, col)){
            ChessPosition next = new ChessPosition(row, col);
            if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        ChessPiece current = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        move(board, myPosition, current, row+1, col+1);
        move(board, myPosition, current, row+1, col);
        move(board, myPosition, current, row+1, col-1);
        move(board, myPosition, current, row-1, col+1);
        move(board, myPosition, current, row-1, col);
        move(board, myPosition, current, row-1, col-1);
        move(board, myPosition, current, row, col+1);
        move(board, myPosition, current, row, col-1);
        return moves;
    }
}
