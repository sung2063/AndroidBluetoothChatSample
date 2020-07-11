package com.sung2063.bluetooth.server;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sung2063.bluetooth.R;
import com.sung2063.bluetooth.commands.IncomingType;
import com.sung2063.bluetooth.commands.Notification;
import com.sung2063.bluetooth.dialog.ErrorAlertDialog;
import com.sung2063.bluetooth.dialog.SupportDialog;
import com.sung2063.bluetooth.errors.ErrorType;
import com.sung2063.bluetooth.listener.EventListener;
import com.sung2063.bluetooth.model.ErrorDataModel;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

/**
 * The ServerConnectivity class helps to create the socket and send and receive the data from the client
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public class ServerConnectivity implements ErrorAlertDialog.AlertDialogEventListener {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private AppCompatActivity activity;
    private EventListener eventListener;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothServerSocket bluetoothServerSocket;
    private BluetoothDevice connectedDevice;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final String BLUETOOTH_SERVICE_NAME = "DEVICE_BLUETOOTH_SERVICE";
    private final UUID BLUETOOTH_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public ServerConnectivity(AppCompatActivity activity, EventListener eventListener) {

        this.activity = activity;
        this.eventListener = eventListener;

        // Check bluetooth adapter is ON
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            ErrorDataModel errorData = new ErrorDataModel(ErrorType.BLUETOOTH_OFF_ERROR, activity.getString(R.string.bluetooth_off_error));
            eventListener.onErrorOccurred(errorData);
        } else {
            // Bluetooth is ON
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.isEmpty()) {
                // There is no paired devices
                ErrorDataModel errorData = new ErrorDataModel(ErrorType.BLUETOOTH_OFF_ERROR, activity.getString(R.string.bluetooth_off_error));
                eventListener.onErrorOccurred(errorData);
            } else {
                // There is paired device
                for (BluetoothDevice device : pairedDevices) {

                    BluetoothClass bluetoothClass = device.getBluetoothClass();
                    int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
                    if (majorDeviceClass == BluetoothClass.Device.Major.PHONE) {
                        connectedDevice = device;
                        break;
                    }
                }

                if (connectedDevice == null) {
                    // There is no connected device
                    ErrorDataModel errorData = new ErrorDataModel(ErrorType.NO_PAIR_DEVICE_ERROR, activity.getString(R.string.no_paired_device_error));
                    eventListener.onErrorOccurred(errorData);
                } else {
                    // There is connected device
                    new AcceptClientAsyncTask().execute();
                }
            }
        }
    }

    private class AcceptClientAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(BLUETOOTH_SERVICE_NAME, BLUETOOTH_UUID);
            } catch (IOException e) {
                Log.d("mTag", "Error with creating server socket.");
                ErrorDataModel errorData = new ErrorDataModel(ErrorType.SOCKET_OPEN_ERROR, activity.getString(R.string.open_socket_error));
                eventListener.onErrorOccurred(errorData);
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            eventListener.onConnected();
            eventListener.onNotified(Notification.NOTIFICATION_ROOM_ESTABLISH);     // Notify the room is created

            try {
                BluetoothSocket bluetoothSocket = bluetoothServerSocket.accept();

                // Connected with Client ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                InputStream inputStream = bluetoothSocket.getInputStream();
                dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
                OutputStream outputStream = bluetoothSocket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                return true;

            } catch (IOException e) {
                Log.d("mTag", "Error with accepting client.");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean acceptingResult) {
            super.onPostExecute(acceptingResult);
            if (acceptingResult)
                new ServerIncomingMessage().execute();
            else
                onDestroy();
        }
    }

    private class ServerIncomingMessage extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {

                boolean isExit = false;
                int commandLength;
                while (!isExit && ((commandLength = dataInputStream.readInt()) != 0)) {

                    // Command =====================================================================
                    byte[] commandByte = new byte[commandLength];
                    boolean commandEnd = false;
                    StringBuilder commandString = new StringBuilder(commandLength);
                    int totalCommandBytesRead = 0;

                    while (!commandEnd) {
                        int currentBytesRead = dataInputStream.read(commandByte);
                        totalCommandBytesRead = currentBytesRead + totalCommandBytesRead;
                        if (totalCommandBytesRead <= commandLength)
                            commandString.append(new String(commandByte, 0, currentBytesRead, StandardCharsets.UTF_8));
                        else
                            commandString.append(new String(commandByte, 0, commandLength - totalCommandBytesRead + currentBytesRead, StandardCharsets.UTF_8));
                        if (commandString.length() >= commandLength)
                            commandEnd = true;
                    }

                    String command = commandString.toString();
                    String[] parsedData = commandString.toString().split("//");
                    int incomingType = Integer.parseInt(parsedData[1]);

                    if (incomingType == IncomingType.NOTIFICATION) {

                        int notificationType = Integer.parseInt(parsedData[2]);
                        eventListener.onNotified(notificationType);

                    } else if (incomingType == IncomingType.GENERAL_MESSAGE) {
                        // Message =====================================================================
                        int messageLength = dataInputStream.readInt();
                        byte[] messageByte = new byte[messageLength];
                        boolean messageEnd = false;
                        StringBuilder messageString = new StringBuilder(messageLength);
                        int totalMessageBytesRead = 0;

                        while (!messageEnd) {
                            int currentBytesRead = dataInputStream.read(messageByte);
                            totalMessageBytesRead = currentBytesRead + totalMessageBytesRead;
                            if (totalMessageBytesRead <= messageLength)
                                messageString.append(new String(messageByte, 0, currentBytesRead, StandardCharsets.UTF_8));
                            if (messageString.length() >= messageLength)
                                messageEnd = true;
                        }

                        String receivedMessage = messageString.toString();
                        if (receivedMessage.equalsIgnoreCase("ctrl + F4"))
                            isExit = true;
                        else
                            eventListener.onReceived(receivedMessage);
                    }
                }
                return true;

            } catch (IOException e) {
                Log.d("mTag", "DataInputStream is NULL.");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            SupportDialog supportDialog = SupportDialog.newInstance();
            supportDialog.show(activity.getSupportFragmentManager(), "SPONSOR_DIALOG");
            onDestroy();
        }
    }


    // =============================================================================================
    // Methods
    // =============================================================================================
    public void sendMessage(String message) {

        if (dataOutputStream != null) {
            try {

                String command = "//" + IncomingType.GENERAL_MESSAGE + "//-1";
                byte[] commandInBytes = command.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(commandInBytes.length);
                dataOutputStream.write(commandInBytes);

                byte[] dataInBytes = message.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(dataInBytes.length);
                dataOutputStream.write(dataInBytes);

            } catch (IOException e) {
                Log.d("mTag", "DataOutputStream is NULL.");
                ErrorDataModel errorData = new ErrorDataModel(ErrorType.CONNECTION_ERROR, activity.getString(R.string.device_error));
                eventListener.onErrorOccurred(errorData);
            }
        }

    }

    public void closeInputStream() {
        if (dataInputStream != null) {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                Log.d("mTag", "DataInputStream is NULL.");
            }
        }
    }

    public void closeOutputStream() {
        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                Log.d("mTag", "DataOutputStream is NULL.");
            }
        }
    }

    public void closeServerSocket() {
        if (bluetoothServerSocket != null) {
            try {
                bluetoothServerSocket.close();
            } catch (IOException e) {
                Log.d("mTag", "BluetoothServerSocket is NULL.");
            }
        }
    }

    public void onDestroy() {
        closeInputStream();
        closeOutputStream();
        closeServerSocket();
    }

    @Override
    public void onCloseClicked() {
        onDestroy();
        activity.finish();
    }

}
