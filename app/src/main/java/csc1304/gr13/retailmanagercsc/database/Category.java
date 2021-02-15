package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;

import java.util.ArrayList;

import static csc1304.gr13.retailmanagercsc.database.Stock.TAG;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class Category {
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public Category(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }

    public boolean createNewCategory(CategoryModel category){
        this.Open();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(dbHelper.CAT_P_ID,category.getId());
        contentValues.put(dbHelper.CAT_P_NAME,category.getCategory_name());
        contentValues.put(dbHelper.CAT_P_DESC,category.getCategory_description());
        long id = database.insert(dbHelper.TABLE_CATEGORY_NAME,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "Category: ------------ new Category details inserted");
            return true;
        }else {
            Log.d(TAG, "Category: ------------ new Category details inserted failed");
            return false;
        }
    }

    public ArrayList<CategoryModel> getAllCategories() {
        this.Open();
        ArrayList<CategoryModel> category= new ArrayList<>();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_CATEGORY_NAME, null, null, null, null, null, null);

            cursor.moveToFirst();
            int totalCustomer = cursor.getCount();
            for (int i = 0; i < totalCustomer; i++){
                category.add(new CategoryModel(
                        cursor.getInt(cursor.getColumnIndex(dbHelper.CAT_P_ID)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.CAT_P_NAME)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.CAT_P_DESC))
                ));
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalCustomer > 0) {
                return category;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }

    public boolean updateCustomerDueAmount(String customerCode,String dueAmount){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.COL_CT_DUE_AMOUNT,dueAmount);
            int value = database.update(dbHelper.TABLE_CATEGORY_NAME,contentValues,String.format("%s = ?", dbHelper.COL_CT_CODE),new String[]{customerCode});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }

    }

    public int getCountCategory(){
        this.Open();
        Cursor cursor = database.query(dbHelper.TABLE_CATEGORY_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        this.Close();
        if (cursor.getCount() > 0){
            return cursor.getCount();
        }else return 0;
    }

    public boolean haveAnyData(){

        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_CATEGORY_NAME, null, null, null, null, null, null);
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
