package org.example.biblijava.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for managing database connections and operations for the BibliJava app.
 */
public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:bibliotheque.db";

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return a {@link Connection} object to the SQLite database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Initializes the database by creating necessary tables if they don't exist.
     * This method creates a "users" table with columns for id, username and password.
     */
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL, "
                + "password TEXT NOT NULL);";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
