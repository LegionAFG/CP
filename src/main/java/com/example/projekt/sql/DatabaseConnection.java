package com.example.projekt.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {

    private Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/casepilotsystem" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456789";

    public void connectToDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                logger.warning("Die Datenbankverbindung besteht bereits.");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL-Treiber erfolgreich geladen.");

            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Verbindung zur Datenbank erfolgreich hergestellt!");
        } catch (ClassNotFoundException e) {
            logger.severe("MySQL-Treiber konnte nicht geladen werden: " + e.getMessage());
        } catch (SQLException e) {
            logger.severe("Fehler bei der Verbindung: " + e.getMessage());
            connection = null;
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }
        } catch (SQLException e) {
            logger.severe("Verbindungsprüfung fehlgeschlagen: " + e.getMessage());
        }
        return this.connection;
    }

    public void close() {
        if (connection == null || isConnectionClosed()) {
            logger.info("Keine aktive Verbindung zum Schließen vorhanden.");
            return;
        }

        try {
            connection.close();
            logger.info("Datenbankverbindung erfolgreich geschlossen.");
        } catch (SQLException e) {
            logger.severe("Fehler beim Schließen der Verbindung: " + e.getMessage());
        } finally {
            connection = null;
        }
    }

    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            logger.severe("Fehler bei der Verbindungsprüfung: " + e.getMessage());
            return true;
        }
    }
}
