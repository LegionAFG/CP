package com.example.projekt.sql;

import com.example.projekt.model.History;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryCRUD {

    private static final Logger logger = Logger.getLogger(HistoryCRUD.class.getName());

    private final DatabaseConnection dbConnection;

    private static final String INSERT_SQL = "INSERT INTO histories (ClientID, HistoryDate, HistoryTime, Title, Content) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ID_SQL = "SELECT * FROM histories WHERE clientID = ?";

    public HistoryCRUD(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insertHistories(String clientID, LocalDate date, LocalTime time, String title, String content) {
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
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
            logger.severe("Fehler beim Einfügen des Termins: " + e.getMessage());
        }
    }

    public ObservableList<History> getHistoryByClientId(String clientId) {
        ObservableList<History> histories = FXCollections.observableArrayList();
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return histories;
        }

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ID_SQL)) {
            statement.setString(1, clientId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ClientID");
                    int historyId = rs.getInt("HistoryID");
                    LocalDate date = rs.getDate("HistoryDate").toLocalDate();
                    LocalTime time = rs.getTime("HistoryTime").toLocalTime();
                    String title = rs.getString("Title");
                    String content = rs.getString("Content");

                    histories.add(new History(historyId, id, date, time, title, content));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Abrufen der Historie mit ClientID", e);
        }

        return histories;
    }
}
