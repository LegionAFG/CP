package com.example.projekt.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {

    private Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public void connectToDatabase() {
        if (connection != null) {
            logger.warning("Die Datenbankverbindung besteht bereits.");
            return;
        }

        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new IllegalArgumentException("Die Umgebungsvariable DB_URL ist nicht gesetzt oder leer.");
        }

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Verbindung zur Datenbank erfolgreich hergestellt!");
        } catch (SQLException e) {
            logger.severe("Fehler bei der Verbindung: " + e.getMessage());
            throw new RuntimeException("Datenbankverbindung fehlgeschlagen!", e);
        }
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
        }
    }
}
