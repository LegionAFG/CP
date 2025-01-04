package com.example.projekt.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ClientCRUD {

    private static final Logger logger = Logger.getLogger(ClientCRUD.class.getName());

    private final DatabaseConnection dbConnection;

    public ClientCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    String insert = "INSERT INTO clients (ClientID, Lastname,Firstname,Birthdate,Gender,Nationality,Relationship) VALUES (?,?,?,?,?,?,?)";


    public void insertClient(String clientId, String lastname, String firstname, LocalDate birthdate, String gender, String nationality, String relationship) {

        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, clientId);
            statement.setString(2, lastname);
            statement.setString(3, firstname);
            statement.setDate(4, java.sql.Date.valueOf(birthdate));
            statement.setString(5, gender);
            statement.setString(6, nationality);
            statement.setString(7, relationship);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Klient erfolgreich eingefügt: " + clientId + ", " + lastname + ", " + firstname + "," + birthdate + "," + gender + "," + nationality + "," + relationship);
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Einfügen des Klienten: " + e.getMessage());
        }
    }
}
