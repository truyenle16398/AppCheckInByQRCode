package com.example.appcheckinbyqrcode.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String TABLE_FAVORITE = "favo";

    private static final String COLUMN_INFO_ID = "id";
    private static final String COLUMN_INFO_NAME ="name";
    private static final String COLUMN_INFO_EMAIL ="email";
    private static final String COLUMN_INFO_AVATAR = "avatar";
    private static final String COLUMN_INFO_PHONE = "phone";
    private static final String COLUMN_INFO_ADDRESS = "address";
    private static final String COLUMN_INFO_TIMECHECKIN = "timecheckin";

    private static final String COLUMN_FAVORITE_ID = "idfavo";
    private static final String COLUMN_FAVORITE_EVENT_ID = "idevent";
    private static final String COLUMN_FAVORITE_EVENT_NAME ="name";
    private static final String COLUMN_FAVORITE_EVENT_CHAIRMAN = "chaiman";
    private static final String COLUMN_FAVORITE_EVENT_INTRO = "intro";
    private static final String COLUMN_FAVORITE_EVENT_IMAGE = "image";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_INFO + "(" +
                "COLUMN_INFO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INFO_EMAIL + " VARCHAR (255)," +
                COLUMN_INFO_NAME + " VARCHAR (255)," +
                COLUMN_INFO_AVATAR + " VARCHAR (255)," +
                COLUMN_INFO_PHONE + " VARCHAR (255)," +
                COLUMN_INFO_ADDRESS + " VARCHAR (255)," +
                COLUMN_INFO_TIMECHECKIN + " VARCHAR (255)" + ")";
        // Execute Script.


        // Script. COLUMN_FAVORITE
        String favorites = "CREATE TABLE " + TABLE_FAVORITE + "(" +
                "COLUMN_FAVORITE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FAVORITE_EVENT_NAME + " VARCHAR (255)," +
                COLUMN_FAVORITE_EVENT_INTRO + " VARCHAR (255)," +
                COLUMN_FAVORITE_EVENT_CHAIRMAN + " VARCHAR (255)," +
                COLUMN_FAVORITE_EVENT_IMAGE + " VARCHAR (255)," +
                COLUMN_FAVORITE_EVENT_ID + " INTEGER "  + ")";
        // Execute favorites
        db.execSQL(favorites);
        db.execSQL(script);


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

    public void insertFavoriteId(int a) {
        Log.d(TAG, "insertFavorite: inserting.......");

        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO "+TABLE_FAVORITE + " (idevent)VALUES (?)",
//                new String[]{String.valueOf(favoriteList.idEvent)});
        db.execSQL("INSERT INTO " + TABLE_FAVORITE + "(idevent) VALUES ("+a+")");
        db.close();
    }

    public void insertFavorite(FavoriteList favoriteList) {
        Log.d(TAG, "insertFavorite: inserting.......");

        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO "+TABLE_FAVORITE + " (idevent)VALUES (?)",
//                new String[]{String.valueOf(favoriteList.idEvent)});
        db.execSQL("INSERT INTO " + TABLE_FAVORITE + "(idevent,name, intro, chaiman, image) VALUES (?,?,?,?,?)",
                new String[]{favoriteList.idEvent.toString(), favoriteList.name, favoriteList.intro, favoriteList.chariman, favoriteList.image});
        db.close();
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

    public List<FavoriteList> getAllFavo() {
        Log.i(TAG, "MyDatabaseHelper.getAllFavo ... " );

        List<FavoriteList> favoriteLists = new ArrayList<FavoriteList>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_FAVORITE, null);//id, name, email, avatar, phone, address

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);

            String name = cursor.getString(1);
            String chaiman = cursor.getString(2);
            String intro = cursor.getString(3);
            String image = cursor.getString(4);
            Integer idevent = cursor.getInt(5);

            Log.d(TAG, "getAllInfo: "+id +idevent+name+chaiman+intro+ image + cursor.getString(5));

            favoriteLists.add(new FavoriteList(id, idevent, name, chaiman, intro, image));
            cursor.moveToNext();
        }
        // return note list
        return favoriteLists;
    }

    //Favorite
    public int getFavoriteID(int id) {
        Log.i(TAG, "MyDatabaseHelper.getAllFavorite ... " );

        List<FavoriteList> favoriteList = new ArrayList<FavoriteList>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_FAVORITE+" where idevent = " +id,null);//id, idEvent, favoriteCheck

        int count = cursor.getCount();
        // return favorite list
        return count;
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

    public void deleteall() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_INFO);
    }
    public boolean deleteFavoriteID(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "deleteFavoriteID: ");
//        db.execSQL("DELETE FROM "+TABLE_FAVORITE+" where TABLE_FAVORITE = " +id,null);

        return db.delete(TABLE_FAVORITE, COLUMN_FAVORITE_EVENT_ID + "=" + id, null) > 0;
    }

}
