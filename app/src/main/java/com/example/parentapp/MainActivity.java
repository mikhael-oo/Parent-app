package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.KidManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class displays the screen where you choose
 * whether to add kids, edit them, timeout your kid, and coin flip for
 * tasks
 */
public class MainActivity extends AppCompatActivity {

    public static final String COIN_HISTORY_SHAREDPREF_KEY = "History_Key";
    private KidManager kidManager = KidManager.getInstance();
    private Coin coin = Coin.getCoinInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String[]> historyListShared = getCoinSharedPrefsData();
        if(historyListShared != null) {
            for (String[] i : historyListShared) {
                coin.addToHistory(i[0], i[1], i[3]);
            }
        }
        setupAllButtons();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MainActivity.class);
    }



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



        Button timeoutBtn = findViewById(R.id.timeoutTimer);
        timeoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = PreTimer.makeIntent(MainActivity.this);
               startActivity(intent);
            }
        });


        Button helpBtn = findViewById(R.id.helpButton);
        helpBtn.setOnClickListener(view -> {
            Intent intent = HelpScreen.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }


    private ArrayList<String[]> getCoinSharedPrefsData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String historyJsonString = prefs.getString(COIN_HISTORY_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String[]>>()    {}.getType();
        ArrayList<String[]> savedHistory = gson.fromJson(historyJsonString, type);
        return savedHistory;

    }
}