package com.kodepelangi.raka.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Raka Teja <rakatejaa@gmail.com>
 */
public class TaskDBHelper extends SQLiteOpenHelper{

    /**
     *
     * @param context android.content.Context
     */
    public TaskDBHelper(Context context){
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    /**
     *
     * @param db android.database.sqlite.SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = String.format("CREATE TABLE %s ("+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"%s TEXT)", TaskContract.TABLE, TaskContract.Columns.TASK);
        Log.d("TaskDBHelper","Query to form table : "+sqlQuery);
        db.execSQL(sqlQuery);
    }

    /**
     *
     * @param db android.database.sqlite.SQLiteDatabase
     * @param oldVersion int
     * @param newVersion int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TaskContract.TABLE);
        onCreate(db);
    }
}
