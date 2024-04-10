package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthToken;
import model.Game;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.handlers.requests.CreateGameRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.sql.Statement.*;
import static java.sql.Types.*;

public class MySqlDataAccess implements DataAccess{

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` TEXT DEFAULT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(gameName)
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              INDEX(username)
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS  auths (
              `token` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              INDEX(username)
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    public MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

                                                                                                                                    //users
    public User addUser(User user) throws DataAccessException {
        var statement = "INSERT INTO users (username, password, email, json) VALUES (?, ?, ?, ?)";
        var json = new Gson().toJson(user);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        executeUpdate(statement, user.getUserName(), encoder.encode(user.getPassword()), user.getEmail(), json);
        return user;
    }

    public User getUser(String username, String password) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, json FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                        if (encoder.matches(password, storedPassword)) {
                            return readUser(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public void clearUsers() throws DataAccessException{
        var statement = "TRUNCATE users";
        executeUpdate(statement);
    }

                                                                                                                                    // auths
    public void addAuth(AuthToken auth) throws DataAccessException {
        var statement = "INSERT INTO auths (token, username, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(auth);
        executeUpdate(statement, auth.getToken(), auth.getUsername(), json);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auths WHERE token=?";
        executeUpdate(statement, authToken);
    }

    public AuthToken getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT token, json FROM auths WHERE token=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public void clearAuth() throws DataAccessException {
        var statement = "TRUNCATE auths";
        executeUpdate(statement);
    }

    public boolean checkAuth(String auth) throws DataAccessException {
        AuthToken token = getAuth(auth);
        return token != null;
    }

                                                                                                                                    //games
    public Game addGame(CreateGameRequest request) throws DataAccessException {
        var statement = "INSERT INTO games (gameName, json) VALUES (?, ?)";
        Game temp = new Game(0, request.getGameName());
        var json = new Gson().toJson(temp.getGame());
        int id = executeUpdate(statement, temp.getGameName(), json);
        return new Game(id , request.getGameName(), temp.getGame());
    }

    public Game getGame(Integer gameID) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public Collection<Game> getAllGames() throws DataAccessException {
        var result = new ArrayList<Game>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    public boolean setPlayer(String username, String color, Game game) throws DataAccessException {
        //System.out.println(game.getGameID() + game.getGameName() + game.getBlackUsername() + game.getWhiteUsername());
        if (Objects.equals(color, null)) {
            return true;
        } else if (Objects.equals(color, "WHITE") && Objects.equals(game.getWhiteUsername(), null)) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "UPDATE games Set whiteUsername=? WHERE gameID=?";
                try (var ps = conn.prepareStatement(statement)) {
                    ps.setString(1, username);
                    System.out.println(game.getGameID());
                    ps.setInt(2, game.getGameID());
                    ps.executeUpdate();
                    return true;
                }
            }
            catch (Exception e) {
                return false;
            }
        }
        else if (Objects.equals(color, "BLACK") && Objects.equals(game.getBlackUsername(), null)) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "UPDATE games Set blackUsername=? WHERE gameID=?";
                try (var ps = conn.prepareStatement(statement)) {
                    ps.setString(1, username);
                    ps.setInt(2, game.getGameID());
                    ps.executeUpdate();
                    return true;
                }
            }
            catch (Exception e) {
                return false;
            }
            }
            return false;
    }

    public boolean updateChessGame(int gameId, ChessGame game){
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "UPDATE games Set json=? WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, new Gson().toJson(game));
                ps.setInt(2, gameId);
                ps.executeUpdate();
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    public void clearGames() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

                                                                                                                        //reading to and from DB
    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case ChessGame p -> ps.setString(i + 1, new Gson().toJson(p));
                        case null -> ps.setNull(i + 1,NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private User readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, User.class);
    }

    private AuthToken readAuth(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, AuthToken.class);
    }

    private Game readGame(ResultSet rs) throws SQLException {
        Game game = new Game(rs.getInt("gameID"), rs.getString("gameName"));
        game.setBlackUsername(rs.getString("blackUsername"));
        game.setWhiteUsername(rs.getString("whiteUsername"));
        game.setGame(new Gson().fromJson(rs.getString("json"), ChessGame.class));
        return game;
    }

    public boolean removePlayer(int gameId, String userName){
        try (var conn = DatabaseManager.getConnection()) {
            Game game = getGame(gameId);
            if (Objects.equals(userName, game.getBlackUsername()) || Objects.equals(userName, game.getWhiteUsername())) {
                var statement = "";
                if (Objects.equals(userName, game.getBlackUsername())) {
                    statement = "UPDATE games Set blackUsername=? WHERE gameID=?";
                }
                else if (Objects.equals(userName, game.getWhiteUsername())) {
                    statement = "UPDATE games Set whiteUsername=? WHERE gameID=?";
                }

                try (var ps = conn.prepareStatement(statement)) {
                    ps.setString(1, null);
                    ps.setInt(2, gameId);
                    ps.executeUpdate();
                    return true;
                }
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

}
