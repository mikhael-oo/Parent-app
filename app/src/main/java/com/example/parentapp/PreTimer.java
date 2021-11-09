package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.parentapp.models.Timer;

public class PreTimer extends AppCompatActivity {

    private final String NOT_EMPTY = "No Empty Field Input Allowed";
    private final String POS_NUMBER_STRING = "Enter Positive number";
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Timeout Configuration");
        setContentView(R.layout.activity_pre_timer);
        timer = Timer.getTimerInstance();
        toTimer();
        populateMinutesGroup();
    }

    private void toTimer() {
        Button button = findViewById(R.id.toTimer);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(PreTimer.this, TimoutTimer.class);
            startActivity(intent);
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, PreTimer.class);
    }

    private void populateMinutesGroup() {
        RadioGroup group = findViewById(R.id.minutesGroup);
        Button customButton = findViewById(R.id.customButton);

        int[] minutes = getResources().getIntArray(R.array.minutes);

        for (int i = 0; i < minutes.length; i++) {
            int minute = minutes[i];
            if (minute != 0) {

                RadioButton button = new RadioButton(this);
                button.setText(minute+ " MINUTES");
                button.setTextColor(0xFFF6D205);

                group.addView(button);
                button.setOnClickListener(view -> timer.setMinutes(minute));
            } else {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setTextColor(0xFFF6D205);
                radioButton.setText(R.string.customTimeText);
                group.addView(radioButton);

                customButton.setOnClickListener(view -> {
                    if (radioButton.isChecked()) {
                        EditText number = findViewById(R.id.customNumber);
                        String input = number.getText().toString();

                        if (input.length() == 0) {
                            Toast.makeText(PreTimer.this, NOT_EMPTY, Toast.LENGTH_SHORT).show();
                        } else {
                            int num = Integer.parseInt(input);

                            if (num == 0) {
                                Toast.makeText(PreTimer.this, POS_NUMBER_STRING, Toast.LENGTH_SHORT).show();
                            } else {
                                timer.setMinutes(num);
                            }
                        }


                    }

                });
            }
        }
    }
}