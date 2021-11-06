package com.example.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parentapp.models.Coin;

public class CoinTossAct extends AppCompatActivity {
    private static String KID_NAME_KEY = "The kid name for the new coin toss";
    private String kidToTossName = null;
    private static final int MAX_SPINS = 15;
    private final Coin coin = Coin.getCoinInstance();
    private boolean isTail = false;
    private int counter = MAX_SPINS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);

        extractDataFromIntent();
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation extraRotate = AnimationUtils.loadAnimation(this,R.anim.rotate_extra);
        ImageView coinImg = findViewById(R.id.coin_img);
        rotate.setAnimationListener(animRotateListener(coinImg, rotate, extraRotate));
        extraRotate.setAnimationListener(animRotateExtraListener(coinImg, rotate, extraRotate));
        setupTossBtn(coinImg, rotate, extraRotate);
    }



    private void extractDataFromIntent() {

        Intent intent = getIntent();
        String name = intent.getStringExtra(KID_NAME_KEY);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        // Here validate if the kid name is not in the list of kids

        kidToTossName = name;
    }


    @Override
    protected void onStart() {
        super.onStart();
        counter = MAX_SPINS;
    }




    private void setupTossBtn(ImageView img, Animation anm, Animation ext) {

        Button tossBtn = findViewById(R.id.toss_btn);
        tossBtn.setOnClickListener(tossListener(img, anm, ext));
    }




    @NonNull
    private View.OnClickListener tossListener(ImageView img, Animation anm, Animation ext) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isTail = false;
                resetAnimations(img, anm, ext);
                if(!coin.toss())    isTail = true;
                img.startAnimation(anm);
            }
        };
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
                    swtichPictures(coinImg);
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
                    resetAnimations(coinImg, anm, ext);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        };
    }

    private void resetAnimations(ImageView c, Animation anm, Animation ext) {
        counter = MAX_SPINS;
        anm.setDuration(50);
        ext.setDuration(50);
        if(isTail) {
            c.setImageResource(R.drawable.loonie_tail);
        }
        else{
            c.setImageResource(R.drawable.loonie_head);
        }

    }


    private void swtichPictures(ImageView coinImg) {
        if(counter % 2 == 1)    {
            coinImg.setImageResource(R.drawable.loonie_tail);
        }
        if(counter % 2 == 0)    {
            coinImg.setImageResource(R.drawable.loonie_head);
        }
    }


    public static Intent makeIntent(Context context, String kidName)   {
        Intent intent = new Intent(context, CoinTossAct.class);
        intent.putExtra(KID_NAME_KEY, kidName);
        return intent;
    }

}