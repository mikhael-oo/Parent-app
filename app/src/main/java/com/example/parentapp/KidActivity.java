package com.example.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parentapp.models.Kid;


public class KidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_start);

        setupAllButtons();
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
}
