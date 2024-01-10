package com.example.appplanandnote.model;

import java.time.LocalDate;

public class Reminder {
    private int reminderID;
    private String reminderName;
    private String reminderContent;
    private String dueDate;
    private int repeatCount;
    private int reminderStatus;
    private int cardID;

    //Hàm khởi tạo

    public Reminder(String reminderName, String reminderContent, String dueDate, int repeatCount, int reminderStatus, int cardID) {
        this.reminderName = reminderName;
        this.reminderContent = reminderContent;
        this.dueDate = dueDate;
        this.repeatCount = repeatCount;
        this.reminderStatus = reminderStatus;
        this.cardID = cardID;
    }

    //Phương thức GET, SET

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public void setReminderContent(String reminderContent) {
        this.reminderContent = reminderContent;
    }

    public String getDueDate() {
        int indexOfT = dueDate.indexOf('T');
        String datePart = dueDate.substring(0, indexOfT);
        return datePart;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }
}
