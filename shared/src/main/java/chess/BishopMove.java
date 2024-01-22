package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMove {
    Collection<ChessMove> moves;

    public void Diagonals(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int UD, int LR){
        int i = row + UD;
        int j = col + LR;
        while(board.OnBoard(i,j)){
            ChessPosition next = new ChessPosition(i, j);
            if (board.getPiece(next) == null){
                moves.add(new ChessMove(myPosition, next, null));
                i += UD;
                j += LR;
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
        Diagonals(board,myPosition,current,row,col,1,1);
        Diagonals(board,myPosition,current,row,col,-1,1);
        Diagonals(board,myPosition,current,row,col,1,-1);
        Diagonals(board,myPosition,current,row,col,-1,-1);
        return moves;
    }
}