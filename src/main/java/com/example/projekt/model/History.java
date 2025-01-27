package com.example.projekt.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class History {

    private int historieId;
    private int clientId;
    private LocalTime historyTime;
    private LocalDate historyDate;
    private String title;
    private String content;

    public History(int historieId, int clientId, LocalTime historyTime, LocalDate historyDate, String title, String content) {
        this.historieId = historieId;
        this.clientId = clientId;
        this.historyTime = historyTime;
        this.historyDate = historyDate;
        this.title = title;
        this.content = content;
    }

    public int getHistorieId() {
        return historieId;
    }

    public void setHistorieId(int historieId) {
        this.historieId = historieId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalTime getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(LocalTime historyTime) {
        this.historyTime = historyTime;
    }

    public LocalDate getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(LocalDate historyDate) {
        this.historyDate = historyDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
