package sg.edu.rp.c346.id19047433.l12_taskmanagerwear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Tasks.db";
    private static final int DATABASE_VERSION = 2;
    private static final String COLUMN_ID = "_id";
    private static final String TABLE_NOTE = "tasks";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "Description";

    //DBHelper constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NAME+ " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT ) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "Created tables");
        //dummy data
        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_NAME, "Buy Milk");
        values1.put(COLUMN_DESCRIPTION, "Low Fat");
        db.insert(TABLE_NOTE, null, values1);
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_NAME, "Post letters");
        values2.put(COLUMN_DESCRIPTION, "Get stamps from car");
        db.insert(TABLE_NOTE, null, values2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }

    //DBHelper Insert new notes
    public long insertTask (String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_NOTE, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Tasks> getItemsOfTasks() {
        // Create an ArrayList that holds String objects
        ArrayList<Tasks> tasks = new ArrayList<>();
        // Select all the tasks' description
        String selectQuery = "SELECT " + COLUMN_NAME+"," + COLUMN_DESCRIPTION
                + " FROM " + TABLE_NOTE;



        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                // Add the task content to the ArrayList object
                //  getString(0) retrieves first column data
                //  getString(1) return second column data
                //  getInt(0) if data is an integer value
                String name = cursor.getString(0);
                String desc = cursor.getString(1);
                Tasks task =new Tasks(name,desc);
                tasks.add(task);


            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return tasks;
    }

    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }
}