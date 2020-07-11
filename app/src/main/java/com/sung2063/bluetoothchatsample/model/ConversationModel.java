package com.sung2063.bluetoothchatsample.model;

import java.util.Date;

/**
 * The ConversationModel class is a data model to hold conversation data
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public class ConversationModel {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private int messageType;        // Either notification or message
    private boolean isOutGoingMessage;
    private String message;
    private Date date;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public ConversationModel() {
        // Default Constructor
    }

    public ConversationModel(int messageType, boolean isOutGoingMessage, String message, Date date) {
        this.messageType = messageType;
        this.isOutGoingMessage = isOutGoingMessage;
        this.message = message;
        this.date = date;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public boolean isOutGoingMessage() {
        return isOutGoingMessage;
    }

    public void setOutGoingMessage(boolean outGoingMessage) {
        isOutGoingMessage = outGoingMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }
}
