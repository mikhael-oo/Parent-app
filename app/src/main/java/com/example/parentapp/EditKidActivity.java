package com.example.parentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;

public class EditKidActivity extends AppCompatActivity {

    KidManager manager;
    EditText editInputName;
    String kidName;
    int position;

    public static Intent makeIntent(Context context) {
        return new Intent(context, KidActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_menu);

        manager = KidManager.getInstance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        position = b.getInt("kid Index");

        getSupportActionBar().setTitle("Add Kid!");

        editInputName = (EditText) findViewById(R.id.nameInput);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_kid:

                if(!(editInputName.getText().toString()).equals("")) {
                    kidName = (editInputName.getText().toString());
                }

                Kid newKid = new Kid(kidName);

                Toast.makeText(this, "Your Child has been edited", Toast.LENGTH_SHORT).show();

                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your game!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.action_delete_kid:
                Toast.makeText(this, "Deleting your game!! BYE BYE ", Toast.LENGTH_SHORT).show();
                finish();
                manager.removeKid(position);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


