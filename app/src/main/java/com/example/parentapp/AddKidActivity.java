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

public class AddKidActivity extends AppCompatActivity {

    KidManager manager;
    EditText inputKidName;
    String kidName;

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddKidActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_menu);

        manager = KidManager.getInstance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();


        getSupportActionBar().setTitle("Add Kid!");

        inputKidName = (EditText) findViewById(R.id.nameInput);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_kid:

                if(!(inputKidName.getText().toString()).equals("")) {
                    kidName = (inputKidName.getText().toString());
                }

                Kid newKid = new Kid(kidName);

                Toast.makeText(this, newKid.getName() +" has been added", Toast.LENGTH_SHORT).show();

                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your game!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
