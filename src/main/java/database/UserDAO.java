package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    private Connection mockConnection;

    public UserDAO(Connection mockConnection) {
        this.mockConnection = mockConnection;
    }

    // Tässä CRUD methodi. Se hakee kaikki käyttäjät tietokannasta ja palauttaa ne
    // listana USER OLIOINA.
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM Käyttäjä";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(populateUserFromResultSet(rs));
            }
        }
        return users;
    }

    // Other CRUD methods (create, read, update, delete) can be added here
    public static User getUserById(int id) throws SQLException {
        User user = new User();
        String query = "SELECT * FROM Käyttäjä WHERE Käyttäjä_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = populateUserFromResultSet(rs);
                }
            }
        }
        return user;
    }

    public static User getUserByName(String name) throws SQLException {
        User user = new User();
        String query = "SELECT * FROM Käyttäjä WHERE Käyttäjänimi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = populateUserFromResultSet(rs);
                }
            }
        }
        return user;
    }

    public static void addUser(User user) throws SQLException {
        String query = "INSERT INTO Käyttäjä (Käyttäjänimi, Ikä, Rooli, Salasana, Sähköpostiosoite) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getAge());
            stmt.executeUpdate();
        }
    }

    public static void updateUser(User user) throws SQLException {
        String query = "UPDATE Käyttäjä SET Käyttäjänimi = ?, Sähköpostiosoite = ?, Rooli = ?, Salasana = ?, Ikä = ? WHERE Käyttäjä_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getAge());
            stmt.setInt(6, user.getId());
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

    private static User populateUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("Käyttäjä_ID"));
        user.setName(rs.getString("Käyttäjänimi"));
        user.setEmail(rs.getString("Sähköpostiosoite"));
        user.setRole(rs.getString("Rooli"));
        user.setPassword(rs.getString("Salasana"));
        user.setAge(rs.getInt("Ikä"));
        return user;
    }
}