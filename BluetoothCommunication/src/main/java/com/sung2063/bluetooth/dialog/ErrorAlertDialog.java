package com.sung2063.bluetooth.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sung2063.bluetooth.R;

/**
 * The ErrorAlertDialog class handles the error message dialog in the library
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public class ErrorAlertDialog extends DialogFragment {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private String message;
    private AlertDialogEventListener eventListener;

    // =============================================================================================
    // Constructor
    // =============================================================================================
    public ErrorAlertDialog(String message, AlertDialogEventListener eventListener) {
        this.message = message;
        this.eventListener = eventListener;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    public static ErrorAlertDialog newInstance(String message, AlertDialogEventListener alertDialogEventListener) {
        return new ErrorAlertDialog(message, alertDialogEventListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alert_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Object Initialization
        final TextView tvErrorMessage = view.findViewById(R.id.tv_error_message);
        Button btClose = view.findViewById(R.id.bt_close_button);

        // Populate Data
        tvErrorMessage.setText(message);

        // Responds
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                eventListener.onCloseClicked();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // =============================================================================================
    // Interface
    // =============================================================================================
    public interface AlertDialogEventListener {
        void onCloseClicked();
    }
}
