package com.example.projekt.sql;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

public class HistoriesCRUD {

    Logger logger = Logger.getLogger(HistoriesCRUD.class.getName());
    Connection connection;

    public HistoriesCRUD(DatabaseConnection dbConnection) {
        this.connection = dbConnection.getConnection();
    }

    String insert = "INSERT INTO histories (ClientID, HistoryDate, HistoryTime, Title, Content) VALUES (?, ?, ?, ?, ?)";

    public void insertHistories(String clientID, LocalDate date, LocalTime time, String title, String content) {

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, clientID);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setTime(3, java.sql.Time.valueOf(time));
            statement.setString(4, title);
            statement.setString(5, content);

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                logger.info("Historie erfolgreich eingefügt: " + clientID + ", " + date + ", " + time + ", " + title + ", " + content);
            } else {
                logger.warning("Es wurde kein Datensatz eingefügt.");
            }

        } catch (SQLException e) {
            logger.severe("Fehler beim Einfügen des Termines: " + e.getMessage());
        }
    }
}
