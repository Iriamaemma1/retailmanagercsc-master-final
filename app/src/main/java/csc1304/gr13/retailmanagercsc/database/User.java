package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.UserDatabaseModel;
import csc1304.gr13.retailmanagercsc.staffId.CurrentLogginSession;

import static csc1304.gr13.retailmanagercsc.database.Stock.TAG;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class User {

    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public User(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }

    public boolean createNewUser(UserDatabaseModel user){

        this.Open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COL_U_NAME,user.getUserName());
        contentValues.put(dbHelper.COL_U_EMAIL,user.getUserEmail());
        contentValues.put(dbHelper.COL_U_PASSWORD,user.getUserPassword());
        contentValues.put(dbHelper.COL_U_PHONE,user.getUserPhone());
        contentValues.put(dbHelper.COL_U_EMPLOYEE_ID,user.getUserEmployeeId());

        long id = database.insert(dbHelper.TABLE_USER_NAME,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "User: ------------ new User details inserted");
            return true;
        }else {
            Log.d(TAG, "User: ------------ new User details inserted failed");
            return false;
        }
    }


//    private String userName;
//    private String userEmail;
//    private String userPassword;
//    private String userPhone;
//    private String userEmployeeId;

    public UserDatabaseModel getUserDetails(){
        UserDatabaseModel user;
        this.Open();


        Log.e("GetUser","Getting userId: "+ CurrentLogginSession.getStaffPrimaryId());

        try {
            Cursor cursor = database.query(dbHelper.TABLE_USER_NAME,null,dbHelper.COL_U_EMPLOYEE_ID+"=?", new String[] {CurrentLogginSession.getStaffPrimaryId()},null,null,null);
            cursor.moveToFirst();

            user = new UserDatabaseModel(cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_NAME)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_PHONE)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_EMPLOYEE_ID))
            );
            int temp = cursor.getCount();
            cursor.close();
            this.Close();


            if (temp > 0) return user;
            else return null;
        }catch (Exception e){
            return null;
        }
    }

    public Boolean getUserDetailsById(String userId){
        UserDatabaseModel user;
        this.Open();

        try {
            Cursor cursor = database.query(dbHelper.TABLE_USER_NAME,null,dbHelper.COL_U_EMPLOYEE_ID+"=?", new String[] {userId},null,null,null);
            cursor.moveToFirst();

            int temp = cursor.getCount();
            cursor.close();
            this.Close();


            if (temp > 0) return true;
            else return false;
        }catch (Exception e){
            return null;
        }
    }


    public boolean haveAnyUser(){
        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_USER_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();

            int temp = cursor.getCount();

            cursor.close();
            this.Close();

            if (temp>0){
                return true;
            }else return false;
        }catch (Exception e){

            Log.d(TAG, "haveAnyUser: "+e);
            return false;
        }

    }

    public boolean deleteUser(String userId){
        this.Open();
        Log.e("User","Deleting user....");
        try {
            int value = database.delete(dbHelper.TABLE_USER_NAME,String.format("%s = ?", dbHelper.COL_U_EMPLOYEE_ID),new String[]{userId});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }
    }

    public UserDatabaseModel AuthenticateUser(String username,String password){
        UserDatabaseModel user;
        this.Open();


        Log.e("GetUser","Getting userId: "+ CurrentLogginSession.getStaffPrimaryId());

        try {
            Cursor cursor = database.query(dbHelper.TABLE_USER_NAME,null,dbHelper.COL_U_NAME+"=? AND "+dbHelper.COL_U_PASSWORD+"=?", new String[] {username,password},null,null,null);
            cursor.moveToFirst();

            user = new UserDatabaseModel(cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_NAME)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_PHONE)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_U_EMPLOYEE_ID))
            );
            int temp = cursor.getCount();
            cursor.close();
            this.Close();


            if (temp > 0) return user;
            else return null;
        }catch (Exception e){
            return null;
        }
    }



}
