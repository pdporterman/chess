package chess;
import java.util.ArrayList;
import java.util.Collection;

public class RookMovement {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> moves = new ArrayList<>(); //use a hash set
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        for (int i = row + 1; i <= 7; i++){
            if (board.getPiece(myPosition) != null){if ();};
            moves.add(new ChessMove(myPosition, new ChessPosition(i, col),null));
        };
        for (int i = row - 1; i >= 0; i--){
            if (board.getPiece(myPosition) != null){break;};
            moves.add(new ChessMove(myPosition, new ChessPosition(i, col),null));
        }
        for (int i = col + 1; i <= 7; i++){
            if (board.getPiece(myPosition) != null){break;};
            moves.add(new ChessMove(myPosition, new ChessPosition(row, i),null));
        };
        for (int i = col - 1; i >= 0; i--){
            if (board.getPiece(myPosition) != null){break;};
            moves.add(new ChessMove(myPosition, new ChessPosition(row, i),null));

    }
}
