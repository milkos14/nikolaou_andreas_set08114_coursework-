package org.andreasnikolaou.todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by antre on 15/03/2018.
 */

public class dbHelper extends SQLiteOpenHelper {

    public static final String database_name = "TasksDB";
    public static final int version = 1;

    public dbHelper(Context context) {

        super(context, database_name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTable = "CREATE TABLE " + tasksDB.Tasks.table_name + " (" +
                tasksDB.Tasks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                tasksDB.Tasks.taskTitle + " STRING NOT NULL" + " );";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tasksDB.Tasks.table_name);
        onCreate(db);
    }
}
