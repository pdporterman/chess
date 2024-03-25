package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class QueenMove {
    SharedMoves sharedMoves = new SharedMoves();
    HashSet<ChessMove> moves;
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        ChessPiece current = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        sharedMoves.getDiagonals(board, myPosition, current, row, col, moves);
        sharedMoves.getRowsCols(board, myPosition, current, row, col, moves);
        return moves;
    }
}
