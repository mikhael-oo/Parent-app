package com.example.parentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;

public class EditTaskActivity extends AppCompatActivity {

    private TaskManager manager;
    private EditText editInputTaskName;
    private String taskName;
    private int position;
    private Task editedTask;


    public static Intent makeIntent(Context context) {
        return new Intent(context, EditTaskActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbarEditTask);

        manager = TaskManager.getInstance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        position = b.getInt("Task Index");
        editedTask = manager.returnTasks().get(position);

        getSupportActionBar().setTitle("Edit " + editedTask.getTaskName());

        editInputTaskName = (EditText) findViewById(R.id.editTaskNameInput);
        editInputTaskName.setText(editedTask.getTaskName());

        Button checkTaskComplete = (Button) findViewById(R.id.taskCompleteCheck);
        checkTaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                editedTask.nextAssignee();
                Log.i("MyApp", "Task has been completed");
            }
        });

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
                taskName = (editInputTaskName.getText().toString());
                editedTask.setTaskName(taskName);

                Toast.makeText(this, "Your task has been edited", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your edit!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.action_delete_kid:
                Toast.makeText(this, "Deleting your " + editedTask.getTaskName() + "!! BYE BYE ", Toast.LENGTH_SHORT).show();
                finish();
                manager.removeTask(position);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
