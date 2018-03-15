package org.andreasnikolaou.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.andreasnikolaou.todolist.Database.dbHelper;
import org.andreasnikolaou.todolist.Database.tasksDB;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private dbHelper helper;
    private ListView taskListview;
    private ArrayAdapter<String> arrayAdapter;
    ArrayList<String> taskArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new dbHelper(this);
        taskListview = (ListView)findViewById(R.id.tasksListview);

        UpdateUI();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                Intent newTask = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(newTask);

                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void UpdateUI() {
        taskArrayList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(tasksDB.Tasks.table_name, new String[]{tasksDB.Tasks.taskTitle}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getColumnIndex(tasksDB.Tasks.taskTitle);
            taskArrayList.add(cursor.getString(id));
        }
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter(this, R.layout.task_list, R.id.taskTextview, taskArrayList);
            taskListview.setAdapter(arrayAdapter);
        }
        else {
            arrayAdapter.clear();
            arrayAdapter.addAll(taskArrayList);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public void onClickDone (View view) {
        View parent = (View) view.getParent();
        TextView taskTextview = (TextView) parent.findViewById(R.id.taskTextview);
        String string = String.valueOf(taskTextview.getText());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(tasksDB.Tasks.table_name, tasksDB.Tasks.taskTitle + " = ?", new String[]{string});
        db.close();
        UpdateUI();
    }

}
