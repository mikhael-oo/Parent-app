package com.example.parentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;

public class TaskHistoryList extends AppCompatActivity {

    Task thisTask;
    TaskManager manager = TaskManager.getInstance();
    private int position;


    public static Intent makeIntent(Context context) {
        return new Intent(context, TaskHistoryList.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        position = b.getInt("Given Task");
        thisTask = manager.returnTasks().get(position);

        getSupportActionBar().setTitle(thisTask.getTaskName() + thisTask.returnTaskHistory().size());
        populateTaskHistoryView();


    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void updateUI() {
        populateTaskHistoryView();
    }

    public void populateTaskHistoryView() {
        ArrayAdapter<Kid> adapter = new TaskHistoryList.MyListAdapter();
        ListView kidHistory = (ListView) findViewById(R.id.listViewTaskHistoryMenu);
        kidHistory.deferNotifyDataSetChanged();
        kidHistory.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Kid> {
        public MyListAdapter() {
            super(TaskHistoryList.this, R.layout.history_kid_item, thisTask.returnTaskHistory());
        }
        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.history_kid_item, parent, false);
            }
            Kid historyKid = thisTask.returnTaskHistory().get(position);

            @SuppressLint("ResourceType") TextView historyKidName = (TextView) itemView.findViewById(R.id.historyKidText);
            historyKidName.setText(historyKid.getName() + ": " + historyKid.getDate());

            @SuppressLint("ResourceType")ImageView historyKidImage = (ImageView) itemView.findViewById(R.id.historyKidMiniImage);
            historyKidImage.setImageBitmap(historyKid.getImage());



            return itemView;
        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = TaskListActivity.makeIntent(TaskHistoryList.this);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
