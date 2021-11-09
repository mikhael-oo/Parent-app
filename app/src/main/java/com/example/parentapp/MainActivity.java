package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parentapp.models.KidManager;

/**
 * This class displays the screen where you choose
 * whether to add kids, edit them, timeout your kid, and coin flip for
 * tasks
 */
public class MainActivity extends AppCompatActivity {

    private KidManager kidManager = KidManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupAllButtons();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    // set up the three required buttons on the screen
    public void setupAllButtons(){
        Button childrenEditBtn = findViewById(R.id.editKid);
        childrenEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = KidActivity.makeIntent(MainActivity.this);
               startActivity(intent);
            }
        });

        Button coinFlipBtn = findViewById(R.id.CoinFlip);
        coinFlipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(kidManager.returnKids().isEmpty()) {
                    Intent intent = CoinTossAct.makeIntent(MainActivity.this, null, false);
                    startActivity(intent);
                }
                else{
                    Intent intent = CoinFlipMenuActivity.makeIntent(MainActivity.this);
                    startActivity(intent);
                }

            }
        });


        // Need to point this to the preTimer activity

        Button timeoutBtn = findViewById(R.id.timeoutTimer);
        timeoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = PreTimer.makeIntent(MainActivity.this);
               startActivity(intent);
            }
        });
    }


}