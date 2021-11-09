package com.example.parentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
Kid activity guides user to adding a child or editing children shown in a listview
Has 2 simple buttons leading to either activity and will guide back to this screen when the
activities are done
 */
public class KidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_start);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        setupAllButtons();
        setTitle(getString(R.string.kid_menu_title));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, KidActivity.class);
    }

    public void setupAllButtons() {
        Button childrenEditBtn = findViewById(R.id.editChoice);
        childrenEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = KidMenuActivity.makeIntent(KidActivity.this);
                startActivity(intent);
            }
        });


        Button addKidBtn = findViewById(R.id.addChoice);
        addKidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddKidActivity.makeIntent(KidActivity.this);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Bye bye!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
