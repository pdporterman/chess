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



        if (col < 8){
            ChessPosition cap = new ChessPosition(row + direction, col+1);
            if (board.getPiece(cap) != null && board.getPiece(cap).getTeamColor() != current.getTeamColor()){
                if (row + direction == 1 || row+direction == 8){
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.ROOK));
                }
                else{
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }



        if (col > 1){
            ChessPosition cap = new ChessPosition(row + direction, col - 1);
            if (board.getPiece(cap) != null && board.getPiece(cap).getTeamColor() != current.getTeamColor()){
                if (row + direction == 1 || row + direction == 8){
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.ROOK));
                }
                else{
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }




        if (board.getPiece(next) == null){
            if (row + direction == 1 || row + direction == 8){
                moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.ROOK));
            }
            else{
                moves.add(new ChessMove(myPosition, next, null));
                if ((direction == 1 && row == 2) || (direction == -1 && row == 7)){
                    if (board.getPiece(two) == null){
                        moves.add(new ChessMove(myPosition, next, null));
                    }
                }
            }
        }
        return moves;
    }
}

