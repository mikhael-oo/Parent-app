package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private static final long START_TIME_IN_MILLIS = 6000;

    private TextView countDowntext;
    private Button startPauseButton;
    private Button resetButton;

    private CountDownTimer countDownTimer;

    private boolean isTimerRunning;

    private long timeLeftInMillis = timer.getMinutes() * 60000;

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

        updateCountDown();
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDown();
        resetButton.setVisibility(View.INVISIBLE);
        startPauseButton.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
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
                startPauseButton.setText(R.string.startText);
                startPauseButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.VISIBLE);

                playSound();
                playVibrate();

            }
        }.start();

        isTimerRunning = true;
        startPauseButton.setText(R.string.pauseText);
        resetButton.setVisibility(View.INVISIBLE);
    }

    private void updateCountDown() {
        int minutes = (int) timeLeftInMillis / 60000;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDowntext.setText(timeLeft);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        startPauseButton.setText(R.string.startText);
        resetButton.setVisibility(View.VISIBLE);
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
}