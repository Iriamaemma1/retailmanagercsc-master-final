package csc1304.gr13.retailmanagercsc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class DeleteAllData {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public static final String TAG = "csc1304.gr13.retailmanagercsc";

    public DeleteAllData(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


    public void deleteStockData(){
        this.Open();
        try {
            database.delete(dbHelper.TABLE_ST_NAME,null,null);
        }catch (Exception e){
            Log.d(TAG, "deleteAllData: "+e);
        }

        this.Close();

    }

    public void deleteCustomer(){
        this.Open();
        try {
            database.delete(dbHelper.TABLE_CT_NAME,null,null);

        }catch (Exception e){

            Log.d(TAG, "deleteAllData: "+e);
        }

        this.Close();
    }

    public void deleteDueInfo(){
        this.Open();
        try {
            Log.e("DueInfo","Deleting due info ....");
            database.delete(dbHelper.TABLE_DUE_INFO_NAME,null,null);

        }catch (Exception e){

            Log.d(TAG, "deleteAllDueInfoData: "+e);
        }

        this.Close();
    }
}
