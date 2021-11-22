package com.example.parentapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.KidManager;

/**
 * This activity is simply an animation demonstration of the coin flip
 * after the flip is completed, name of the kid will be search against the KidManager data
 * if kidName is not in the kid Manager then the toss will not be recorded in the history of tosses
 */
public class CoinTossAct extends AppCompatActivity {

    private final Coin coin = Coin.getCoinInstance();
    private final KidManager kidManager = KidManager.getInstance();
    public static final int DURATION_MILLIS = 50;
    private static final String KID_NAME_KEY = "The kid name for the new coin toss";
    private static final String KID_CHOICE_KEY = "The kid choice for the new coin toss";
    private static final String HEADS = "Heads";
    private static final String TAILS = "Tails";
    private static final String WON = "WON";
    private static final String LOST = "LOST";
    private String kidToTossName = null;
    private boolean kidChoice = false;
    private boolean isKidWinner = false;
    private static final int MAX_SPINS = 22;
    private boolean isTail = false;
    private int counter = MAX_SPINS;
    private MediaPlayer flipSound;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.coin_toss_title));
    }



    @Override
    protected void onStart() {
        super.onStart();

        extractDataFromIntent();
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation extraRotate = AnimationUtils.loadAnimation(this,R.anim.rotate_extra);
        ImageView coinImg = findViewById(R.id.coin_img);
        rotate.setAnimationListener(animRotateListener(coinImg, rotate, extraRotate));
        extraRotate.setAnimationListener(animRotateExtraListener(coinImg, rotate, extraRotate));
        startFlip(coinImg, rotate);
        counter = MAX_SPINS;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if(flipSound.isPlaying())   {
                    flipSound.stop();
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed() {
        if(flipSound != null)   {
            flipSound.stop();
        }
        finish();
    }



    private void startFlip(ImageView coinImg, Animation rotate) {
        isTail = !coin.toss();
        flipSound = MediaPlayer.create(
                this,
                R.raw.spinning_coin
        );
        playSound(flipSound);
        coinImg.startAnimation(rotate);
    }



    private void extractDataFromIntent() {

        Intent intent = getIntent();
        kidToTossName = intent.getStringExtra(KID_NAME_KEY);
        kidChoice = intent.getBooleanExtra(KID_CHOICE_KEY, false);
    }





    @NonNull
    private Animation.AnimationListener animRotateListener(ImageView coinImg, Animation anm, Animation ext) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if(counter >= 0 )   {
                    anm.setDuration(anm.getDuration() + anm.getDuration() / (counter + 1));
                    switchPictures(coinImg);
                    coinImg.startAnimation(ext);
                }
                counter--;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        };
    }





    @NonNull
    private Animation.AnimationListener animRotateExtraListener(ImageView coinImg, Animation anm, Animation ext) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(counter == 0 & isTail) counter = -1;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(counter >= 0 )   {
                    coinImg.startAnimation(anm);
                    ext.setDuration(ext.getDuration() + ext.getDuration() / (counter + 1));
                }
                if(counter < 0) {
                    tossResult();
                    resetAnimations(coinImg, anm, ext);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        };
    }





    private void tossResult() {

        if(kidChoice != isTail)     isKidWinner = true;
        if(kidManager.search(kidToTossName)) {
            String theChoice = kidChoice ? HEADS : TAILS;
            String outcome = isKidWinner ? WON : LOST;
            coin.addToHistory(kidToTossName, theChoice, outcome);
        }
        TextView result_tv = findViewById(R.id.result_tv);
        String res = isTail ? TAILS: HEADS;
        result_tv.setText(res);
        coin.kidFlippedCoin(kidToTossName);
    }





    private void resetAnimations(ImageView c, Animation anm, Animation ext) {
        counter = MAX_SPINS;
        anm.setDuration(DURATION_MILLIS);
        ext.setDuration(DURATION_MILLIS);
        if(isTail) {
            c.setImageResource(R.drawable.loonie_tail);
        }
        else{
            c.setImageResource(R.drawable.loonie_head);
        }

    }


    private void switchPictures(ImageView coinImg) {
        if(counter % 2 == 1)    {
            coinImg.setImageResource(R.drawable.loonie_tail);
        }
        if(counter % 2 == 0)    {
            coinImg.setImageResource(R.drawable.loonie_head);
        }
    }



    private void playSound(MediaPlayer s) {
        if(!s.isPlaying())   s.start();
    }


    public static Intent makeIntent(Context context, String kidName, boolean choice)   {
        Intent intent = new Intent(context, CoinTossAct.class);
        intent.putExtra(KID_NAME_KEY, kidName);
        intent.putExtra(KID_CHOICE_KEY, choice);
        return intent;
    }
}