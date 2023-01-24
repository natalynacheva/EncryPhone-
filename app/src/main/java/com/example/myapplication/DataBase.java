package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DataBase extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_ID = "ID";
    SQLiteDatabase db;
    public DataBase(@Nullable Context context){

        super(context,"accounts.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatemnt = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT" + ")";
        sqLiteDatabase.execSQL(createTableStatemnt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String createTableStatemnt = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT" + ")";
        sqLiteDatabase.execSQL(createTableStatemnt);
    }

    public boolean addOne(AccountModel accountModel){
        db =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME,  accountModel.getName());
        cv.put(COLUMN_USER_EMAIL, accountModel.getEmail());
        cv.put(COLUMN_USER_PASSWORD, accountModel.getPassword());

        long insert = db.insert(ACCOUNT_TABLE,null,cv);
        return insert != -1;
    }
    //TODO this method is for setting if user wants to delete acc
    /*.setOnClickListener(new AdapterView.OnClickListener(){
    // @Override
    // public void onClickListener(... )}
    dataBase.deleteOne(user)*/
    // METHOD FOR PATIENT LOGIN
    public boolean checkUserLogin(String mail, String pass) {
        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " +
                COLUMN_USER_PASSWORD + " = ? ", new String[] {mail,pass} );
       // cursor.close();
        if(cursor.getCount() > 0) return true;
        else return false;

    }
    // METHOD FOR CHECKING IF EMAIL EXISTS OR NOT
    public boolean checkMail(String email) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_USER_EMAIL + " = ? ", new String[] {email});
        if(cursor.getCount() > 0) return false;
        else return true;
    }


    public boolean deleteOne(AccountModel accountModel){
       db= this.getWritableDatabase();
        String queryString = "DELETE FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_ID + " = " + accountModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();

    }

    public Cursor emailExists(String email){
        db = this.getReadableDatabase();

        Cursor cursor = db.query(ACCOUNT_TABLE, new String[]{COLUMN_USER_EMAIL,COLUMN_USER_PASSWORD},COLUMN_USER_EMAIL + "=?",new String[]{email},null,null,null );
        return cursor;
    }

}
