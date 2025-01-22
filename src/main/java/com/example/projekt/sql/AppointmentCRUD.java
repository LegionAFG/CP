package com.example.projekt.sql;

import com.example.projekt.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentCRUD {

    private static final Logger logger = Logger.getLogger(AppointmentCRUD.class.getName());

    DatabaseConnection dbConnection;

    public AppointmentCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private static final String INSERT_SQL = "INSERT INTO appointments (ClientID, AppointmentDate, AppointmentTime,Institution,City,Street,Status) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ID_SQL = "SELECT * FROM appointments WHERE clientID = ?";
    private static final String SELECT_APPOINTMENT_ID_SQL = "SELECT AppointmentID FROM appointments WHERE ClientID = ? AND AppointmentDate = ? AND AppointmentTime = ?";
    private static final String UPDATE_SQL;
    String SELECT_SQL;

    static {
        UPDATE_SQL =  "UPDATE appointments " +
                "SET AppointmentDate = ?, AppointmentTime = ?, Institution = ?, City = ?, Street = ?, Status = ? " +
                "WHERE ClientID = ? AND AppointmentID = ?";
    }

    {
        SELECT_SQL = "SELECT a.ClientID, " +
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

    public boolean insertAppointment(String clientId, LocalDate date, String time, String institution, String city, String street, String status) {

        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, clientId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, time);
            statement.setString(4, institution);
            statement.setString(5, city);
            statement.setString(6, street);
            statement.setString(7, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Termin erfolgreich eingefügt: " + clientId + ", " + date + "," + time + "," + institution + "," + city + "," + street + "," + status);
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Einfügen des Termines: " + e.getMessage());
        }
        return false;
    }

    public boolean updateAppointment(int appointmentId,String clientId, LocalDate appointmentDate, String appointmentTime,
                                     String institution, String city, String street, String status) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setDate(1, java.sql.Date.valueOf(appointmentDate));
            statement.setString(2, appointmentTime);
            statement.setString(3, institution);
            statement.setString(4, city);
            statement.setString(5, street);
            statement.setString(6, status);
            statement.setString(7, clientId);
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
                String clientID = resultSet.getString("ClientID");
                String lastname = resultSet.getString("Lastname");
                String firstname = resultSet.getString("Firstname");
                LocalDate date = resultSet.getDate("AppointmentDate").toLocalDate();
                LocalTime time = resultSet.getTime("AppointmentTime").toLocalTime();
                String institution = resultSet.getString("Institution");
                String city = resultSet.getString("City");
                String street = resultSet.getString("Street");
                String status = resultSet.getString("Status");

                Appointment appointment = new Appointment(clientID, lastname, firstname, date, time, institution, city, street, status);
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
                String street = rs.getString("street");
                String city = rs.getString("city");
                LocalDate date = rs.getDate("appointmentDate").toLocalDate();
                LocalTime time = rs.getTime("appointmentTime").toLocalTime();
                String institution = rs.getString("institution");
                String status = rs.getString("status");

                Appointment appointment = new Appointment(id, date, time, institution, city, street, status);
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
            e.printStackTrace();
        }

        return appointmentList;

    }

    public Integer getAppointmentId(String clientId, LocalDate date, String time) {

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_APPOINTMENT_ID_SQL)) {

            statement.setString(1, clientId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, time);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("AppointmentID");
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Abrufen der AppointmentID: " + e.getMessage());
        }
        return null;
    }



}


