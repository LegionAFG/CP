package com.example.projekt.sql;

import com.example.projekt.model.Client;
import com.example.projekt.service.IdService;
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
    private final IdService idService;

    private static final String INSERT_SQL = "INSERT INTO clients (ClientID, Lastname, Firstname, Birthdate, Gender, Nationality, Relationship) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM clients";
    private static final String CHECK_ID_SQL = "SELECT COUNT(*) AS count FROM clients WHERE ClientID = ?";
    private static final String SELECT_ID_SQL = "SELECT * FROM clients WHERE ClientID = ?";
    private static final String DELETE_ID_SQL = "DELETE FROM clients WHERE ClientID = ?";
    private static final String UPDATE_SQL = "UPDATE clients " + "SET Lastname = ?, Firstname = ?, Birthdate = ?, Gender = ?, " + "Nationality = ?, Relationship = ? " + "WHERE ClientID = ?";


    public ClientCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.idService = new IdService(this);
    }

    public boolean insertClient(String clientId, String lastname, String firstname, LocalDate birthdate, String gender, String nationality, String relationship) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
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
                return true;
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Einfügen des Klienten: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateClient(String clientId, String lastname, String firstname, LocalDate birthdate, String gender, String nationality, String relationship){
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setString(1, lastname);
            statement.setString(2, firstname);
            statement.setDate(3, java.sql.Date.valueOf(birthdate));
            statement.setString(4, gender);
            statement.setString(5, nationality);
            statement.setString(6, relationship);
            statement.setString(7, clientId);

            int rowUpdated = statement.executeUpdate();
            if(rowUpdated > 0){
                logger.info("Klient erfolgreich bearbeitet: "+ lastname + ", " + firstname + ", " + birthdate + ", " + gender + ", " + nationality + ", " + relationship);
                return true;
            } else {
                logger.warning("Es wurde kein Datensatz bearbeitet.");
                return false;
            }
            }catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim bearbeiten des Klienten: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteClient(String clientId) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(DELETE_ID_SQL)) {
            statement.setString(1, clientId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                logger.info("Klient mit ID " + clientId + " erfolgreich gelöscht.");
                return true;
            } else {
                logger.warning("Kein Klient mit ID " + clientId + " gefunden.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Einfügen des Klienten: " + e.getMessage(), e);
            return false;
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

    public boolean isIdExists(String clientId) {
        Connection connection = dbConnection.getConnection();
        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(CHECK_ID_SQL)) {
            statement.setString(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler bei isIdExists: " + e.getMessage(), e);
        }
        return false;
    }

    public ObservableList<Client> getClientByClientId(String clientId) {
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        try {
            Connection connection = dbConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(SELECT_ID_SQL);
            statement.setString(1, clientId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("ClientID");
                String lastname = rs.getString("Lastname");
                String firstname = rs.getString("Firstname");
                LocalDate date = rs.getDate("Birthdate").toLocalDate();
                String gender = rs.getString("Gender");
                String nationality = rs.getString("Nationality");
                String relationship = rs.getString("Relationship");

                Client client = new Client(id, lastname, firstname, date, gender, nationality, relationship);
                clientList.add(client);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Abrufen des Klienten: " + e.getMessage(), e);
        }

        return clientList;
    }
}
