package database;

import model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public static void addReservation(Reservation reservation) throws SQLException {
        // Select lainaaika from tuote
        String selectQuery = "SELECT Lainaaika FROM Tuote WHERE Tuote_ID = ?";
        // pass selected lainaaika to this query
        String query = "INSERT INTO Lainat (Tuote_ID, Käyttäjä_ID, ErääntymisPVM, Lasku) VALUES (?, ?, ?, ?)";
        String updateSaldoQuery = "UPDATE Tuote SET Saldo = Saldo - 1 WHERE Tuote_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement updateSaldoStmt = conn.prepareStatement(updateSaldoQuery)) {
            selectStmt.setInt(1, reservation.getProductId());
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                LocalDate endDate = LocalDate.now().plusDays(rs.getInt("Lainaaika"));
                stmt.setInt(1, reservation.getProductId());
                stmt.setInt(2, reservation.getUserId());
                stmt.setDate(3, Date.valueOf(endDate));
                stmt.setDouble(4, 0.0);
                stmt.executeUpdate();

                updateSaldoStmt.setInt(1, reservation.getProductId());
                updateSaldoStmt.executeUpdate();
            }
        }

    }

    public static void returnReservation(int id) throws SQLException {
        String selectQuery = "SELECT * FROM Lainat WHERE Laina_ID = ?";
        String updateQuery = "UPDATE Lainat SET Palautettu = ?, Lasku = ? WHERE Laina_ID = ?";
        String updateSaldoQuery = "UPDATE Tuote SET Saldo = Saldo + 1 WHERE Tuote_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                PreparedStatement updateSaldoStmt = conn.prepareStatement(updateSaldoQuery)) {

            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                Date endDate = rs.getDate("ErääntymisPVM");
                LocalDate currentDate = LocalDate.now();
                boolean isLate = currentDate.isAfter(endDate.toLocalDate());
                double fine = isLate ? 12.0 : 0.0;

                updateStmt.setBoolean(1, true);
                updateStmt.setDouble(2, fine);
                updateStmt.setInt(3, id);
                updateStmt.executeUpdate();

                updateSaldoStmt.setInt(1, rs.getInt("Tuote_ID"));
                updateSaldoStmt.executeUpdate();
            }
        }
    }

    public static List<Reservation> getReservationsByUserId(int id) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT L.*, T.Nimi, T.Tekijä, T.Tyyppi " +
                "FROM Lainat L " +
                "JOIN Tuote T ON L.Tuote_ID = T.Tuote_ID " +
                "WHERE L.Käyttäjä_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("Laina_ID"));
                reservation.setEndDate(rs.getDate("ErääntymisPVM"));
                reservation.setFine(rs.getDouble("Lasku"));
                reservation.setReturned(rs.getBoolean("Palautettu"));
                reservation.setUserId(rs.getInt("Käyttäjä_ID"));
                reservation.setProductId(rs.getInt("Tuote_ID"));
                reservation.setProductName(rs.getString("Nimi"));
                reservation.setAuthor(rs.getString("Tekijä"));
                reservation.setType(rs.getString("Tyyppi"));
                reservations.add(reservation);
            }
        }
        return reservations;
    }



}