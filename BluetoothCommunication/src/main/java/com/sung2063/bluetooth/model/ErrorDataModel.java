package com.sung2063.bluetooth.model;

/**
 * The ErrorDataModel class is a model to form a library error data
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public class ErrorDataModel {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private int errorType;
    private String errorMessage;

    // =============================================================================================
    // Constructor
    // =============================================================================================
    public ErrorDataModel(int errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
