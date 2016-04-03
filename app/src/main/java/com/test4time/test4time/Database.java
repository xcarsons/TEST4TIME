package com.test4time.test4time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;

/**
 * @Author - Carson Schaefer
 * API for SQLite database on Android device
 */
public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Test4Time.db";
    private static final String USERS_TABLE = "USERS";
    private static final String BLOCKAPPS_TABLE = "BLOCKAPPS";
    private static Context context;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, 1, errorHandler);
        this.context = context;
    }

    /*
     * Only called the first time the database is created.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("ONCREATE", "HERE");
        // open the sql file
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("SQLscripts/createUsers.sql")));
            String sql = "";
            while (reader.ready()) {
                String ln = reader.readLine();
                sql += ln.contains("/*") ? "" : ln; // don't read the comment lines
            }
            sqLiteDatabase.execSQL(sql);
            sql = "";
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("SQLscripts/blockApps.sql")));
            while (reader.ready()) {
                String ln = reader.readLine();
                sql += ln.contains("/*") ? "" : ln; // don't read the comment lines
            }
            sqLiteDatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("ONCREATEFAIL", e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BLOCKAPPS_TABLE);
        onCreate(sqLiteDatabase);
    }

    /*
     * Insert a user into the database (USERS TABLE).
     */
    public boolean insertUser(String name, int type, int pin, String grade, int time, int timeUp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("TYPE", type);
        contentValues.put("PIN", pin);
        contentValues.put("GRADE", grade);
        contentValues.put("TIME", time);
        contentValues.put("TIMEUP", timeUp);
        long result = -1;
        try {
            result = db.insertOrThrow(USERS_TABLE, null, contentValues); // returns -1 if data is not inserted
        }
        catch (Exception e) {
            Log.e("INSERTFAIL", e.toString());
        }
        return result == -1 ? false : true;
    }

    /*
     * Inert an application into the BLOCKAPPS table
     */
    public boolean insertApp(String name, String pack, String process) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("PACKAGE", pack);
        contentValues.put("PROCESS", process);

        long result = -1;
        try {
            result = db.insertOrThrow(BLOCKAPPS_TABLE, null, contentValues); // returns -1 if data is not inserted
        }
        catch (Exception e) {
            Log.e("INSERTFAIL", e.toString());
        }
        return result == -1 ? false : true;
    }

    /*
     * Update a users information. The User is selected based on the Name.
     */
    public boolean updateUser(String name, int type, int pin, String grade, int time, int timeUp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("TYPE", type);
        contentValues.put("PIN", pin);
        contentValues.put("GRADE", grade);
        contentValues.put("TIME", time);
        contentValues.put("TIMEUP", timeUp);
        try {
            int result = db.update(USERS_TABLE,contentValues, "NAME = " + "'" + name + "'", null);
            return result == 0 ? false : true; // result is number of rows updated
        } catch (Exception e) {
            Log.e("UPDATEUSER",e.toString());
            return false;
        }
    }

    /*
     * Returns all columns of a User
     * @name - name of the user to return
     * Cursor info:
     * - data.getString(0) returns ID
     * - data.getString(1) returns NAME
     * - data.getString(2) returns TYPE
     * - data.getString(3) returns PIN
     * - data.getString(4) returns GRADE
     * - data.getString(5) returns TIME
     * - data.getString(6) returns TIMEUP
     */
    public Cursor getUserData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
        Cursor result = null;
        // open the sql file
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("SQLscripts/getUser.sql")));
            while (reader.ready()) {
                String ln = reader.readLine();
                sql += ln.contains("/*") ? "" : ln; // don't read the comment lines
            }
            result = db.rawQuery(sql + "'" + name + "'", null);
        } catch (Exception e) {
            Log.e("getUserData", e.toString());
        }
        return result;
    }

    /*
     * Returns all columns of a specified application, queries the application name
     * * * Cursor info:
     * - data.getString(0) returns ID
     * - data.getString(1) returns NAME
     * - data.getString(2) returns PackageName
     * - data.getString(3) returns ProcessName
     */
    public Cursor getBlockAppData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
        Cursor result = null;
        // open sql file
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("SQLscripts/getApp.sql")));
            while (reader.ready()) {
                String ln = reader.readLine();
                sql += ln.contains("/*") ? "" : ln; // don't read comment lines
            }
            result = db.rawQuery(sql + "'" + name + "'", null);
        } catch (Exception e) {
            Log.e("getBlockAppData", e.toString());
        }
        return result;
    }

    /*
     * return all users
     * * Cursor info:
     * - data.getString(0) returns ID
     * - data.getString(1) returns NAME
     * - data.getString(2) returns TYPE
     * - data.getString(3) returns PIN
     * - data.getString(4) returns GRADE
     * - data.getString(5) returns TIME
     * - data.getString(6) returns TIMEUP
     */
    public Cursor getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+USERS_TABLE;
        Cursor result = null;
        result = db.rawQuery(sql, null);
        return result;
    }

    /*
     * Returns all apps that are listed as blocked
     * * Cursor info:
     * - data.getString(0) returns ID
     * - data.getString(1) returns NAME
     * - data.getString(2) returns PackageName
     * - data.getString(3) returns ProcessName
     */
    public Cursor getBlockedApps() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BLOCKAPPS_TABLE;
        Cursor result = null;
        result = db.rawQuery(sql,null);
        return result;
    }


    /*
     * delete a user in the Users table with the given name.
     * Note that parent user cannot be deleted.
     * @param name - name of the User to be deleted.
     * @return - if delete was successful or not
     */
    public boolean deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        boolean ret = db.delete(USERS_TABLE,"NAME = " + "'" + name + "'", null) > 0;
        db.setTransactionSuccessful();
        db.endTransaction();
        return ret;
    }

    /*
     * Remove an application from the BLOCKAPPS table with the given name.
     * @param name - name of the application
     */
    public boolean deleteApp(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        boolean ret = db.delete(BLOCKAPPS_TABLE,"NAME = "+ "'" + name + "'", null)>0;
        db.setTransactionSuccessful();
        db.endTransaction();
        return ret;
    }

    /*
     * Delete all rows (applications) in BLOCKAPPS table
     */
    public void deleteBlockAppsRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + BLOCKAPPS_TABLE);
    }
}
