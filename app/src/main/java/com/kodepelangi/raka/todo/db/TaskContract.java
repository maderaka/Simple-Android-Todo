package com.kodepelangi.raka.todo.db;

import android.provider.BaseColumns;

/**
 * @author  Raka Teja <rakatejaa@gmail.com>
 */
public class TaskContract {

    public static final String DB_NAME = "com.kodepelangi.todo";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns{
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }

}
