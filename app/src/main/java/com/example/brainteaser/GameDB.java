package com.example.brainteaser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class GameDB {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_NOGP = "games_played";
    public static final String KEY_AVERAGE = "average";
    public static final String KEY_HIGHEST = "highest";

    private final String DATABASE_NAME = "gameDb";
    private final String DATABASE_TABLE = "gameTable";
    private final int VERSION = 1;

    private final Context ourContext;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public GameDB(Context context) {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" +
                    KEY_USERNAME + " TEXT PRIMARY KEY NOT NULL, " + KEY_PASSWORD + " TEXT NOT NULL, " + KEY_TOTAL + " INTEGER, " +
                    KEY_HIGHEST + " INTEGER, " + KEY_AVERAGE + " INTEGER, " + KEY_NOGP + " INTEGER);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

    public GameDB open() {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        ourDatabase.close();
    }

    public long createEntry(String username, String password) {
        int val = 0;
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);
        cv.put(KEY_NOGP, val);
        cv.put(KEY_HIGHEST, val);
        cv.put(KEY_TOTAL, val);
        cv.put(KEY_AVERAGE, val);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public int checkUser(String username, String password) {
        String[] columns = {KEY_USERNAME};
        String selection = KEY_USERNAME + " =? AND " + KEY_PASSWORD + " =?";
        String[] selectionArgs = {username, password};

        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        int cursorCount = 0;
        while (!cursor.isAfterLast()) {
            cursorCount++;
            cursor.moveToNext();
        }
        return cursorCount;
    }

    public void updateUser(String username, String password, int result) {
        int high , game, total, average;
        String[] columns = {KEY_HIGHEST, KEY_NOGP, KEY_TOTAL, KEY_AVERAGE};
        String selection = KEY_USERNAME + " =? AND " + KEY_PASSWORD + " =?";
        String[] selectionArgs = {username, password};

        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            high = cursor.getShort(cursor.getColumnIndex(KEY_HIGHEST));
            game = cursor.getShort(cursor.getColumnIndex(KEY_NOGP)) + 1;
            total = cursor.getShort(cursor.getColumnIndex(KEY_TOTAL));
            average = (total+result)/game;
            ContentValues cv = new ContentValues();
            if (result > high)
                cv.put(KEY_HIGHEST, result);
            cv.put(KEY_TOTAL, total + result);
            cv.put(KEY_NOGP, game);
            cv.put(KEY_AVERAGE, average);
            ourDatabase.update(DATABASE_TABLE, cv, KEY_USERNAME + " =? AND " + KEY_PASSWORD + " =?", new String[]{username, password} );
            Toast.makeText(ourContext, "Your score : " + result + " !", Toast.LENGTH_SHORT).show();
            //Toast.makeText(ourContext, "Data successfully uploaded !" , Toast.LENGTH_SHORT).show();
        }

    }

    public int highest(String username, String password)
    {
        int high;
        String selection = KEY_USERNAME + " =? AND " + KEY_PASSWORD + " =?";
        String[] columns = {KEY_HIGHEST};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, selection,new String[]{username, password}, null, null, null );
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
        {
            high = cursor.getShort(cursor.getColumnIndex(KEY_HIGHEST));
        }
        else
        {
            high = 0;
        }

        return high;
    }

    public ArrayList<Ranks> getAllHighest()
    {
        int i=1;
        ArrayList<Ranks> data = new ArrayList<Ranks>();
        String[] columns = {KEY_USERNAME, KEY_HIGHEST};
        String sort_order = KEY_HIGHEST + " DESC";
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, sort_order);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Ranks ranks = new Ranks(null, 0, 0);
            ranks.setRank(i);
            ranks.setScore(cursor.getShort(cursor.getColumnIndex(KEY_HIGHEST)));
            ranks.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            data.add(ranks);
            i++;
            cursor.moveToNext();
        }
        return data;
    }

    public ArrayList<Average> getAllAverage()
    {
        int i=1;
        ArrayList<Average> data = new ArrayList<Average>();
        String[] columns = {KEY_USERNAME, KEY_AVERAGE};
        String sort_order = KEY_AVERAGE + " DESC";
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, sort_order);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Average average = new Average(null, 0, 0);
            average.setRank(i);
            average.setScore(cursor.getShort(cursor.getColumnIndex(KEY_AVERAGE)));
            average.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            data.add(average);
            i++;
            cursor.moveToNext();
        }
        return data;
    }

}
