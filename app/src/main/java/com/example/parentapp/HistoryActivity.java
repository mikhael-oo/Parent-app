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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parentapp.models.Coin;


/**
 *  This Activity contains the history information of Coin class
 *  it will populate a listview according to the created View
 *  it prints the records of the history in each row
 *
 *  if kid gets deleted so will its history info
 *  if kid get edited so will its history info
 */
public class HistoryActivity extends AppCompatActivity {
    public static final int TEXTVIEW_PURPLE_BG = 0xED28015D;
    public static final int RADIUS_OF_TV = 10;
    public static final String WON = "WON";
    public static final int GOLD_COLOR = 0xFFF6D205;
    private final Coin coin = Coin.getCoinInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.history_activity_title));
    }



    @Override
    protected void onStart()    {
        super.onStart();
        populateHistoryList();
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



    private void populateHistoryList() {
        ArrayAdapter<String[]> adapter =  new MyListAdapter();
        ListView lv = findViewById(R.id.history_list);
        lv.setAdapter(adapter);
    }




    private class MyListAdapter extends ArrayAdapter<String[]>  {
        public MyListAdapter()  {
            super(HistoryActivity.this, R.layout.history_view, coin.getHistory());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)   {
            View historyView = convertView;
            if(historyView == null) {
                historyView = getLayoutInflater().inflate(R.layout.history_view, parent, false);
            }
            String[] record = coin.getRecord(coin.historyLength() - position - 1);
            EditListElems(historyView, record);
            return historyView;
        }
    }



    private void EditListElems(View historyView, String[] record) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(TEXTVIEW_PURPLE_BG);
        gd.setCornerRadius(RADIUS_OF_TV);
        gd.setStroke(2, GOLD_COLOR);

        TextView name = historyView.findViewById(R.id.history_name);
        name.setText(record[0]);
        name.setBackground(gd);
        name.setTextColor(GOLD_COLOR);
        TextView choice = historyView.findViewById(R.id.history_choice);
        choice.setText(record[1]);
        choice.setBackground(gd);
        choice.setTextColor(GOLD_COLOR);
        TextView date = historyView.findViewById(R.id.history_date);
        date.setText(record[2]);
        date.setBackground(gd);
        date.setTextColor(GOLD_COLOR);
        ImageView result = historyView.findViewById(R.id.history_result_img);
        if(record[3].equalsIgnoreCase(WON))   {
            result.setImageResource(R.drawable.history_winner_img);
        }
        else{
            result.setImageResource(R.drawable.history_lost_img);
        }

    }



    public static Intent makeIntent(Context context)   {
        Intent intent = new Intent(context, HistoryActivity.class);
        return intent;
    }
}