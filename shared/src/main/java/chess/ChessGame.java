package chess;

import chess.moves.ChessMove;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamColor;
    private ChessBoard board = new ChessBoard();

    public ChessGame() {
        this.board.resetBoard();
        this.teamColor = TeamColor.WHITE;
    }


    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }


    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamColor = team;
    }


    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }



    public boolean validMove(ChessMove move){
        ChessPiece startPiece = board.getPiece(move.getStartPosition());
        ChessPiece endPiece = board.getPiece(move.getEndPosition());
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        if (!board.onBoard(end.getRow(),end.getColumn())){
            return false;
        }
        if (endPiece == null || (startPiece.getTeamColor() != endPiece.getTeamColor())){
            board.addPiece(end, startPiece);
            board.addPiece(start, null);
            boolean valid = !isInCheck(board.getPiece(move.getEndPosition()).getTeamColor());
            board.addPiece(start, startPiece);
            board.addPiece(end, endPiece);
            return valid;
        }
        return false;
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
        Collection<ChessMove> valid = new HashSet<>();
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
        ChessPiece piece = board.getPiece(move.getStartPosition());
        HashSet<ChessMove> moveSet = (HashSet<ChessMove>) validMoves(move.getStartPosition());
        TeamColor color = piece.getTeamColor();
        TeamColor next = (this.teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        boolean turn = getTeamTurn() == color;
        boolean inMoveSet = moveSet.contains(move);
        if (inMoveSet && turn){
            if (move.getPromotionPiece() != null){
                board.addPiece(move.getEndPosition(), new ChessPiece(color, move.getPromotionPiece()));
            }
            else{
                board.addPiece(move.getEndPosition(),piece);
            }
            board.addPiece(move.getStartPosition(), null);
            setTeamTurn(next);
        }
        else{
            throw new InvalidMoveException(piece.toString());
        }
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
        ChessPosition kingPosition = getKingPosition(teamColor);
        for (int r = 1; r <= 8; r++){
            for (int c = 1; c <= 8; c++){
                ChessPosition pos = new ChessPosition(r,c);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(board, pos);
                    for (ChessMove move : moves){
                        if (move.getEndPosition().equals(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean movesCheck(TeamColor teamColor){
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition pos = new ChessPosition(row,col);
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


    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return movesCheck(teamColor);
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
            return movesCheck(teamColor);
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
