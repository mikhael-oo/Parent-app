package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tester = findViewById(R.id.test);

        tester.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, preTimer.class);
            startActivity(intent);
        });
    }
}