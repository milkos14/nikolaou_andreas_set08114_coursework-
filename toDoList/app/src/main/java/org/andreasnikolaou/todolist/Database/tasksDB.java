package org.andreasnikolaou.todolist.Database;

import android.provider.BaseColumns;

/**
 * Created by antre on 15/03/2018.
 */

public class tasksDB {

    public class Tasks implements BaseColumns {
        public static final String table_name = "Tasks";
        public static final String taskTitle = "Title";
    }
}
