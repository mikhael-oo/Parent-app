package com.example.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parentapp.models.Timer;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimoutTimer extends AppCompatActivity {
    private Timer timer = Timer.getTimerInstance();
    private long START_TIME = timer.getMinutes() * 60000;

    private TextView countDowntext;
    private Button startPauseButton;
    private Button resetButton;
    private Button stopAlarm;
    private TextView timerIntervalText;

    private CountDownTimer countDownTimer;

    private boolean isTimerRunning;
    private long timeLeftInMillis = START_TIME;
    private long endTime;

    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setTitle(getString(R.string.timeout_timer_title));
        countDowntext = findViewById(R.id.countdown_text);
        startPauseButton = findViewById(R.id.start_pause_button);
        resetButton = findViewById(R.id.reset_button);
        stopAlarm = findViewById(R.id.stopAlarm);
        timerIntervalText = findViewById(R.id.timerInterval);

        setUpPieChart();

         mp = MediaPlayer.create(getBaseContext(), R.raw.alarm);

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

        stopAlarm.setOnClickListener(view -> {
            stopSound();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timer_duration,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time_at_25:
                changeTimer(0.25);
                timerIntervalText.setText(getString(R.string.timer_interval) + " 25%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.time_at_50:
                changeTimer(0.50);
                timerIntervalText.setText(getString(R.string.timer_interval) + " 50%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.time_at_75:
                changeTimer(0.75);
                timerIntervalText.setText(getString(R.string.timer_interval) + " 75%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.time_at_100:
                // there is no need to do anything
                timerIntervalText.setText(getString(R.string.timer_interval) + " 100%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.time_at_200:
                changeTimer(2.0);
                timerIntervalText.setText(getString(R.string.timer_interval) + " 200%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.time_at_300:
                changeTimer(3.0);
                timerIntervalText.setText(getString(R.string.timer_interval) + " 300%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.time_at_400:
                changeTimer(4.0);
                timerIntervalText.setText(getString(R.string.timer_interval) + " 400%");
                timerIntervalText.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME;
        updateCountDown();
        updateTimerInterface();
        setUpPieChart();
    }

    // using this when the user wants to alter the time left
    private void changeTimer(double number) {
        if (isTimerRunning) {
            pauseTimer();
        }
        timeLeftInMillis = (long)((double)timeLeftInMillis * number);
        updateCountDown();
        updateTimerInterface();
        setUpPieChart();
        Toast.makeText(this, ""+ timeLeftInMillis, Toast.LENGTH_SHORT).show();
    }

    private void setUpPieChart() {
        double timeLeft = timeLeftInMillis / 1000;
        double timeSpent = (START_TIME - timeLeftInMillis) / 1000;
        float times[] = {(float) timeLeft, (float) timeSpent};
        String timeNames[] = {"Time Left", "Time Spent"};
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            pieEntries.add(new PieEntry(times[i], timeNames[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Time Graph");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);


        // Getting the chart
        PieChart chart = findViewById(R.id.timer_piechart);
        chart.setData(data);

        chart.invalidate();
    }

    private void startTimer() {

        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                // l represents the time left in milliseconds
                timeLeftInMillis = l;
                updateCountDown();
                setUpPieChart();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                updateTimerInterface();

                playSound();
                playVibrate();

            }
        }.start();

        isTimerRunning = true;
        updateTimerInterface();
    }



    private void updateCountDown() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60 ;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeft;

        if (hours > 0) {
            timeLeft = String.format(Locale.getDefault(),
                    "%d:%02d:%02d",hours, minutes, seconds);
        } else {
            timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }

        countDowntext.setText(timeLeft);
    }

    private void updateTimerInterface() {
        if (isTimerRunning) {
          resetButton.setVisibility(View.INVISIBLE);
          startPauseButton.setText(R.string.pauseText);
          stopAlarm.setVisibility(View.INVISIBLE);

        } else {

            startPauseButton.setText(R.string.startText);

            if (timeLeftInMillis < 1000) {
                startPauseButton.setVisibility(View.INVISIBLE);
                stopAlarm.setVisibility(View.VISIBLE);
            } else {
                startPauseButton.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis != START_TIME) {
                resetButton.setVisibility(View.VISIBLE);
            } else {
                resetButton.setVisibility(View.INVISIBLE);
                stopAlarm.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        updateTimerInterface();
    }

    private void playSound() {
        mp.start();
    }

    private void stopSound() {
        mp.stop();
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

        //editor.putLong("startTimeInMillis", START_TIME);
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", isTimerRunning);
        editor.putLong("endTime", endTime);
        editor.apply();


    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        //START_TIME = prefs.getLong("startTimeInMillis", timer.getMinutes() * 60000);
        timeLeftInMillis = prefs.getLong("millisLeft", START_TIME);
        isTimerRunning = prefs.getBoolean("timerRunning",false);

        updateCountDown();
        updateTimerInterface();

        if (isTimerRunning) {
            endTime = prefs.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
                isTimerRunning = false;
                updateCountDown();
                updateTimerInterface();
            } else {
                startTimer();
            }
        }
    }
}