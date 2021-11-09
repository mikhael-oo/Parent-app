package com.example.parentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;

/*
Edits the child's name and supports deletion of the child as well, it takes a bundle of the
position of the child and sets it to the array position of the kidmanager and allows access to the
child's name
 */

public class EditKidActivity extends AppCompatActivity {

    private Coin coin = Coin.getCoinInstance();
    private KidManager manager;
    private EditText editInputName;
    private String kidName;
    private int position;
    private Kid editedKid;


    public static Intent makeIntent(Context context) {
        return new Intent(context, EditKidActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_edit);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);

        manager = KidManager.getInstance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        position = b.getInt("Kid Index");
        editedKid = manager.returnKids().get(position);

        getSupportActionBar().setTitle("Edit " + editedKid.getName() + "'s name!");

        editInputName = (EditText) findViewById(R.id.editNameInput);
        editInputName.setText(editedKid.getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_kid,menu);
        getMenuInflater().inflate(R.menu.menu_save_kid,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_kid:

                kidName = (editInputName.getText().toString());
                coin.editHistory(editedKid.getName(), kidName);
                editedKid.setName(kidName);

                Toast.makeText(this, "Your kid has been edited", Toast.LENGTH_SHORT).show();

                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your edit!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.action_delete_kid:
                Toast.makeText(this, "Deleting your " + editedKid.getName() + "!! BYE BYE ", Toast.LENGTH_SHORT).show();
                finish();
                coin.deleteFromHistory(kidName);
                manager.removeKid(position);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


