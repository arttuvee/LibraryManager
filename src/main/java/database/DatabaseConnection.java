package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // TODO: Pistä tähän oikeat tiedot.

    private static final String URL = "jdbc:mysql://10.120.32.69:3306/kirjastoDB";
    private static final String USER = "kirjastoDB";
    private static final String PASSWORD = "Kirjasto123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
