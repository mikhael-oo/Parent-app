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

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;

public class AddTaskActivity extends AppCompatActivity {

    TaskManager manager;
    EditText inputTaskName;
    String taskName;

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddTaskActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Add new Task!");

        manager = TaskManager.getInstance();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Toolbar toolbar = findViewById(R.id.toolbar);

        inputTaskName = (EditText) findViewById(R.id.taskNameInput);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_task,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_task:

                taskName = (inputTaskName.getText().toString());

                Task newTask = new Task(taskName);
                manager.addTask(newTask);

                Toast.makeText(this, newTask.getTaskName() +" has been added", Toast.LENGTH_SHORT).show();

                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your task!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
