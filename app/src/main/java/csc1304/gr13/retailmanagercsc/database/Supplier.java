package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;

import java.util.ArrayList;

import static csc1304.gr13.retailmanagercsc.database.Stock.TAG;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class Supplier {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public Supplier(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


    public boolean CreateNewSupplier(SupplierModel supplier){
        this.Open();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(dbHelper.COLUMN_ID,supplier.getId());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN1,supplier.getSupplier_name());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN2,supplier.getSupplier_company_name());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN3,supplier.getSupplier_contact_person());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN4,supplier.getSupplier_phone_number());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN5,supplier.getSupplier_address());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN6,supplier.getSupplier_bank_name());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN7,supplier.getSupplier_bank_account());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN8,supplier.getSupplier_email());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN9,supplier.getSupplier_website());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN10,supplier.getSupplier_description());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN11,supplier.getCreated_by_id());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN12,supplier.getUpdated_by_id());
        contentValues.put(dbHelper.SUPPLIERS_COLUMN13,supplier.getCreated_at());

        long id = database.insert(dbHelper.SUPPLIERS_TABLE_NAME,null,contentValues);


        this.Close();
        if(id > 0){
            Log.d(TAG, "Supplier: ------------ new Supplier details inserted");
            return true;
        }else {
            Log.d(TAG, "Supplier: ------------ new Supplier details inserted failed");
            return false;
        }
    }




    public ArrayList<SupplierModel> getAllSuppliers() {
        this.Open();
        ArrayList<SupplierModel> supplier= new ArrayList<>();
        try {
            Cursor cursor = database.query(dbHelper.SUPPLIERS_TABLE_NAME, null, null, null, null, null, null);

            cursor.moveToFirst();
            int totalSupplier = cursor.getCount();
            for (int i = 0; i < totalSupplier; i++){
                supplier.add(new SupplierModel(
                                cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN1)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN2)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN3)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN4)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN5)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN6)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN7)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN8)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN9)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN10)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN11)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN12)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN13))
                ));
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalSupplier > 0) {
                return supplier;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }


    public ArrayList<SupplierModel> getSupplier(String supplierId) {
        this.Open();
        ArrayList<SupplierModel> supplier= new ArrayList<>();
        try {
            Cursor cursor = database.query(dbHelper.SUPPLIERS_TABLE_NAME, null, dbHelper.COLUMN_ID + "=?", new String[]{supplierId}, null, null, null);

            cursor.moveToFirst();
            int totalSupplier = cursor.getCount();
            for (int i = 0; i < totalSupplier; i++){
                supplier.add(new SupplierModel(
                        cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN1)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN2)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN3)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN4)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN5)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN6)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN7)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN8)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN9)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN10)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN11)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN12)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.SUPPLIERS_COLUMN13))
                ));
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalSupplier > 0) {
                return supplier;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }

    public boolean updateUpdateSuppliers(String customerCode,String dueAmount){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.COL_CT_DUE_AMOUNT,dueAmount);
            int value = database.update(dbHelper.SUPPLIERS_TABLE_NAME,contentValues,String.format("%s = ?", dbHelper.COL_CT_CODE),new String[]{customerCode});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }

    }

    public int getCountSupplier(){
        this.Open();
        Cursor cursor = database.query(dbHelper.SUPPLIERS_TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        this.Close();
        if (cursor.getCount() > 0){
            return cursor.getCount();
        }else return 0;
    }

    public boolean haveAnyData(){

        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.SUPPLIERS_TABLE_NAME, null, null, null, null, null, null);
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
