package com.sung2063.bluetoothchatsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The MainActivity is a starting point of application
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-05
 */
public class MainActivity extends AppCompatActivity {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private ConstraintLayout clServerContainer, clClientContainer;

    // =============================================================================================
    // Activity Life Cycle
    // =============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar Setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        // Object Initialization
        clServerContainer = (ConstraintLayout) findViewById(R.id.cl_server_button);
        clClientContainer = (ConstraintLayout) findViewById(R.id.cl_client_button);

        // Responds
        clServerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ServerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        clClientContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}