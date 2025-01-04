package com.example.projekt.sql;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

public class FileCRUD {

    Logger logger = Logger.getLogger(FileCRUD.class.getName());

    Connection connection;

    public FileCRUD(DatabaseConnection dbConnection) {
        this.connection = dbConnection.getConnection();
    }

    String insert = "INSERT INTO files (ClientID, Filename, FileData) VALUES (?, ?, ?)";


    public void insertFile(String clientID, String fileName, File fileData) {

        if (connection == null) {
            logger.severe("Keine aktive Datenbankverbindung vorhanden!");
            return;
        }

        if (!fileData.exists()) {
            logger.severe("Die Datei existiert nicht: " + fileData.getAbsolutePath());
            return;
        }

        try (
                FileInputStream inputStream = new FileInputStream(fileData);
                PreparedStatement statement = connection.prepareStatement(insert)
        ) {

            statement.setString(1, clientID);
            statement.setString(2, fileName);
            statement.setBinaryStream(3, inputStream, (int) fileData.length());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                logger.info("Datei wurde erfolgreich hochgeladen: " + fileName);
            } else {
                logger.warning("Die Datei konnte nicht hochgeladen werden.");
            }

        } catch (Exception e) {
            logger.severe("Fehler beim Hochladen der Datei: " + e.getMessage());
        }
    }
}
