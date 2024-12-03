package com.example.projekt.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ClientData {

    private final DatabaseConnection databaseConnection;

    public ClientData() {
        this.databaseConnection = new DatabaseConnection();
    }

    public void addClient(String clientID, String lastname, String firstname, LocalDate dateOfBirth, String gender, String nationality, String relationship) {
        Connection conn = databaseConnection.connectToDatabase();
        if (conn == null) {
            System.err.println("Keine Verbindung zur Datenbank verfügbar.");
            return;
        }

        try (Statement stm = conn.createStatement()) {
            String add = "INSERT INTO Clients (ClientID, Lastname, Firstname, DateOfBirth, Gender, Nationality, Relationship) " +
                    "VALUES ('" + clientID + "', '" + lastname + "', '" + firstname + "', '" + dateOfBirth + "', '" +
                    gender + "', '" + nationality + "', '" + relationship + "')";
            stm.execute(add);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
