package com.example.parentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BreathActivity extends AppCompatActivity {

    public static final int COMPLETE_EXHALE_DURATION = 10000;
    public static final int INCOMPLETE_EXHALE_DURATION = 2000;
    private int numBreaths = 0;
    private long startTime = 0;
    private int breathTime = 0;
    private int inhaleTime = 0;
    private int exhaleTime = 0;
    private boolean isFirstInhale = true;
    private boolean isFinishedInhale = false;
    private boolean isMidBreaths = false;
    private EditText numBreathsEt;
    private Button begin;
    private TextView hint;
    private TextView timer;
    private ImageView circle;

    private Animation inhale_anm;
    private Animation exhale_anm;



    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            breathTime = seconds;
            exhaleTime = breathTime - inhaleTime;

            timer.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);

            if(breathTime == 3) {
                handleLargeInhale();
            }
            if(exhaleTime == 3 && isFinishedInhale) {
                handleFinishBreath();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath);


        begin = findViewById(R.id.breath_btn);
        hint = findViewById(R.id.breath_help_tv);
        timer = findViewById(R.id.breath_timer_tv);
        circle = findViewById(R.id.circle_img);
        numBreathsEt = findViewById(R.id.breath_num_et);
        inhale_anm = AnimationUtils.loadAnimation(this, R.anim.breath_inhale);
        exhale_anm = AnimationUtils.loadAnimation(this, R.anim.breath_exhale);

        numBreaths = SharedPrefsConfig.getBreathPrefsSharedPrefs(this);
        numBreathsEt.setText(Integer.toString(numBreaths));

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Taking Breaths");

        setupBreathBtn();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setupNumbreaths() {
        if(checkBreaths()) {
            if (isFirstInhale) {
                numBreaths = Integer.parseInt(numBreathsEt.getText().toString());
                isFirstInhale = false;
            }
        }
    }


    private void setupBreathBtn() {
        begin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setupNumbreaths();
                if(checkBreaths()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        circle.setImageResource(R.drawable.green_circle);
                        numBreathsEt.setEnabled(false);
                        handleSmallInhale();
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (breathTime < 3) {
                            resetUI();
                        } else {
                            handleExhale();
                        }
                    }
                }
                else{
                    Toast.makeText(BreathActivity.this, "Number of Breaths should be from 1 to 10", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }


    private boolean checkBreaths()  {
        return !numBreathsEt.getText().toString().equalsIgnoreCase("")
                && Integer.parseInt(numBreathsEt.getText().toString()) > 0
                && Integer.parseInt(numBreathsEt.getText().toString()) <= 10;
    }


    private void handleSmallInhale() {
        hint.setText("You are holding breaths .good job");
        begin.setText("IN");

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        circle.startAnimation(inhale_anm);
    }


    private void handleLargeInhale() {

        hint.setText("Release the button to start Exhaling");
        begin.setText("OUT");
    }


    private void handleExhale() {

        isFinishedInhale = true;
        hint.setText("You can breathing out");
        begin.setText("OUT");
        inhaleTime = breathTime;

        begin.setEnabled(false);
        exhale_anm.setDuration(COMPLETE_EXHALE_DURATION);
        circle.setImageResource(R.drawable.blue_circle);
        circle.startAnimation(exhale_anm);
    }


    private void handleFinishBreath() {
        startTime = 0;
        breathTime = 0;
        inhaleTime = 0;
        exhaleTime = 0;
        numBreaths--;
        if(numBreaths == 0) {
            hint.setText("Successfully finished all breaths");
            begin.setText("Good Job!");
            isMidBreaths = false;
            begin.setEnabled(false);
        }
        else{
            hint.setText("Press In to start your breath");
            begin.setText("IN");
            isFinishedInhale = false;
            isMidBreaths = true;
            begin.setEnabled(true);
        }
        numBreathsEt.setText(Integer.toString(numBreaths));
        timerHandler.removeCallbacks(timerRunnable);
        SharedPrefsConfig.setBreathPrefsSharedPrefs(this, numBreaths);
    }


    private void resetUI() {

        timerHandler.removeCallbacks(timerRunnable);
        if(!isMidBreaths) {
            hint.setText("Press Begin to start your breath");
            begin.setText("Begin");
            numBreathsEt.setEnabled(true);
            isFirstInhale = true;
        }
        else{
            hint.setText("Press IN to start your breath");
        }

        exhale_anm.setDuration(INCOMPLETE_EXHALE_DURATION);
        circle.startAnimation(exhale_anm);
    }


    public static Intent makeIntent(Context context)   {
        return new Intent(context, BreathActivity.class);
    }
}