package com.sung2063.bluetooth.dialog;

import android.content.Intent;
import android.net.Uri;
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
 * The SupportDialog class is a handling dialog to support @sung2063
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public class SupportDialog extends DialogFragment {

    // =============================================================================================
    // Constructor
    // =============================================================================================
    public SupportDialog() {
        // Default Constructor
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    public static SupportDialog newInstance() {
        return new SupportDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.support_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Object Initialization
        Button btSupport = view.findViewById(R.id.bt_sponsor_button);
        Button btClose = view.findViewById(R.id.bt_close_button);

        // Responds
        btSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("https://github.com/sponsors/sung2063") );
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("https://github.com/") );
                startActivity( browse );
                dismiss();
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
