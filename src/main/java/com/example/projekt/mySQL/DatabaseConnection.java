package com.example.projekt.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {


    private Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());


    private static final String DB_URL = "jdbc:mysql://localhost:3306/casepilotsystem?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "docker2023";

    public Connection connectToDatabase() {
        if (connection != null) {
            logger.warning("Die Datenbankverbindung besteht bereits.");
            return connection;
        }

        try {

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