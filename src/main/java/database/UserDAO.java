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
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Käyttäjä";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("Käyttäjä_ID"));
                user.setName(rs.getString("Käyttäjänimi"));
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
    public static User getUserById(int id) throws SQLException {
        User user = new User();
        user.setId(id);
        String query = "SELECT * FROM Käyttäjä WHERE Käyttäjä_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("Käyttäjä_ID"));
                    user.setName(rs.getString("Käyttäjänimi"));
                    user.setEmail(rs.getString("Sähköpostiosoite"));
                    user.setAge(rs.getInt("Ikä"));
                    user.setRole(rs.getString("Rooli"));
                }
            }
        }
        return user;
    }

    public static User getUserByName(String name) throws SQLException {
        User user = new User();
        user.setName(name);
        String query = "SELECT * FROM Käyttäjä WHERE Käyttäjänimi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("Käyttäjä_ID"));
                    user.setName(rs.getString("Käyttäjänimi"));
                    user.setEmail(rs.getString("Sähköpostiosoite"));
                    user.setAge(rs.getInt("Ikä"));
                    user.setRole(rs.getString("Rooli"));
                }
            }
        }
        return user;
    }

    public static void addUser(User user) throws SQLException {
        String query = "INSERT INTO Käyttäjä (Käyttäjänimi, Sähköpostiosoite, Ikä, Rooli, Salasana) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getAge());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getPassword());
            stmt.executeUpdate();
        }
    }

    public static void updateUser(User user) throws SQLException {
        String query = "UPDATE Käyttäjä SET Käyttäjänimi = ?, Sähköpostiosoite = ?, Ikä = ?, Rooli = ? WHERE Käyttäjä_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getAge());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }

    public static void deleteUserById(int id) throws SQLException {
        String query = "DELETE FROM Käyttäjä WHERE Käyttäjä_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }



}
