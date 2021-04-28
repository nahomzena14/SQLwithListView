package com.example.listviewwithsql.model.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.listviewwithsql.model.User;
import com.example.listviewwithsql.util.Position;


public class UserDatabaseHelper extends SQLiteOpenHelper {
    //Database
    public static final String DATABASE_NAME = "users.db";
    public static int DATABASE_VERSION = 1;

    //TABLE
    public static final String TABLE_NAME = "users";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String POSITION_COLUMN = "position";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            "("+ID_COLUMN +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME_COLUMN + " TEXT, "+
            POSITION_COLUMN + " TEXT)";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String update = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(update);
        onCreate(sqLiteDatabase);
    }

    public void insertUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN, user.getName());
        contentValues.put(POSITION_COLUMN, user.getPosition().name());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDB = getReadableDatabase();

        Cursor readCursor = sqLiteDB.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return readCursor;
    }



}
