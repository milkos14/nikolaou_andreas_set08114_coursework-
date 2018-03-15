package org.andreasnikolaou.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.andreasnikolaou.todolist.Database.dbHelper;
import org.andreasnikolaou.todolist.Database.tasksDB;

import java.util.ArrayList;

/**
 * Created by antre on 15/03/2018.
 */

public class NewTaskActivity extends AppCompatActivity {

    private dbHelper helper;
    ArrayList<String> taskArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity_layout);

        helper = new dbHelper(this);
        taskArrayList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_task:
                final EditText task = (EditText) findViewById(R.id.taskEditText);
                String newTask = String.valueOf(task.getText());
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                if (newTask.matches("[ ]*")) {
                    Toast.makeText(getBaseContext(), "You can't save an empty task", Toast.LENGTH_SHORT).show();
                }
                else {
                    values.put(tasksDB.Tasks.taskTitle, newTask);
                    db.insertWithOnConflict(tasksDB.Tasks.table_name, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    Intent main = new Intent(NewTaskActivity.this, MainActivity.class);
                    startActivity(main);

                    db.close();
                }
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
