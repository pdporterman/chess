package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor team;
    ChessBoard board = new ChessBoard();

    public ChessGame() {
        this.team = TeamColor.WHITE;
        this.board.resetBoard();
    }


    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */


    public void setTeamTurn(TeamColor team) {
        if (team.equals(TeamColor.WHITE)) {
            this.team = TeamColor.BLACK;
        }
        else{
            this.team = TeamColor.WHITE;
        }
    }


    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }



    public boolean validMove(ChessMove move){
        ChessMove undo = new ChessMove(move.getEndPosition(),move.getStartPosition(),move.getPromotionPiece());
        ChessPiece piece = board.getPiece(move.getStartPosition());
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        board.addPiece(end, piece);
        board.addPiece(start, null);
        boolean valid = isInCheck(board.getPiece(move.getEndPosition()).getTeamColor());
        board.addPiece(start, piece);
        board.addPiece(end, null);
        return valid;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> valid = new ArrayList<>();
        if (piece != null){
            Collection<ChessMove> all = piece.pieceMoves(board,startPosition);
            for (ChessMove move : all){
                if (validMove(move)){
                    valid.add(move);
                }
            }
            return valid;
        }
        return null;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */

    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (validMove(move)){
            board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(), null);
        }
        throw new InvalidMoveException("Invalid move: " + move);
    }


    public ChessPosition getKingPosition(TeamColor teamColor){
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPosition pos = new ChessPosition(r,c);
                ChessPiece temp = board.getPiece(pos);
                if (temp != null && temp.getPieceType().equals(ChessPiece.PieceType.KING) && temp.getTeamColor().equals(teamColor)){
                    return pos;
                }
            }
        }
        return null;
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king_position = getKingPosition(teamColor);
        for (int r = 1; r <= 8; r++){
            for (int c = 1; c <= 8; c++){
                ChessPosition pos = new ChessPosition(r,c);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(board, pos);
                    for (ChessMove move : moves){
                        if (move.getEndPosition().equals(king_position)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            for (int r = 1; r <= 8; r++) {
                for (int c = 1; c <= 8; c++) {
                    ChessPosition pos = new ChessPosition(r,c);
                    ChessPiece temp = board.getPiece(pos);
                    if (temp != null && temp.getTeamColor().equals(teamColor)) {
                        int valid = validMoves(pos).size();
                        if (valid > 0){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){
            for (int r = 1; r <= 8; r++) {
                for (int c = 1; c <= 8; c++) {
                    ChessPosition pos = new ChessPosition(r,c);
                    ChessPiece temp = board.getPiece(pos);
                    if (temp != null && temp.getTeamColor().equals(teamColor)) {
                        int valid = validMoves(pos).size();
                        if (valid > 0){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }


    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
