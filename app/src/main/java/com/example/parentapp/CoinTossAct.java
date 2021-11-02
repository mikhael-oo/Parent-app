package com.example.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class CoinTossAct extends AppCompatActivity {

    private int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        ImageView coinImg = (ImageView) findViewById(R.id.coin_img);
        rotate.setAnimationListener(animListener(coinImg, rotate));
        setupTossBtn(coinImg, rotate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        counter = 10;
    }


    private void setupTossBtn(ImageView img, Animation anm) {

        Button tossBtn = (Button) findViewById(R.id.toss_btn);
        tossBtn.setOnClickListener(tossListener(img, anm));
    }



    @NonNull
    private View.OnClickListener tossListener(ImageView img, Animation anm) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                img.startAnimation(anm);
            }
        };
    }


    @NonNull
    private Animation.AnimationListener animListener(ImageView coin, Animation anm) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                counter--;
                if(counter != 0) {
                    anm.setDuration(anm.getDuration() + 45);
                    coin.startAnimation(anm);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }
}