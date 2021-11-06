package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;

import java.util.ArrayList;
import java.util.List;

public class CoinFlipMenuActivity extends AppCompatActivity {

    private Coin coin = Coin.getCoinInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip_menu);

        setupTossMenuBtn();
    }


    private void setupTossMenuBtn() {

        //create a search base on kid name in kid manager

        Button toss = (Button) findViewById(R.id.toss_menu_btn);
        toss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText kidNameEd = (EditText) findViewById(R.id.coin_kidName_editText);
                String kidName = kidNameEd.getText().toString();
                Intent intent = CoinTossAct.makeIntent(CoinFlipMenuActivity.this, kidName);
                startActivity(intent);
            }
        });
    }
}