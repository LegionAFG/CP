package com.example.projekt.sql;

import com.example.projekt.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentCRUD {

    private static final Logger logger = Logger.getLogger(AppointmentCRUD.class.getName());

    DatabaseConnection dbConnection;

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public AppointmentCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private static final String INSERT_SQL = "INSERT INTO appointments (ClientID, AppointmentDate, AppointmentTime,Institution,City,Street,Status) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ID_SQL = "SELECT * FROM appointments WHERE clientID = ?";
    private static final String SELECT_APPOINTMENT_ID_SQL = "SELECT AppointmentID FROM appointments WHERE ClientID = ? AND AppointmentDate = ? AND AppointmentTime = ?";
    private static final String UPDATE_SQL;
    private static final String DELETE_ID_SQL = "DELETE FROM appointments WHERE AppointmentID = ?";
    String SELECT_SQL;

    static {
        UPDATE_SQL = "UPDATE appointments " +
                "SET AppointmentDate = ?, AppointmentTime = ?, Institution = ?, City = ?, Street = ?, Status = ? " +
                "WHERE ClientID = ? AND AppointmentID = ?";
    }

    {
        SELECT_SQL = "SELECT a.AppointmentID, " +
                "       a.ClientID, " +
                "       a.AppointmentDate, " +
                "       a.AppointmentTime, " +
                "       a.Institution, " +
                "       a.City, " +
                "       a.Street, " +
                "       a.Status, " +
                "       c.lastname AS Lastname, " +
                "       c.firstname AS Firstname " +
                "FROM appointments a " +
                "JOIN clients c ON a.ClientID = c.ClientID";
    }

    public boolean insertAppointment(String clientId, LocalDate date, LocalTime time, String institution, String city, String street, String status) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, clientId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, time.format(timeFormatter)); // Zeit im Format HH:mm
            statement.setString(4, institution);
            statement.setString(5, city);
            statement.setString(6, street);
            statement.setString(7, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Termin erfolgreich eingefügt: " + clientId + ", " + date + ", " + time.format(timeFormatter) + ", " + institution + ", " + city + ", " + street + ", " + status);
                return true;
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Einfügen des Termines: " + e.getMessage());
        }
        return false;
    }

    public boolean updateAppointment(int appointmentId, int clientId, LocalDate appointmentDate, LocalTime appointmentTime, String institution, String city, String street, String status) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setDate(1, java.sql.Date.valueOf(appointmentDate));
            statement.setString(2, appointmentTime.format(timeFormatter)); // Zeit im Format HH:mm
            statement.setString(3, institution);
            statement.setString(4, city);
            statement.setString(5, street);
            statement.setString(6, status);
            statement.setInt(7, clientId);
            statement.setInt(8, appointmentId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Termin erfolgreich aktualisiert: ClientID = " + clientId);
                return true;
            } else {
                logger.warning("Kein Datensatz aktualisiert. Überprüfen Sie die ClientID: " + clientId);
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Aktualisieren des Termins: " + e.getMessage());
        }
        return false;
    }

    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return appointmentList;
        }
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("AppointmentID");
                String clientID = resultSet.getString("ClientID");
                String lastname = resultSet.getString("Lastname");
                String firstname = resultSet.getString("Firstname");
                LocalDate date = resultSet.getDate("AppointmentDate").toLocalDate();
                LocalTime time = resultSet.getTime("AppointmentTime").toLocalTime();
                String institution = resultSet.getString("Institution");
                String city = resultSet.getString("City");
                String street = resultSet.getString("Street");
                String status = resultSet.getString("Status");

                Appointment appointment = new Appointment(clientID,appointmentID, lastname, firstname, date, time, institution, city, street, status);
                appointmentList.add(appointment);

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Abrufen der Termine: " + e.getMessage(), e);
        }
        return appointmentList;
    }

    public ObservableList<Appointment> getAppointmentsByClientId(String clientId) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            Connection connection = dbConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(SELECT_ID_SQL);
            statement.setString(1, clientId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                String id = rs.getString("ClientID");
                int appointmentId = rs.getInt("AppointmentID");
                String street = rs.getString("street");
                String city = rs.getString("city");
                LocalDate date = rs.getDate("appointmentDate").toLocalDate();
                LocalTime time = rs.getTime("appointmentTime").toLocalTime();
                String institution = rs.getString("institution");
                String status = rs.getString("status");

                Appointment appointment = new Appointment(id,appointmentId,date, time, institution, city, street, status);
                appointment.setId(id);
                appointment.setStreet(street);
                appointment.setCity(city);
                appointment.setDate(date);
                appointment.setTime(time);
                appointment.setInstitution(institution);
                appointment.setStatus(status);

                appointmentList.add(appointment);

            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Abrufen der Termine mit ClientID", e);
        }

        return appointmentList;

    }

    public Integer getAppointmentId(String clientId, LocalDate date, LocalTime time) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_APPOINTMENT_ID_SQL)) {

            statement.setInt(1, Integer.parseInt(clientId)); // ClientID als int
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, time.format(timeFormatter)); // Zeit im Format HH:mm

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("AppointmentID");
            } else {
                logger.warning("Keine Ergebnisse für ClientID=" + clientId + ", Date=" + date + ", Time=" + time.format(timeFormatter));
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Abrufen der AppointmentID: " + e.getMessage());
        }
        return null;
    }

    public boolean deleteAppointment(int appointmentId) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(DELETE_ID_SQL)) {
            statement.setInt(1, appointmentId);

            int rowDeleted = statement.executeUpdate();

            if (rowDeleted > 0) {
                logger.info("Termin mit ID " + appointmentId + " erfolgreich gelöscht.");
                return true;
            } else {
                logger.warning("Kein Termin mit ID " + appointmentId + " gefunden.");
                return false;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim löschen des Termines: " + e.getMessage(), e);
            return false;
        }
    }
}


