package chess.moves;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class QueenMove {

    
    Collection<ChessMove> moves;

    public void diagonals(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int ud, int lr){
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


    public void rows(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction){
        int i = row + direction;
        while(board.onBoard(i,col)){
            ChessPosition next = new ChessPosition(i, col);
            if (board.getPiece(next) == null){
                moves.add(new ChessMove(myPosition, next, null));
                i += direction;
            }
            else{
                if (board.getPiece(next).getTeamColor() != current.getTeamColor()){
                    moves.add(new ChessMove(myPosition, next, null));
                }
                break;
            }
        }
    }


    public void cols(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction){
        int i = col + direction;
        while(board.onBoard(row, i)){
            ChessPosition next = new ChessPosition(row, i);
            if (board.getPiece(next) == null){
                moves.add(new ChessMove(myPosition, next, null));
                i += direction;
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
        diagonals(board,myPosition,current,row,col,1,1);
        diagonals(board,myPosition,current,row,col,-1,1);
        diagonals(board,myPosition,current,row,col,1,-1);
        diagonals(board,myPosition,current,row,col,-1,-1);
        rows(board,myPosition,current,row,col,1);
        rows(board,myPosition,current,row,col,-1);
        cols(board,myPosition,current,row,col,1);
        cols(board,myPosition,current,row,col,-1);
        return moves;
    }
}
