package com.sung2063.bluetooth.listener;

import com.sung2063.bluetooth.errors.ErrorType;
import com.sung2063.bluetooth.model.ErrorDataModel;

/**
 * The EventListener interface offers the library event methods to the user
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public interface EventListener {

    void onConnected();

    void onNotified(int notificationType);

    void onReceived(String message);

    void onErrorOccurred(ErrorDataModel errorData);

}
