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

    String INSERT_SQL = "INSERT INTO appointments (ClientID, AppointmentDate, AppointmentTime,Institution,PostCode,Street,Status) VALUES (?,?,?,?,?,?,?)";
    String SELECT_SQL;

    {
        SELECT_SQL = "SELECT a.ClientID, " +
                "       a.AppointmentDate, " +
                "       a.AppointmentTime, " +
                "       a.Institution, " +
                "       a.PostCode, " +
                "       a.Street, " +
                "       a.Status, " +
                "       c.lastname AS Lastname, " +
                "       c.firstname AS Firstname " +
                "FROM appointments a " +
                "JOIN clients c ON a.ClientID = c.ClientID";
    }

    public void insertAppointment(String clientId, LocalDate date, LocalTime time, String institution, String postCode, String street, String status) {

        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, clientId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setTime(3, java.sql.Time.valueOf(time));
            statement.setString(4, institution);
            statement.setString(5, postCode);
            statement.setString(6, street);
            statement.setString(7, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Termin erfolgreich eingefügt: " + clientId + ", " + date + "," + time + "," + institution + "," + postCode + "," + street + "," + status);
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
        } catch (SQLException e) {
            logger.severe("Fehler beim Einfügen des Termines: " + e.getMessage());
        }
    }

    public ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection connection = dbConnection.getConnection();

        if (connection == null){
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return appointmentList;
        }
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SQL);
        ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()){
                String clientID = resultSet.getString("ClientID");
                String lastname = resultSet.getString("Lastname");
                String firstname = resultSet.getString("Firstname");
                LocalDate date = resultSet.getDate("AppointmentDate").toLocalDate();
                LocalTime time = resultSet.getTime("AppointmentTime").toLocalTime();
                String institution = resultSet.getString("Institution");
                String postCode = resultSet.getString("PostCode");
                String street = resultSet.getString("Street");
                String status = resultSet.getString("Status");

                Appointment appointment = new Appointment(clientID,lastname,firstname,date,time,institution,postCode,street,status);
                appointmentList.add(appointment);


            }

        }catch (SQLException e){
            logger.log(Level.SEVERE, "Fehler beim Abrufen der Termine: " + e.getMessage(), e);
        }
        return appointmentList;
    }


}


