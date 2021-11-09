package com.example.parentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;


/*
KidMenu lists every child in the KidManager arraylist and allows click on
the children to access their edit screen.
Updates whenever changes are made in terms of edits and deletions
 */
public class KidMenuActivity extends AppCompatActivity {

    KidManager manager;

    public static Intent makeIntent(Context context) {
        return new Intent(context, KidMenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_menu);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        manager = KidManager.getInstance();
        registerClickOnList();

        getSupportActionBar().setTitle("List of Kids!");
        populateKidView();


    }

    public void checkEmpty(){
        if(manager.returnKids() == null) {
            Toast.makeText(this, "You have no kids!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        checkEmpty();
    }


    private void updateUI() {
        populateKidView();
    }


    public void populateKidView() {
        ArrayAdapter<Kid> adapter = new MyListAdapter();
        ListView kidList = (ListView) findViewById(R.id.listViewKidMenu);
        kidList.deferNotifyDataSetChanged();
        kidList.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Kid> {
        public MyListAdapter() {
            super(KidMenuActivity.this, R.layout.kid_list_item, manager.returnKids());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.kid_list_item, parent, false);

            }
            Kid currentKid = manager.returnKids().get(position);

            @SuppressLint("ResourceType") TextView kidInfo = (TextView) itemView.findViewById(R.id.kidText);
            kidInfo.setText(currentKid.getName());

            return itemView;
        }

    }

    //clicks on item on the list
    private void registerClickOnList() {
        ListView kidList = (ListView) findViewById(R.id.listViewKidMenu);
        kidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent editIntent = new Intent(KidMenuActivity.this, EditKidActivity.class);
                Bundle editBundle = new Bundle();
                editBundle.putInt("Kid Index", position);
                editIntent.putExtras(editBundle);
                startActivity(editIntent);

            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Bye bye!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
