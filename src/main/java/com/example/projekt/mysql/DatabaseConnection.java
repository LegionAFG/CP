package com.example.projekt.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {

    private Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public Connection connectToDatabase() {
        if (connection != null) {
            logger.warning("Die Datenbankverbindung besteht bereits.");
            return connection;
        }

        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL-Treiber erfolgreich geladen.");
        } catch (ClassNotFoundException e) {
            logger.severe("MySQL-Treiber konnte nicht geladen werden: " + e.getMessage());
        }

        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new IllegalArgumentException("Die Umgebungsvariable DB_URL ist nicht gesetzt oder leer.");
        }

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Verbindung zur Datenbank erfolgreich hergestellt!");
        } catch (SQLException e) {
            logger.severe("Fehler bei der Verbindung: " + e.getMessage());
            connection = null;
        }
        return connection;
    }

    public void close() {
        if (connection == null) {
            logger.info("Keine aktive Verbindung zum Schließen vorhanden.");
            return;
        }

        try {
            if (!connection.isClosed()) {
                connection.close();
                logger.info("Datenbankverbindung erfolgreich geschlossen.");
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Schließen der Verbindung: " + e.getMessage());
        } finally {
            connection = null;
        }
    }
}
