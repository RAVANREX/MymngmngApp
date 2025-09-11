package com.example.mnymng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.example.mnymng.fragments.BottomDrawer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonOpenDrawer = findViewById(R.id.gp_to_home_button);
        buttonOpenDrawer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeWindow.class);
            startActivity(intent);
        });
    }
}