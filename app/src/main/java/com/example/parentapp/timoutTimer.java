package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parentapp.models.Timer;

import java.util.Locale;

public class timoutTimer extends AppCompatActivity {
    private Timer timer = Timer.getTimerInstance();
    private final long START_TIME = timer.getMinutes() * 60000;
    //private static final long START_TIME_IN_MILLIS = 60000;

    private TextView countDowntext;
    private Button startPauseButton;
    private Button resetButton;

    private CountDownTimer countDownTimer;

    private boolean isTimerRunning;

    private long timeLeftInMillis = START_TIME;

    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countDowntext = findViewById(R.id.countdown_text);
        startPauseButton = findViewById(R.id.start_pause_button);
        resetButton = findViewById(R.id.reset_button);

        startPauseButton.setOnClickListener(view -> {
            if(isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();

            }
        });

        resetButton.setOnClickListener(view -> {
            resetTimer();
        });

    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME;
        updateCountDown();
        updateButtons();
    }

    private void startTimer() {

        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                // l represents the time left in milliseconds
                timeLeftInMillis = l;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                updateButtons();

                playSound();
                playVibrate();

            }
        }.start();

        isTimerRunning = true;
        updateButtons();
    }

    private void updateCountDown() {
        int minutes = (int) timeLeftInMillis / 60000;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDowntext.setText(timeLeft);
    }

    private void updateButtons() {
        if (isTimerRunning) {
          resetButton.setVisibility(View.INVISIBLE);
          startPauseButton.setText(R.string.pauseText);
        } else {
            startPauseButton.setText(R.string.startText);

            if (timeLeftInMillis < 1000) {
                startPauseButton.setVisibility(View.INVISIBLE);
            } else {
                startPauseButton.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis < START_TIME) {
                resetButton.setVisibility(View.VISIBLE);
            } else {
                resetButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        updateButtons();
    }

    private void playSound() {
        MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.alarm);
        mp.start();
    }

    private void playVibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final VibrationEffect vibrationEffect;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrationEffect = VibrationEffect.createOneShot(9000,VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.cancel();
            vibrator.vibrate(vibrationEffect);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", isTimerRunning);
        editor.putLong("endTime", endTime);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        timeLeftInMillis = prefs.getLong("millisLeft", START_TIME);
        isTimerRunning = prefs.getBoolean("timerRunning",false);

        updateCountDown();
        updateButtons();

        if (isTimerRunning) {
            endTime = prefs.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
                isTimerRunning = false;
                updateCountDown();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }
}