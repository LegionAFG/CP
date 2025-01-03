package com.example.projekt.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AddClient {

    private static final Logger logger = Logger.getLogger(AddClient.class.getName());

    private final DatabaseConnection dbConnection;

    public AddClient(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    public void insertUser(String clientId, String lastname, String firstname, String date, String gender, String nationality, String relationship ) {
        String sql = "INSERT INTO clients (ClientID, Lastname,Firstname,DateOfBirth,Gender,Nationality,Relationship) VALUES (?,?,?,?,?,?,?)";

        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clientId);
            statement.setString(2, lastname);
            statement.setString(3, firstname);
            statement.setString(4, date);
            statement.setString(5, gender);
            statement.setString(6, nationality);
            statement.setString(7, relationship);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("User erfolgreich eingefügt: " + clientId + ", " + lastname);
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
        }catch (SQLException e){
            logger.severe("Fehler beim Einfügen des Benutzers: " + e.getMessage());
        }
    }
}
