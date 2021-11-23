package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;

/**
 * This class displays the screen where you choose
 * whether to add kids, edit them, timeout your kid, and coin flip for
 * tasks
 */
public class MainActivity extends AppCompatActivity {

    private KidManager kidManager = KidManager.getInstance();
    private Coin coin = Coin.getCoinInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupCoinSharedPrefData();
        setupTaskSharedPrefData();
        setupKidsSharedPrefData();

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



    private void setupCoinSharedPrefData() {

        ArrayList<String[]> historyListShared = SharedPrefsConfig.getCoinHistorySharedPrefsData(this);
        if(historyListShared != null) {
            for (String[] i : historyListShared) {
                coin.addToHistory(i[0], i[1], i[3]);
            }
        }
        ArrayList<Kid> coinQueue = SharedPrefsConfig.getCoinQueueSharedPrefsData(this);
        if(coinQueue != null)   {
            for(Kid i : coinQueue)  {
                coin.getTurnQueue().add(i);
            }
        }
    }



    private void setupTaskSharedPrefData() {
        ArrayList<Task> taskList = SharedPrefsConfig.getSavedTasksSharedPrefsData(this);
        if(taskList != null)    {
            TaskManager.getInstance().tasks = taskList;
        }
    }



    private void setupKidsSharedPrefData() {
        ArrayList<Kid> kids = SharedPrefsConfig.getKidsSharedPrefsData(this);
        if(kids != null)    {
            KidManager.getInstance().kids = kids;
            for(Kid i : kidManager) {
                i.setImage(null);
            }
            //setupKidImageSharedPrefData();
        }
    }


    private void setupKidImageSharedPrefData()  {

        ArrayList<String> images = SharedPrefsConfig.getKidImageSharedPrefsData(this);
        if(images != null) {
            for (int i = 0; i < images.size(); i++) {
                byte[] imageBytes = android.util.Base64.decode(images.get(i), android.util.Base64.DEFAULT);
                Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                KidManager.getInstance().kids.get(i).setImage(decodeImage);
            }
        }
    }
}