package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenMovement {
    Collection<ChessMove> moves;


    private void getDiagonal(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int WE, int NS) {
        int vertBorder = (NS > 0) ? 8 : 1;
        int sideBorder = (WE > 0) ? 8 : 1;
        if (col == sideBorder){
            return;
        }
        int j = col + WE;
        for (int i = row + NS; i-NS != vertBorder; i += NS) {
            ChessPosition next = new ChessPosition(i, j);
            if (board.getPiece(next) != null) {
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
            else{
                moves.add(new ChessMove(myPosition, next, null));
            }
            if (j == sideBorder){
                break;
            }
            j += WE;
        }
    }

    private void getRows(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction) {
        int border = (direction > 0) ? 8 : 1;
        for (int i = row + direction; i-direction != border; i += direction) {
            ChessPosition next = new ChessPosition(i, col);
            if (board.getPiece(next) != null) {
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
            else{
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
    }

    private void getCols(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction) {
        int border = (direction > 0) ? 8 : 1;
        for (int i = col + direction; i-direction != border; i += direction) {
            ChessPosition next = new ChessPosition(row, i);
            if (board.getPiece(next) != null) {
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
            else{
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
    }




    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece current = board.getPiece(myPosition);
        getRows(board, myPosition, current, row, col, 1); //get greater rows
        getRows(board, myPosition, current, row, col, -1); //get lesser rows
        getCols(board, myPosition, current, row, col, 1); // get greater cols
        getCols(board, myPosition, current, row, col, -1); //get lesser cols
        getDiagonal(board, myPosition, current, row, col, 1,1); //get right forward
        getDiagonal(board, myPosition, current, row, col, -1,1); //get left forward
        getDiagonal(board, myPosition, current, row, col, 1,-1); // get right backward
        getDiagonal(board, myPosition, current, row, col, -1,-1); //get left backward
        return moves;
    }
}
