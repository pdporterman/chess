package chess.moves;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.HashSet;

public class SharedMoves {
    public void diagonals(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int ud, int lr, HashSet<ChessMove> moves){
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

    public void rows(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction, HashSet<ChessMove> moves){
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

    public void cols(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, int direction, HashSet<ChessMove> moves){
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

    public void move(ChessBoard board, ChessPosition myPosition, ChessPiece current, int row, int col, HashSet<ChessMove> moves){
        if (board.onBoard(row, col)){
            ChessPosition next = new ChessPosition(row, col);
            if (board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current.getTeamColor()){
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
    }

    public void getDiagonals(ChessBoard board, ChessPosition myPosition, ChessPiece current, Integer row, Integer col, HashSet<ChessMove> moves){
        diagonals(board,myPosition,current,row,col,1,1, moves);
        diagonals(board,myPosition,current,row,col,-1,1, moves);
        diagonals(board,myPosition,current,row,col,1,-1, moves);
        diagonals(board,myPosition,current,row,col,-1,-1, moves);
    }

    public void getRowsCols(ChessBoard board, ChessPosition myPosition, ChessPiece current, Integer row, Integer col, HashSet<ChessMove> moves) {
        rows(board,myPosition,current,row,col,1, moves);
        rows(board,myPosition,current,row,col,-1, moves);
        cols(board,myPosition,current,row,col,1, moves);
        cols(board,myPosition,current,row,col,-1, moves);
    }

    }
