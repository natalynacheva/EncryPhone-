package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DataBase extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_ID = "ID";

    public DataBase(@Nullable Context context){

        super(context,"accounts.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatemnt = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + "TEXT, " + COLUMN_USER_EMAIL + "TEXT, " + COLUMN_USER_PASSWORD + "TEXT )";
        sqLiteDatabase.execSQL(createTableStatemnt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(AccountModel accountModel){
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME,  accountModel.getName());
        cv.put(COLUMN_USER_EMAIL, accountModel.getEmail());
        cv.put(COLUMN_USER_PASSWORD, accountModel.getPassword());

        long insert = sqLiteDatabase.insert(ACCOUNT_TABLE,null,cv);
        return insert != -1;
    }
}
