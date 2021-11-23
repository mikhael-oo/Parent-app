package com.example.parentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;

public class TaskListActivity extends AppCompatActivity {

    public static final String TASK_KEY_INDEX = "Task Index";
    public static final String YOU_HAVE_NO_TASKS = "You have no tasks!";
    public static final String BYE_BYE = "Bye bye!";
    TaskManager manager;

    public static Intent makeIntent(Context context) {
        return new Intent(context, TaskListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        manager = TaskManager.getInstance();
        registerClickOnList();
        getSupportActionBar().setTitle("List of Tasks!");
        populateTaskView();


    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void updateUI() {
        populateTaskView();
    }


    public void populateTaskView() {
        ArrayAdapter<Task> adapter = new TaskListActivity.MyListAdapter();
        ListView taskList = (ListView) findViewById(R.id.listViewTaskMenu);
        taskList.deferNotifyDataSetChanged();
        taskList.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Task> {
        public MyListAdapter() {
            super(TaskListActivity.this, R.layout.task_list_item, manager.returnTasks());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.task_list_item, parent, false);

            }
            Task currentTask = manager.returnTasks().get(position);

            @SuppressLint("ResourceType")
            TextView taskName = (TextView) itemView.findViewById(R.id.displayTaskNameText);
            taskName.setText(currentTask.getTaskName());
            TextView taskKid = (TextView) itemView.findViewById(R.id.displayTaskKidText);
            taskKid.setText(currentTask.getTaskKid());
            ImageView taskKidImage = itemView.findViewById(R.id.displayTaskKidImage);
            taskKidImage.setImageBitmap(currentTask.getTaskKidImage());

            return itemView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task,menu);
        return true;
    }

    private void registerClickOnList() {
        ListView taskList = (ListView) findViewById(R.id.listViewTaskMenu);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent editTaskIntent = new Intent(TaskListActivity.this, EditTaskActivity.class);
                Bundle editTaskBundle = new Bundle();
                editTaskBundle.putInt(TASK_KEY_INDEX, position);
                editTaskIntent.putExtras(editTaskBundle);
                startActivity(editTaskIntent);

            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = AddTaskActivity.makeIntent(TaskListActivity.this);
                startActivity(intent);
                return true;

            case android.R.id.home:
                Toast.makeText(this, BYE_BYE, Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
