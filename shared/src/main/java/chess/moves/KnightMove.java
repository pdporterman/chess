package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class KnightMove {
    SharedMoves sharedMoves = new SharedMoves();
    HashSet<ChessMove> moves;
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        ChessPiece current = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        sharedMoves.move(board, myPosition, current, row+1, col+2, moves);
        sharedMoves.move(board, myPosition, current, row+1, col-2, moves);
        sharedMoves.move(board, myPosition, current, row-1, col+2, moves);
        sharedMoves.move(board, myPosition, current, row-1, col-2, moves);
        sharedMoves.move(board, myPosition, current, row+2, col+1, moves);
        sharedMoves.move(board, myPosition, current, row+2, col-1, moves);
        sharedMoves.move(board, myPosition, current, row-2, col+1, moves);
        sharedMoves.move(board, myPosition, current, row-2, col-1, moves);
        return moves;
    }
}
