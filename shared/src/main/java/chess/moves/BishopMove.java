package chess.moves;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class BishopMove {
    Collection<ChessMove> moves;

    public void bishopDiagonals(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int ud, int lr){
        int i = row + ud;
        int j = col + lr;
        while(board.onBoard(i,j)){
            ChessPosition next = new ChessPosition(i, j);
            if (board.getPiece(next) == null){
                moves.add(new ChessMove(myPosition, next, null));
                i += ud;
                j += lr;
            }
            else{
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()){
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
        }
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        ChessPiece current = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        bishopDiagonals(board,myPosition,current,row,col,1,1);
        bishopDiagonals(board,myPosition,current,row,col,-1,1);
        bishopDiagonals(board,myPosition,current,row,col,1,-1);
        bishopDiagonals(board,myPosition,current,row,col,-1,-1);
        return moves;
    }
}
