package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    // Tässä CRUD methodi. Se hakee kaikki käyttäjät tietokannasta ja palauttaa ne
    // listana USER OLIOINA.
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT" +
                // YLEISTIEDOT
                " u.user_id," +
                " u.email," +
                " u.role," +
                " u.pass," +
                " u.age," +

                // TÄSSÄ NIMET ERI KIELILLÄ
                " fi.name AS fi," +
                " en.name AS en," +
                " ja.name AS ja," +
                " ua.name AS ua" +

                // JOINIT
                " FROM users u" +
                " LEFT JOIN user_translations fi ON u.user_id = fi.user_id AND fi.language_code = 'fi'" +
                " LEFT JOIN user_translations en ON u.user_id = en.user_id AND en.language_code = 'en'" +
                " LEFT JOIN user_translations ja ON u.user_id = ja.user_id AND ja.language_code = 'ja'" +
                " LEFT JOIN user_translations ua ON u.user_id = ua.user_id AND ua.language_code = 'ua'";


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
        String query = "SELECT" +
                " u.user_id," +
                " u.email," +
                " u.role," +
                " u.pass," +
                " u.age," +
                " fi.name AS fi," +
                " en.name AS en," +
                " ja.name AS ja," +
                " ua.name AS ua" +
                " FROM users u" +
                " LEFT JOIN user_translations fi ON u.user_id = fi.user_id AND fi.language_code = 'fi'" +
                " LEFT JOIN user_translations en ON u.user_id = en.user_id AND en.language_code = 'en'" +
                " LEFT JOIN user_translations ja ON u.user_id = ja.user_id AND ja.language_code = 'ja'" +
                " LEFT JOIN user_translations ua ON u.user_id = ua.user_id AND ua.language_code = 'ua'" +
                " WHERE u.user_id = ?";

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
        String query = "SELECT" +
                " u.user_id," +
                " u.email," +
                " u.role," +
                " u.pass," +
                " u.age," +
                " fi.name AS fi," +
                " en.name AS en," +
                " ja.name AS ja," +
                " ua.name AS ua" +
                " FROM users u" +
                " LEFT JOIN user_translations fi ON u.user_id = fi.user_id AND fi.language_code = 'fi'" +
                " LEFT JOIN user_translations en ON u.user_id = en.user_id AND en.language_code = 'en'" +
                " LEFT JOIN user_translations ja ON u.user_id = ja.user_id AND ja.language_code = 'ja'" +
                " LEFT JOIN user_translations ua ON u.user_id = ua.user_id AND ua.language_code = 'ua'" +
                " WHERE fi.name = ? OR en.name = ? OR ja.name = ? OR ua.name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, name);
            stmt.setString(3, name);
            stmt.setString(4, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = populateUserFromResultSet(rs);
                }
            }
        }
        return user;
    }

    public static void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (email, role, pass, age) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getAge());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    addUserTranslations(userId, user);
                }
            }
        }
    }

    public static void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, role = ?, pass = ?, age = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getAge());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
            updateUserTranslations(user);
        }
    }

    private static void updateUserTranslations(User user) throws SQLException {
        String query = "UPDATE user_translations SET name = ? WHERE user_id = ? AND language_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (user.getFi_name() != null) {
                stmt.setString(1, user.getFi_name());
                stmt.setInt(2, user.getId());
                stmt.setString(3, "fi");
                stmt.addBatch();
            }
            if (user.getEn_name() != null) {
                stmt.setString(1, user.getEn_name());
                stmt.setInt(2, user.getId());
                stmt.setString(3, "en");
                stmt.addBatch();
            }
            if (user.getJa_name() != null) {
                stmt.setString(1, user.getJa_name());
                stmt.setInt(2, user.getId());
                stmt.setString(3, "ja");
                stmt.addBatch();
            }
            if (user.getUa_name() != null) {
                stmt.setString(1, user.getUa_name());
                stmt.setInt(2, user.getId());
                stmt.setString(3, "ua");
                stmt.addBatch();
            }
            stmt.executeBatch();
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


    // TÄHÄN LOPPUU CRUD METODIT
    // APUFUNKTIOT ALLA
    private static void addUserTranslations(int userId, User user) throws SQLException {
        String query = "INSERT INTO user_translations (user_id, language_code, name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (user.getFi_name() != null) {
                stmt.setInt(1, userId);
                stmt.setString(2, "fi");
                stmt.setString(3, user.getFi_name());
                stmt.addBatch();
            }
            if (user.getEn_name() != null) {
                stmt.setInt(1, userId);
                stmt.setString(2, "en");
                stmt.setString(3, user.getEn_name());
                stmt.addBatch();
            }
            if (user.getJa_name() != null) {
                stmt.setInt(1, userId);
                stmt.setString(2, "ja");
                stmt.setString(3, user.getJa_name());
                stmt.addBatch();
            }
            if (user.getUa_name() != null) {
                stmt.setInt(1, userId);
                stmt.setString(2, "ua");
                stmt.setString(3, user.getUa_name());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static User populateUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setPassword(rs.getString("pass"));
        user.setAge(rs.getInt("age"));
        user.setFi_name(rs.getString("fi"));
        user.setEn_name(rs.getString("en"));
        user.setJa_name(rs.getString("ja"));
        user.setUa_name(rs.getString("ua"));
        return user;
    }

}