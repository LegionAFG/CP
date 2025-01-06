package com.example.projekt.sql;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

public class AppointmentCRUD {

    private static final Logger logger = Logger.getLogger(AppointmentCRUD.class.getName());


    DatabaseConnection dbConnection;

    public AppointmentCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    String INSERT_SQL = "INSERT INTO appointments (ClientID, AppointmentDate, AppointmentTime,Institution,PostCode,Street,Status) VALUES (?,?,?,?,?,?,?)";

    public void insertAppointment(String clientId, LocalDate date, LocalTime time, String institution, String postCode, String street, String status) {

        Connection connection = dbConnection.getConnection();

        if (connection == null){
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)){
            statement.setString(1,clientId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setTime(3, java.sql.Time.valueOf(time));
            statement.setString(4,institution);
            statement.setString(5,postCode);
            statement.setString(6,street);
            statement.setString(7,status);

            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                logger.info("Termin erfolgreich eingefügt: " + clientId + ", " + date + "," + time + "," + institution + "," + postCode + "," + street + "," + status);
            }else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }
            }catch (SQLException e){
            logger.severe("Fehler beim Einfügen des Termines: " + e.getMessage());
            }
        }
    }


