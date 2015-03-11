package com.kodepelangi.raka.todo;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kodepelangi.raka.todo.db.TaskContract;
import com.kodepelangi.raka.todo.db.TaskDBHelper;


public class MainActivity extends ListActivity {

    protected TaskDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_task) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a Task");
            builder.setMessage("What do you want do?");
            final EditText editText = new EditText(this);
            builder.setView(editText);

            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String task = editText.getText().toString();
                    Log.d("MainActivity", task);

                    helper = new TaskDBHelper(MainActivity.this);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    values.clear();
                    values.put(TaskContract.Columns.TASK, task);
                    db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                    prepareData();

                }
            });

            builder.setNegativeButton("Cancel",null);
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDoneButtonClick(View view){
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'", TaskContract.TABLE, TaskContract.Columns.TASK, task);

        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);

        prepareData();

    }

    protected void prepareData() {
        SQLiteDatabase sqLiteDatabase = new TaskDBHelper(this).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TaskContract.TABLE,
                new String[] {TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

        cursor.moveToFirst();

        ListAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.task_view, cursor, new String[] {TaskContract.Columns.TASK}, new int[] {R.id.taskTextView},0);

        this.setListAdapter(listAdapter);
    }
}
