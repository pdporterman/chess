package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor color;
    PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {return color;}

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {return type;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return color == piece.color && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN){
            PawnMove instance = new PawnMove();
            return instance.pieceMoves(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK){
            RookMove instance = new RookMove();
            return instance.pieceMoves(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP){
            BishopMove instance = new BishopMove();
            return instance.pieceMoves(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){
            KnightMove instance = new KnightMove();
            return instance.pieceMoves(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN){
            QueenMove instance = new QueenMove();
            return instance.pieceMoves(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KING){
            KingMove instance = new KingMove();
            return instance.pieceMoves(board, myPosition);
        }
        return null;
    }
}
