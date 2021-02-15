package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;

import java.util.ArrayList;

import static csc1304.gr13.retailmanagercsc.database.Stock.TAG;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class Customer {
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public Customer(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }

    public boolean createNewCustomer(CustomerModel customer){
        this.Open();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(dbHelper.COL_CT_ID,customer.getId());
        contentValues.put(dbHelper.COL_CT_NAME,customer.getCustomerName());
        contentValues.put(dbHelper.COL_CT_CODE,customer.getCustomerCode());
        contentValues.put(dbHelper.COL_CT_TYPE_,customer.getCustomerType());
        contentValues.put(dbHelper.COL_CT_GENDER,customer.getCustomerGender());
        contentValues.put(dbHelper.COL_CT_PHONE,customer.getCustomerPhone());
        contentValues.put(dbHelper.COL_CT_EMAIL,customer.getCustomerEmail());
        contentValues.put(dbHelper.COL_CT_ADDRESS,customer.getCustomerAddress());
        contentValues.put(dbHelper.COL_CT_DUE_AMOUNT,customer.getCustomerDueAmount());

        long id = database.insert(dbHelper.TABLE_CT_NAME,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "CustomerId:"+id+", Customer: ------------ new Customer details inserted");
            return true;
        }else {
            Log.d(TAG, "Customer: ------------ new Customer details inserted failed");
            return false;
        }
    }

    public ArrayList<CustomerModel> getAllCustomer() {
        this.Open();
        ArrayList<CustomerModel> customers= new ArrayList<>();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_CT_NAME, null, null, null, null, null, null);

            cursor.moveToFirst();
            int totalCustomer = cursor.getCount();
            for (int i = 0; i < totalCustomer; i++){
                customers.add(new CustomerModel(
                        cursor.getInt(cursor.getColumnIndex(dbHelper.COL_CT_ID)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_NAME)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_CODE)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_TYPE_)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_GENDER)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_PHONE)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_DUE_AMOUNT))
                ));
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalCustomer > 0) {
                return customers;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }


    public CustomerModel getCustomerById(int customerid) {
        this.Open();
        CustomerModel customers= null;
        try {
            Cursor cursor = database.query(dbHelper.TABLE_CT_NAME, null, String.format("%s = ?", dbHelper.COL_CT_ID),new String[]{String.valueOf(customerid)}, null, null, null);

            cursor.moveToFirst();
            int totalCustomer = cursor.getCount();
            for (int i = 0; i < totalCustomer; i++){
                customers= new CustomerModel(
                        cursor.getInt(cursor.getColumnIndex(dbHelper.COL_CT_ID)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_NAME)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_CODE)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_TYPE_)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_GENDER)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_PHONE)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.COL_CT_DUE_AMOUNT))
                );
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalCustomer > 0) {
                return customers;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }


    public boolean updateCustomerDueAmount(String customerId,String dueAmount){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.COL_CT_DUE_AMOUNT,dueAmount);
            int value = database.update(dbHelper.TABLE_CT_NAME,contentValues,String.format("%s = ?", dbHelper.COL_CT_ID),new String[]{customerId});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }

    }

    public int getCountCustomer(){
        this.Open();
        Cursor cursor = database.query(dbHelper.TABLE_CT_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        this.Close();
        if (cursor.getCount() > 0){
            return cursor.getCount();
        }else return 0;
    }

    public boolean haveAnyData(){

        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_CT_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();

            int temp = cursor.getCount();

            cursor.close();
            this.Close();

            if (temp>0){
                return true;
            }else return false;
        }catch (Exception e){

            Log.d(TAG, "haveAnyData: "+e);
            return false;
        }

    }

}
