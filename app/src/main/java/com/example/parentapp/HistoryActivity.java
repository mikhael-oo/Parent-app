package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.parentapp.models.Coin;

public class HistoryActivity extends AppCompatActivity {
    private Coin coin = Coin.getCoinInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void onStart()    {
        super.onStart();
        populateHistoryList();
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
            String[] record = coin.getRecord(position);
            EditListElems(historyView, record);
            return historyView;
        }

    }

    private void EditListElems(View historyView, String[] record) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(10);
        gd.setStroke(2, 0xFF000000);

        TextView name = historyView.findViewById(R.id.history_name);
        name.setText(record[0]);
        name.setBackground(gd);
        TextView choice = historyView.findViewById(R.id.history_choice);
        choice.setText(record[1]);
        choice.setBackground(gd);
        TextView date = historyView.findViewById(R.id.history_date);
        date.setText(record[2]);
        date.setBackground(gd);
        ImageView result = historyView.findViewById(R.id.history_result_img);
        result.setImageResource(R.drawable.loonie_head);
        // here change the image based on the result when you got the res\
        //
        //
        //
    }





    public static Intent makeIntent(Context context)   {
        Intent intent = new Intent(context, HistoryActivity.class);
        return intent;
    }
}