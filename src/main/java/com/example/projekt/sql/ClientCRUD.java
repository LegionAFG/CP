package com.example.projekt.sql;

import com.example.projekt.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientCRUD {

    private static final Logger logger = Logger.getLogger(ClientCRUD.class.getName());

    private final DatabaseConnection dbConnection;

    private static final String INSERT_SQL = "INSERT INTO clients (ClientID, Lastname, Firstname, Birthdate, Gender, Nationality, Relationship) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM" + " clients";

    public ClientCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insertClient(String clientId, String lastname, String firstname, LocalDate birthdate, String gender, String nationality, String relationship) {

        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, clientId);
            statement.setString(2, lastname);
            statement.setString(3, firstname);
            statement.setDate(4, java.sql.Date.valueOf(birthdate));
            statement.setString(5, gender);
            statement.setString(6, nationality);
            statement.setString(7, relationship);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Klient erfolgreich eingefügt: " + clientId + ", " + lastname + ", " + firstname + ", " + birthdate + ", " + gender + ", " + nationality + ", " + relationship);
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Einfügen des Klienten: " + e.getMessage(), e);
        }
    }

    public ObservableList<Client> getAllClients() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return clientList;
        }
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String clientId = resultSet.getString("ClientID");
                String lastname = resultSet.getString("Lastname");
                String firstname = resultSet.getString("Firstname");
                LocalDate birthdate = resultSet.getDate("Birthdate").toLocalDate();
                String gender = resultSet.getString("Gender");
                String nationality = resultSet.getString("Nationality");
                String relationship = resultSet.getString("Relationship");

                Client client = new Client(clientId, lastname, firstname, birthdate, gender, nationality, relationship);
                clientList.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Abrufen der Klienten: " + e.getMessage(), e);
        }
        return clientList;
    }
}
