package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMove {
    Collection<ChessMove> moves;

    public void move(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col){
        int direction = (current.getTeamColor() == ChessGame.TeamColor.WHITE) ? 1 : -1;
        boolean promotion = (row + direction == 1 || row + direction == 8);
        ChessPosition one = new ChessPosition(row + direction,col);
        ChessPosition two = new ChessPosition(row + direction*2, col);
        ChessPosition capr = new ChessPosition(row + direction,col + 1);
        ChessPosition capl = new ChessPosition(row + direction,col - 1);
        if(board.getPiece(one) == null){
            if(promotion){
                moves.add(new ChessMove(myPosition, one, ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, one, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, one, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, one, ChessPiece.PieceType.KNIGHT));
            }
            else{
                moves.add(new ChessMove(myPosition, one, null));
                if ((current.getTeamColor() == ChessGame.TeamColor.WHITE && row == 2) || (current.getTeamColor() == ChessGame.TeamColor.BLACK && row == 7)){
                    if(board.getPiece(two) == null){
                        moves.add(new ChessMove(myPosition, two, null));
                    }
                }
            }

        }
        if (board.OnBoard(row + direction, col + 1)) {
            if (board.getPiece(capr) != null && board.getPiece(capr).getTeamColor() != current.getTeamColor()) {
                if (promotion) {
                    moves.add(new ChessMove(myPosition, capr, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, capr, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, capr, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, capr, ChessPiece.PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, capr, null));
                }
            }
        }

        if (board.OnBoard(row + direction, col -1)) {
            if (board.getPiece(capl) != null && board.getPiece(capl).getTeamColor() != current.getTeamColor()) {
                if (promotion) {
                    moves.add(new ChessMove(myPosition, capl, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, capl, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, capl, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, capl, ChessPiece.PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, capl, null));
                }
            }
        }
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        ChessPiece current = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        move(board, myPosition, current, row, col);
        return moves;
    }
}
