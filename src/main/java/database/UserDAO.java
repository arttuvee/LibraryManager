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

        String query = "SELECT user_id, name, email, role, pass, age FROM users";

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
        String query = "SELECT user_id, name, email, role, pass, age FROM users WHERE user_id = ?";

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
        String query = "SELECT user_id, name, email, role, pass, age FROM users WHERE name = ?";

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
        String query = "INSERT INTO users (name, email, role, pass, age) VALUES (?, ?, ?, ?, ?)";
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
        String query = "UPDATE users SET name = ?, email = ?, role = ?, pass = ?, age = ? WHERE user_id = ?";
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
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private static User populateUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setPassword(rs.getString("pass"));
        user.setAge(rs.getInt("age"));
        return user;
    }
}