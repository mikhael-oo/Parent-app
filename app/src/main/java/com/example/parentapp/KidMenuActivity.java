package com.example.parentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;



public class KidMenuActivity extends AppCompatActivity {

    KidManager manager;

    public static Intent makeIntent(Context context){
        return new Intent(context, KidMenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_menu);

        manager = KidManager.getInstance();

        getSupportActionBar().setTitle("List of Kids!");
        populateKidView();


    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void updateUI() {
        populateKidView();
    }


    public void populateKidView() {

        ArrayAdapter<Kid> adapter = new MyListAdapter();

        ListView gameList = (ListView) findViewById(R.id.listViewKidMenu);
        gameList.deferNotifyDataSetChanged();
        gameList.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Kid> {
        public MyListAdapter() {
            super(KidMenuActivity.this, R.layout.kid_list_item, manager.returnGames());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.kid_list_item, parent, false);

            }
            Kid currentKid = manager.returnKids().get(position);

            @SuppressLint("ResourceType") TextView kidInfo = (TextView) itemView.findViewById(R.layout.kid_list_item);
            kidInfo.setText(currentKid.getName());

            return itemView;
        }

    }

    //clicks on item on the list
    private void registerClickOnList() {
        ListView gameList = (ListView) findViewById(R.id.listViewKidMenu);
        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent editIntent = new Intent(KidMenuActivity.this, AddKidActivity.class);
                Bundle editBundle = new Bundle();
                editIntent.putExtras(editBundle);
                startActivity(editIntent);

            }
        });
    }



}
