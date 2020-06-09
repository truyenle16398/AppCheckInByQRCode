package com.example.appcheckinbyqrcode.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "InfoQR";

    // Table name: Note.
    private static final String TABLE_INFO = "Info";

    private static final Integer COLUMN_INFO_ID = 0;
    private static final String COLUMN_INFO_NAME ="name";
    private static final String COLUMN_INFO_EMAIL ="email";
    private static final String COLUMN_INFO_AVATAR = "avatar";
    private static final String COLUMN_INFO_PHONE = "phone";
    private static final String COLUMN_INFO_ADDRESS = "address";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_INFO + "(" +
                COLUMN_INFO_EMAIL + " TEXT," +
                COLUMN_INFO_EMAIL + " TEXT," +
                COLUMN_INFO_NAME + " TEXT," +
                COLUMN_INFO_AVATAR + " TEXT," +
                COLUMN_INFO_PHONE + " TEXT," +
                COLUMN_INFO_ADDRESS + " TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);

        // Create tables again
        onCreate(db);
    }

    public void createDefaultNotesIfNeed()  {
        int count = this.getNotesCount();
        if(count ==0 ) {
            InfoQR note1 = new InfoQR("Firstly see Android ListView",
                    "See Android ListView Example in o7planning.org");
            InfoQR note2 = new InfoQR("Learning Android SQLite",
                    "See Android SQLite Example in o7planning.org");
            this.addInfo(note1);
            this.addInfo(note2);
        }
    }

    public void addInfo(InfoQR infoQR) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + infoQR.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INFO_NAME,infoQR.getName());
        values.put(COLUMN_INFO_EMAIL,infoQR.getEmail());
        values.put(COLUMN_INFO_AVATAR,infoQR.getAvatar());
        values.put(COLUMN_INFO_PHONE,infoQR.getPhone());
        values.put(COLUMN_INFO_ADDRESS,infoQR.getAddress());

        // Inserting Row
        db.insert(TABLE_INFO, null, values);

        // Closing database connection
        db.close();
    }
//
//
//    public InfoQR getinfo(int id) {
//        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_INFO, new String[] { COLUMN_INFO_NAME,
//                        COLUMN_INFO_NAME, COLUMN_INFO_NAME }, COLUMN_INFO_NAME + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        InfoQR note = new InfoQR(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return note
//        return note;
//    }
//
//
    public List<InfoQR> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<InfoQR> noteList = new ArrayList<InfoQR>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InfoQR infoQR = new InfoQR();
                infoQR.setName(cursor.getString(0));
                infoQR.setEmail(cursor.getString(1));
                infoQR.setAvatar(cursor.getString(2));
                // Adding note to list
                noteList.add(infoQR);
            } while (cursor.moveToNext());
        }
        // return note list
        return noteList;
    }
//
    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
//
//
//    public int updateNote(Note note) {
//        Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + note.getNoteTitle());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
//        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());
//
//        // updating row
//        return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?",
//                new String[]{String.valueOf(note.getNoteId())});
//    }
//
//    public void deleteNote(Note note) {
//        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle() );
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + " = ?",
//                new String[] { String.valueOf(note.getNoteId()) });
//        db.close();
//    }
}
