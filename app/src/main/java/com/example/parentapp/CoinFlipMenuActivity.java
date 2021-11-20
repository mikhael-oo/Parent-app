package com.example.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;

/**
 * This Activity interacts with the user to input their desired kidName and choice of coin side
 * This Activity can navigate to history of coin tosses or to the coin toss
 */
public class CoinFlipMenuActivity extends AppCompatActivity {

    private final Coin coin = Coin.getCoinInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip_menu);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.coin_toss_menu_title));
        setupTossMenuBtn();
        setupHistoryBtn();
    }



    @Override
    public void onResume()   {
        super.onResume();
        coin.updateTurns();
        setupKidTurnTv();
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




    private void setupTossMenuBtn() {

        Button toss = findViewById(R.id.toss_menu_btn);
        RadioButton heads = findViewById(R.id.coin_menu_head_rb);
        RadioButton tails = findViewById(R.id.coin_menu_tail_rb);
        EditText kidNameEd = findViewById(R.id.coin_kidName_editText);


        toss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((heads.isChecked() || tails.isChecked())) {

                    String kidName = kidNameEd.getText().toString();
                    boolean choice = heads.isChecked();

                    if(!isThisKidTurn(kidNameEd, coin.getLastRecord())) {
                        Intent intent = CoinTossAct.makeIntent(CoinFlipMenuActivity.this, kidName, choice);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(CoinFlipMenuActivity.this, "Its not " + kidName + "'s Turn"
                                , Toast.LENGTH_LONG).show();
                    }
                }

                else    {
                    Toast.makeText(CoinFlipMenuActivity.this, "Please choose the outcome"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    private boolean isThisKidTurn(EditText kidNameEd, String[] lastRecord) {
        if(!coin.isHistoryEmpty()) {
            String kidName = kidNameEd.getText().toString();
            return kidName.equalsIgnoreCase(lastRecord[0]);
        }
        return false;
    }



    private void setupHistoryBtn() {

        Button histBtn = findViewById(R.id.coin_menu_history_btn);
        histBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = HistoryActivity.makeIntent(CoinFlipMenuActivity.this);
                startActivity(intent);
            }
        });
    }


    private void setupKidTurnTv() {
        Kid kid = coin.getTurnKid();
        TextView kidTurn = findViewById(R.id.coin_turn_name);
        Toast.makeText(this, coin.getCoinTurns().size() + " " + coin.getHistory().size() , Toast.LENGTH_LONG).show();
        kidTurn.setText(kid.getName() + "'s Turn to Toss");
    }


    public static Intent makeIntent(Context context)   {
        Intent intent = new Intent(context, CoinFlipMenuActivity.class);
        return intent;
    }
}