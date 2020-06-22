package com.example.appcheckinbyqrcode.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "nnn";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "infoqr.db";

    // Table name: Note.
    private static final String TABLE_INFO = "info";

    // Table name: Favorite.
    private static final String TABLE_FAVORITE = "favorite";

    //COLUMN_INFO
    private static final String COLUMN_INFO_ID = "id";
    private static final String COLUMN_INFO_NAME ="name";
    private static final String COLUMN_INFO_EMAIL ="email";
    private static final String COLUMN_INFO_AVATAR = "avatar";
    private static final String COLUMN_INFO_PHONE = "phone";
    private static final String COLUMN_INFO_ADDRESS = "address";
    private static final String COLUMN_INFO_TIMECHECKIN = "timecheckin";

    //COLUMN_FAVORITE
    private static final String COLUMN_FAVORITE_ID = "idFavo";
    private static final String COLUMN_FAVORITE_EVENT_ID = "idEventFavo";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script. COLUMN_INFO
        String script = "CREATE TABLE " + TABLE_INFO + "(" +
                "COLUMN_INFO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INFO_EMAIL + " VARCHAR (255)," +
                COLUMN_INFO_NAME + " VARCHAR (255)," +
                COLUMN_INFO_AVATAR + " VARCHAR (255)," +
                COLUMN_INFO_PHONE + " VARCHAR (255)," +
                COLUMN_INFO_ADDRESS + " VARCHAR (255)," +
                COLUMN_INFO_TIMECHECKIN + " VARCHAR (255)" + ")";
        // Execute Script.
        db.execSQL(script);

        // Script. COLUMN_FAVORITE
        String favorites = "CREATE TABLE " + TABLE_FAVORITE + "(" +
                "COLUMN_FAVORITE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "COLUMN_FAVORITE_EVENT_ID  INTEGER"  + ")";
        // Execute favorites
        db.execSQL(favorites);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        // Create tables again
        onCreate(db);
    }

//    public void createDefaultNotesIfNeed()  {
//        int count = this.getNotesCount();
//        if(count ==0 ) {
//            InfoQR note1 = new InfoQR("Firstly see Android ListView",
//                    "See Android ListView Example in o7planning.org","aaa","aa","aa");
//            InfoQR note2 = new InfoQR("Learning Android SQLite",
//                    "See Android SQLite Example in o7planning.org","aaa","aa","aa");
////            this.addInfo(note1);
////            this.addInfo(note2);
//        }
//    }

    public void insertInfo(InfoQR infoQR) {
        Log.d(TAG, "insertInfo: inserting.......");

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_INFO+" (name, email, avatar, phone, address, timecheckin) VALUES (?,?,?,?,?,?)",
                new String[]{infoQR.name, infoQR.email, infoQR.avatar, infoQR.phone, infoQR.address, infoQR.timecheckin});
    }

    public void insertFavorite(FavoriteList favoriteList) {
        Log.d(TAG, "insertFavorite: inserting.......");

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_FAVORITE+" (idEvent) VALUES (?)",
                new String[]{String.valueOf(favoriteList.idEvent)});
    }


    //infor
    public List<InfoQR> getAllInfo() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<InfoQR> infoQRList = new ArrayList<InfoQR>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_INFO, null);//id, name, email, avatar, phone, address

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String email = cursor.getString(1);
            String name = cursor.getString(2);
            String avatar = cursor.getString(3);
            String phone = cursor.getString(4);
            String address = cursor.getString(5);
            String timecheckin = cursor.getString(6);
            Log.d(TAG, "getAllInfo: "+id +name+email+avatar+phone+ address + timecheckin + cursor.getString(6));
            infoQRList.add(new InfoQR(id, name, email, avatar, phone, address,timecheckin));
            cursor.moveToNext();
        }
        // return note list
        return infoQRList;
    }

    //Favorite
    public List<FavoriteList> getAllFavorite() {
        Log.i(TAG, "MyDatabaseHelper.getAllFavorite ... " );

        List<FavoriteList> favoriteList = new ArrayList<FavoriteList>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_FAVORITE, null);//id, idEvent, favoriteCheck

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int idEvent = cursor.getInt(1);
            Log.d(TAG, "getAllFavorite: "+id +idEvent + cursor.getString(2));
            favoriteList.add(new FavoriteList(id, idEvent));
            cursor.moveToNext();
        }
        // return favorite list
        return favoriteList;
    }
    // infor
    public int getInfoCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    // favorite
    public int getFavoriteount() {
        Log.i(TAG, "MyDatabaseHelper.getFavoriteount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_FAVORITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    //info
    public void deleteall() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_INFO);
    }

    //favorite
    public void deleteFavoriteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_FAVORITE);
    }




}
