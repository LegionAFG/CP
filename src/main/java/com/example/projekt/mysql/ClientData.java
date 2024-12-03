package com.example.projekt.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ClientData {

    private static final Logger logger = Logger.getLogger(ClientData.class.getName());
    private final DatabaseConnection databaseConnection;


    public ClientData() {
        this.databaseConnection = new DatabaseConnection();
    }

    public void addClient(String clientID, String lastname, String firstname, LocalDate dateOfBirth, String gender, String nationality, String relationship) {
        String sql = "INSERT INTO Clients (ClientID, Lastname, Firstname, DateOfBirth, Gender, Nationality, Relationship) " +
                "VALUES ('" + clientID + "', '" + lastname + "', '" + firstname + "', '" + dateOfBirth + "', '" +
                gender + "', '" + nationality + "', '" + relationship + "')";

        logger.info("SQL-Statement: " + sql);

        try (Connection conn = databaseConnection.connectToDatabase()) {
            if (conn == null) {
                logger.warning("Keine Verbindung zur Datenbank verfügbar.");
                return;
            }

            try (Statement stm = conn.createStatement()) {
                stm.execute(sql);
                logger.info("Klient erfolgreich in die Datenbank eingefügt.");
            } catch (SQLException e) {
                logger.severe("Fehler beim Ausführen des SQL-Statements: " + e.getMessage());
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Aufbau der Verbindung zur Datenbank: " + e.getMessage());
        }
    }
}
