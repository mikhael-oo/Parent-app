package com.example.parentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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

/**
 * This activity adds an exhale/inhale feature by interacting with a button
 * and having a runnable object track our inhale/exhale time
 * then setting the state of UI accordingly
 */
public class BreathActivity extends AppCompatActivity {

    public static final int COMPLETE_EXHALE_DURATION = 10000;
    public static final int INCOMPLETE_EXHALE_DURATION = 2000;
    public static final String INHALE_BTN = "IN";
    public static final String INHALE_POST_TIP = "Release the button to start Exhaling";
    public static final String INHALE_POST_BTN = "OUT";
    public static final String EXHALE_HINT = "You can exhale";
    public static final String EXHALE_BTN = "OUT";
    public static final String FINISHED_HINT = "Successfully finished all breaths";
    public static final String FINISHED_BTN = "Good Job!";
    public static final String PRESS_IN_HINT = "Press IN to start your breath";
    public static final String BEGIN_HINT = "Press Begin to start your breath";
    public static final String BEGIN_BTN = "Begin";
    public static final  String TITLE = "Taking Breaths";
    public static final  String INVALID_NUM_BREATHS = "N must be between 1 and 10";
    private final int MAX_N = 10;
    private final String INHALE_HINT = "You are holding breaths .good job";

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
    private ImageView circle;

    private Animation inhale_anm;
    private Animation exhale_anm;
    private MediaPlayer inhale_music;
    private MediaPlayer exhale_music;



    private final Handler timerHandler = new Handler();
    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            seconds = seconds % 60;
            breathTime = seconds;
            exhaleTime = breathTime - inhaleTime;

            timerHandler.postDelayed(this, 500);

            if(breathTime == 3) {
                handleLargeInhale();
            }
            if(exhaleTime == 3 && isFinishedInhale) {
                handleFinishBreath();
            }
        }
    };


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath);


        begin = findViewById(R.id.breath_btn);
        hint = findViewById(R.id.breath_help_tv);
        circle = findViewById(R.id.circle_img);
        numBreathsEt = findViewById(R.id.breath_num_et);
        inhale_anm = AnimationUtils.loadAnimation(this, R.anim.breath_inhale);
        exhale_anm = AnimationUtils.loadAnimation(this, R.anim.breath_exhale);

        numBreaths = SharedPrefsConfig.getBreathPrefsSharedPrefs(this);
        numBreathsEt.setText(Integer.toString(numBreaths));

        inhale_music = MediaPlayer.create(this, R.raw.inhale_music);
        exhale_music = MediaPlayer.create(this, R.raw.exhale_music);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle(TITLE);

        setupBreathBtn();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                stopMusic(inhale_music);
                stopMusic(exhale_music);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed() {
        stopMusic(inhale_music);
        stopMusic(exhale_music);
        finish();
    }


    private void setupNumbreaths() {
        if(checkBreaths()) {
            if (isFirstInhale) {
                numBreaths = Integer.parseInt(numBreathsEt.getText().toString());
                isFirstInhale = false;
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
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
                            stopMusic(inhale_music);
                            resetUI();
                        } else {
                            handleExhale();
                        }
                    }
                }
                else{
                    Toast.makeText(BreathActivity.this, INVALID_NUM_BREATHS, Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }


    private boolean checkBreaths()  {
        return !numBreathsEt.getText().toString().equalsIgnoreCase("")
                && Integer.parseInt(numBreathsEt.getText().toString()) > 0
                && Integer.parseInt(numBreathsEt.getText().toString()) <= MAX_N;
    }


    private void handleSmallInhale() {
        hint.setText(INHALE_HINT);
        begin.setText(INHALE_BTN);

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        circle.startAnimation(inhale_anm);

        playSound(inhale_music);
    }


    private void handleLargeInhale() {

        hint.setText(INHALE_POST_TIP);
        begin.setText(INHALE_POST_BTN);
    }


    private void handleExhale() {

        isFinishedInhale = true;
        hint.setText(EXHALE_HINT);
        begin.setText(EXHALE_BTN);
        inhaleTime = breathTime;

        begin.setEnabled(false);
        exhale_anm.setDuration(COMPLETE_EXHALE_DURATION);
        circle.setImageResource(R.drawable.blue_circle);
        circle.startAnimation(exhale_anm);

        playSound(exhale_music);
    }


    private void handleFinishBreath() {
        startTime = 0;
        breathTime = 0;
        inhaleTime = 0;
        exhaleTime = 0;
        numBreaths--;
        if(numBreaths == 0) {
            hint.setText(FINISHED_HINT);
            begin.setText(FINISHED_BTN);
            isMidBreaths = false;
            begin.setEnabled(false);
        }
        else{
            hint.setText(PRESS_IN_HINT);
            begin.setText(INHALE_BTN);
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
            hint.setText(BEGIN_HINT);
            begin.setText(BEGIN_BTN);
            numBreathsEt.setEnabled(true);
            isFirstInhale = true;
        }
        else{
            hint.setText(PRESS_IN_HINT);
        }

        exhale_anm.setDuration(INCOMPLETE_EXHALE_DURATION);
        circle.startAnimation(exhale_anm);
    }


    private void playSound(MediaPlayer music) {

        if(inhale_music.isPlaying()) {
            inhale_music.stop();
            inhale_music = MediaPlayer.create(this, R.raw.inhale_music);
        }
        if(exhale_music.isPlaying()) {
            exhale_music.stop();
            exhale_music = MediaPlayer.create(this, R.raw.exhale_music);
        }
        music.start();
    }


    private void stopMusic(MediaPlayer music) {
        if(music.equals(inhale_music))  {
            inhale_music.stop();
            inhale_music = MediaPlayer.create(this, R.raw.inhale_music);
        }
        else{
            exhale_music.stop();
            exhale_music = MediaPlayer.create(this, R.raw.exhale_music);
        }
    }


    public static Intent makeIntent(Context context)   {
        return new Intent(context, BreathActivity.class);
    }
}