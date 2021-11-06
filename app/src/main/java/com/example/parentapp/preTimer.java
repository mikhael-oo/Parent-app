package com.example.parentapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.parentapp.models.Timer;

public class preTimer extends AppCompatActivity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_timer);
        timer = Timer.getTimerInstance();
        toTimer();
        populateMinutesGroup();
    }

    private void toTimer() {
        Button button = findViewById(R.id.toTimer);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(preTimer.this, timoutTimer.class);
            startActivity(intent);
        });
    }


    private void populateMinutesGroup() {
        RadioGroup group = findViewById(R.id.minutesGroup);

        int[] minutes = getResources().getIntArray(R.array.minutes);

        for (int i = 0; i < minutes.length; i++) {
            int minute = minutes[i];
            if (minute != 0) {

                RadioButton button = new RadioButton(this);
                button.setText(minute+ " MINS");

                group.addView(button);
                button.setOnClickListener(view -> {
                    timer.setMinutes(minute);
                });
            } else {
                RadioButton button = new RadioButton(this);
                button.setText("CUSTOM TIME");
                group.addView(button);

                button.setOnClickListener(view -> {
                    EditText number = findViewById(R.id.customNumber);
                    int num = Integer.parseInt(number.getText().toString());
                    timer.setMinutes(num);
                });
            }
        }
    }
}