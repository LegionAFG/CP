package com.example.projekt.service;

import com.example.projekt.sql.ClientCRUD;

import java.security.SecureRandom;
import java.util.Random;

public class IdService {

    private final ClientCRUD clientCRUD;
    private static final Random random = new SecureRandom();

    public IdService(ClientCRUD clientCRUD) {
        this.clientCRUD = clientCRUD;
    }

    private String generate6DigitNumber() {
        int num = 100000 + random.nextInt(900000);
        return String.valueOf(num);
    }

    public String generateUnique6DigitId() {
        String newId;
        int attempts = 0;
        int maxAttempts = 1000;

        do {
            newId = generate6DigitNumber();
            attempts++;
            if (attempts > maxAttempts) {
                throw new RuntimeException("Maximale Anzahl an Versuchen zur Generierung einer einzigartigen ID erreicht.");
            }
        } while (clientCRUD.isIdExists(newId)); // Wiederholen, falls ID schon existiert

        return newId;
    }
}
