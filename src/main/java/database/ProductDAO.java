package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Product;

public class ProductDAO {


    // Helper method to extract product data from ResultSet
    private static Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("Product_ID"));
        product.setJulkaisuvuosi(rs.getInt("release_year"));
        product.setIkaraja(rs.getInt("age_limit"));
        product.setSaldo(rs.getInt("saldo"));
        product.setLainaaika(rs.getInt("borrow_time"));
        product.setKoodi(rs.getString("code"));
        product.setTyyppi(rs.getString("type"));
        product.setGenre(rs.getString("genre"));
        product.setAuthor(rs.getString("author"));
        product.setProducer(rs.getString("producer"));


        // Fetch translations if available
        String query2 = "SELECT * FROM products_translations WHERE product_id = ?";
        try (PreparedStatement stmt2 = rs.getStatement().getConnection().prepareStatement(query2)) {
            stmt2.setInt(1, product.getId());
            try (ResultSet rs2 = stmt2.executeQuery()) {
                while (rs2.next()) {
                    String language = rs2.getString("language_code");
                    switch (language) {
                        case "en" -> product.setEnglishData(rs2.getString("name"), rs2.getString("description"));
                        case "fi" -> product.setFinnishData(rs2.getString("name"), rs2.getString("description"));
                        case "ja" -> product.setJapaneseData(rs2.getString("name"), rs2.getString("description"));
                        case "uk" -> product.setUkrainianData(rs2.getString("name"), rs2.getString("description"));
                    }
                }
            }
        }
        return product;
    }

    // Tässä CRUD methodi. Se hakee kaikki tuotteet tietokannasta ja palauttaa ne
    // listana TUOTE OLIOINA.
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        }
        return products;
    }

    public static Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE Product_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractProductFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public static void addProduct(Product product) throws SQLException {
        String checkKoodiQuery = "SELECT COUNT(*) FROM Hyllypaikka WHERE Koodi = ?";
        String insertKoodiQuery = "INSERT INTO Hyllypaikka (Koodi) VALUES (?)";
        String insertProductQuery = "INSERT INTO products (release_year, age_limit, saldo, borrow_time, code, type, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkKoodiQuery)) {
                checkStmt.setString(1, product.getKoodi());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        try (PreparedStatement insertKoodiStmt = conn.prepareStatement(insertKoodiQuery)) {
                            insertKoodiStmt.setString(1, product.getKoodi());
                            insertKoodiStmt.executeUpdate();
                        }
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertProductQuery)) {
                stmt.setInt(1, product.getJulkaisuvuosi());
                stmt.setInt(2, product.getIkaraja());
                stmt.setInt(3, product.getSaldo());
                stmt.setString(5, product.getKoodi());
                stmt.setString(6, product.getTyyppi());
                stmt.setString(7, product.getGenre());
                Integer lainaaika = product.getLainaaika();
                stmt.setInt(4, lainaaika == null ? (Objects.equals(product.getTyyppi(), "Kirja") || Objects.equals(product.getTyyppi(), "kirja") ? 28 : 14) : lainaaika);
                stmt.executeUpdate();
            }
        }
    }

    public static void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET release_year = ?, age_limit = ?, saldo = ?, borrow_time = ?, code = ?, type = ?, genre = ? WHERE Product_ID = ?";
        String query2 = "UPDATE products_translations SET name = ?, description = ? WHERE product_id = ? AND language_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, product.getJulkaisuvuosi());
            stmt.setInt(2, product.getIkaraja());
            stmt.setInt(3, product.getSaldo());
            stmt.setInt(4, product.getLainaaika());
            stmt.setString(5, product.getKoodi());
            stmt.setString(6, product.getTyyppi());
            stmt.setString(7, product.getGenre());
            stmt.setInt(8, product.getId());
            stmt.executeUpdate();
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt2 = conn.prepareStatement(query2)) {
                for (String language : new String[]{"en", "fi", "ja", "uk"}) {
                    stmt2.setString(1, product.getName(language));
                    stmt2.setString(2, product.getDescription(language));
                    stmt2.setInt(3, product.getId());
                    stmt2.setString(4, language);
                    stmt2.executeUpdate();
                }
            }
        }
    }

    public static void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM Tuote WHERE Tuote_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}