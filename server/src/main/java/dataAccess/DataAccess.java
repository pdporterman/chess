package dataAccess;


import chess.ChessGame;
import model.User;
import model.AuthToken;
import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws DataAccessException;

    User getUser(User user) throws DataAccessException;

    Collection<User> getAllUser(User user) throws DataAccessException;

    void deleteUser(User user) throws DataAccessException;

    void clearUsers() throws DataAccessException;

    AuthToken addAuth(AuthToken auth) throws DataAccessException;

    ChessGame addGame(ChessGame game) throws DataAccessException;

    ChessGame getGame(ChessGame game) throws DataAccessException;

    Collection<ChessGame> getAllUser(ChessGame game) throws DataAccessException;

    void deleteGame(ChessGame game) throws DataAccessException;

    void clearGames() throws DataAccessException;

}
