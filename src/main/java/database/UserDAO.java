package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    // Tässä CRUD methodi. Se hakee kaikki käyttäjät tietokannasta ja palauttaa ne
    // listana USER OLIOINA.
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("Käyttäjä_ID"));
                user.setName(rs.getString("Käyttäjäninimi"));
                user.setEmail(rs.getString("Sähköpostiosoite"));
                user.setAge(rs.getInt("Ikä"));
                user.setRole(rs.getString("Rooli"));
                // Kannassa on vielä kenttä salasanalle, mutta sitä ei varmaankaan haluta tähän.
                users.add(user);
            }
        }
        return users;
    }

    // Other CRUD methods (create, read, update, delete) can be added here
    public User getUserById(int id) throws SQLException {
        User user = new User();
        user.setId(id);
        String query = "SELECT * FROM users WHERE Käyttäjä_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("Käyttäjä_ID"));
                    user.setName(rs.getString("Käyttäjäninimi"));
                    user.setEmail(rs.getString("Sähköpostiosoite"));
                    user.setAge(rs.getInt("Ikä"));
                    user.setRole(rs.getString("Rooli"));
                }
            }
        }
        return user;
    }

}
