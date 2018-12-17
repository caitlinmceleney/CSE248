package com.example.caitlin.tournamentmanager.Model;

import java.util.Date;

public class Message {
    private String message;
    private String messageSender;
    private String messageTime;
    private String messageDate;

    public Message(String message, String messageSender, String messageTime, String messageDate) {
        this.message = message;
        this.messageSender = messageSender;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public String toString() {
        return messageSender + ": \n" + message + "\n" + messageTime + "\t\t" + messageDate + "\n\n\n";
    }
}

