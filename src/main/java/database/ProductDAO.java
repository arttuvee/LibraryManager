package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO {

    // Tässä CRUD methodi. Se hakee kaikki tuotteet tietokannasta ja palauttaa ne
    // listana TUOTE OLIOINA.
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Tuote";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Tuote_ID"));
                product.setNimi(rs.getString("Nimi"));
                product.setJulkaisuvuosi(rs.getInt("Julkaisuvuosi"));
                product.setTekija(rs.getString("Tekijä"));
                product.setJulkaisija(rs.getString("Julkaisija"));
                product.setIkaraja(rs.getInt("Ikäraja"));
                product.setTyyppi(rs.getString("Tyyppi"));
                product.setKuvaus(rs.getString("Kuvaus"));
                product.setGenre(rs.getString("Genre"));
                product.setSaldo(rs.getInt("Saldo"));
                product.setLainaaika(rs.getInt("Lainaaika"));
                product.setKoodi(rs.getString("Koodi"));
                products.add(product);
            }
        }
        return products;
    }

    public static Product getProductById(int id) throws SQLException {
        Product product = new Product();
        String query = "SELECT * FROM Tuote WHERE Tuote_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product.setId(rs.getInt("Tuote_ID"));
                    product.setNimi(rs.getString("Nimi"));
                    product.setJulkaisuvuosi(rs.getInt("Julkaisuvuosi"));
                    product.setTekija(rs.getString("Tekijä"));
                    product.setJulkaisija(rs.getString("Julkaisija"));
                    product.setIkaraja(rs.getInt("Ikäraja"));
                    product.setTyyppi(rs.getString("Tyyppi"));
                    product.setKuvaus(rs.getString("Kuvaus"));
                    product.setGenre(rs.getString("Genre"));
                    product.setSaldo(rs.getInt("Saldo"));
                    product.setLainaaika(rs.getInt("Lainaaika"));
                    product.setKoodi(rs.getString("Koodi"));
                }
            }
        }
        return product;
    }

    public static List<Product> getProductsByName(String name) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Tuote WHERE Nimi LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("Tuote_ID"));
                    product.setNimi(rs.getString("Nimi"));
                    product.setJulkaisuvuosi(rs.getInt("Julkaisuvuosi"));
                    product.setTekija(rs.getString("Tekijä"));
                    product.setJulkaisija(rs.getString("Julkaisija"));
                    product.setIkaraja(rs.getInt("Ikäraja"));
                    product.setTyyppi(rs.getString("Tyyppi"));
                    product.setKuvaus(rs.getString("Kuvaus"));
                    product.setGenre(rs.getString("Genre"));
                    product.setSaldo(rs.getInt("Saldo"));
                    product.setLainaaika(rs.getInt("Lainaaika"));
                    product.setKoodi(rs.getString("Koodi"));
                    products.add(product);
                }
            }
        }
        return products;
    }

    public static void addProduct(Product product) throws SQLException {
        String checkKoodiQuery = "SELECT COUNT(*) FROM Hyllypaikka WHERE Koodi = ?";
        String insertKoodiQuery = "INSERT INTO Hyllypaikka (Koodi) VALUES (?)";
        String insertProductQuery = "INSERT INTO Tuote (Nimi, Julkaisuvuosi, Tekijä, Julkaisija, Ikäraja, Tyyppi, Kuvaus, Genre, Saldo, Lainaaika, Koodi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                stmt.setString(1, product.getNimi());
                stmt.setInt(2, product.getJulkaisuvuosi());
                stmt.setString(3, product.getTekija());
                stmt.setString(4, product.getJulkaisija());
                stmt.setInt(5, product.getIkaraja());
                stmt.setString(6, product.getTyyppi());
                stmt.setString(7, product.getKuvaus());
                stmt.setString(8, product.getGenre());
                stmt.setInt(9, product.getSaldo());
                stmt.setInt(10, product.getLainaaika());
                stmt.setString(11, product.getKoodi());
                stmt.executeUpdate();
            }
        }
    }

    public static void updateProduct(Product product) throws SQLException {
        String query = "UPDATE Tuote SET Nimi = ?, Julkaisuvuosi = ?, Tekijä = ?, Julkaisija = ?, Ikaraja = ?, Tyyppi = ?, Kuvaus = ?, Genre = ?, Saldo = ?, Lainaaika = ?, Koodi = ? WHERE Tuote_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getNimi());
            stmt.setInt(2, product.getJulkaisuvuosi());
            stmt.setString(3, product.getTekija());
            stmt.setString(4, product.getJulkaisija());
            stmt.setInt(5, product.getIkaraja());
            stmt.setString(6, product.getTyyppi());
            stmt.setString(7, product.getKuvaus());
            stmt.setString(8, product.getGenre());
            stmt.setInt(9, product.getSaldo());
            stmt.setInt(10, product.getLainaaika());
            stmt.setString(11, product.getKoodi());
            stmt.setInt(12, product.getId());
            stmt.executeUpdate();
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