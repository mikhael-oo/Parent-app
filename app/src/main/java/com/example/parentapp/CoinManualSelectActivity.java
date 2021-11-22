package com.example.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;

import java.util.ArrayList;

public class CoinManualSelectActivity extends AppCompatActivity {

    public static final int TEXTVIEW_PURPLE_BG = 0xED28015D;
    public static final int RADIUS_OF_TV = 10;
    public static final int GOLD_COLOR = 0xFFF6D205;
    private final Coin coin = Coin.getCoinInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_manual_select);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        setTitle("Coin's Queue");
    }



    @Override
    protected void onStart()    {

        super.onStart();
        populateQueueList();
        registerClickCallback();
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



    private void populateQueueList() {
        ArrayAdapter<Kid> adapter = new MyListAdapter();
        ListView lv = findViewById(R.id.queue_list);
        lv.setAdapter(adapter);
    }



    private class MyListAdapter extends ArrayAdapter<Kid>  {
        public MyListAdapter()  {
            super(CoinManualSelectActivity.this, R.layout.queue_view, coin.getTurnQueue());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)   {
            View queueView = convertView;
            if(queueView == null) {
                queueView = getLayoutInflater().inflate(R.layout.queue_view, parent, false);
            }
            Kid kid = coin.getTurnQueue().get(position);
            EditListElems(queueView, kid, position);
            return queueView;
        }
    }


    private void EditListElems(View queueView, Kid kid, int pos) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(TEXTVIEW_PURPLE_BG);
        gd.setCornerRadius(RADIUS_OF_TV);
        gd.setStroke(2, GOLD_COLOR);

        TextView name = queueView.findViewById(R.id.queue_kidName_tv);
        name.setText(kid.getName());
        name.setBackground(gd);
        name.setTextColor(GOLD_COLOR);

        TextView pos_tv = queueView.findViewById(R.id.queue_pos_tv);
        pos++;
        pos_tv.setText("" + pos);
        pos_tv.setBackground(gd);
        pos_tv.setTextColor(GOLD_COLOR);
        // add a portrait
    }


    private void registerClickCallback() {
        ListView list = findViewById(R.id.queue_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View viewClicked,
                                    int position,
                                    long id) {

                Kid clickedKid = coin.getTurnQueue().get(position);
                coin.deleteFromTurns(clickedKid.getName());
                coin.addToStartOfQueue(clickedKid.getName());
                finish();
            }
        });
    }


    public static Intent makeIntent(Context context)   {
        Intent intent = new Intent(context, CoinManualSelectActivity.class);
        return intent;
    }
}