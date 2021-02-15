package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.VariationModel;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class Variation {
    public static final String TAG = "csc1304.gr13.retailmanagercsc";
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public Variation(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


    public Long CreateVariation(VariationModel productVariation){

        this.Open();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(dbHelper.VARIATION_ID,productVariation.getId());
        contentValues.put(dbHelper.VARIATION_NAME,productVariation.getVariation_name());
        contentValues.put(dbHelper.VARIATION_DESC,productVariation.getVariation_description());


        long id = database.insert(dbHelper.TABLE_VARIATION,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "Product: ------------ new Product inserted");
            return id;
        }else {
            Log.d(TAG, "Product: ------------ new Product inserted failed");
            return -1L;
        }
    }

public ArrayList<VariationModel> getAllVariations() {
        this.Open();
        ArrayList<VariationModel> productVariation= new ArrayList<>();
        try {

                    int get = 0;
                    Cursor cursor = database.query(dbHelper.TABLE_VARIATION, null, null, null, null, null, null);
                    cursor.moveToFirst();
                    int totalVariation = cursor.getCount();
                    if (cursor.getCount() > 0) {
                        for (int i = 0; i < totalVariation; i++){
                            Log.e("VariationCounter",get+"");
                            get++;

                            productVariation.add(new VariationModel(
                                    cursor.getInt(cursor.getColumnIndex(dbHelper.VARIATION_ID)),
                                    cursor.getString(cursor.getColumnIndex(dbHelper.VARIATION_NAME)),
                                    cursor.getString(cursor.getColumnIndex(dbHelper.VARIATION_DESC)) ));
                            cursor.moveToNext();
                        }

                    }
            cursor.close();

            this.Close();

            if (get > 0) {
                return productVariation;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProductVariation: "+e);
            return null;

        }
    }

    public VariationModel getVariationById(String variationId) {

        this.Open();

        VariationModel productVar = null;
        try {
            Cursor cursor = database.query(dbHelper.TABLE_VARIATION, null, null, null, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                productVar=new VariationModel(
                        cursor.getString(cursor.getColumnIndex(dbHelper.VARIATION_NAME)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.VARIATION_DESC)));
                cursor.moveToNext();

                }

            cursor.close();

            this.Close();
            int get = cursor.getCount();
            if (get > 0) {
                    return productVar;
            } else {
                    return null;
            }

        } catch (Exception e) {
            Log.d(TAG, "getStocks: " + e);
            return null;

        }

    }

    public ArrayList<VariationModel> getVariationsByPId(String productId) {

        this.Open();
        ArrayList<VariationModel> list= new ArrayList<>();

        VariationModel productVar = null;
        try {

            Cursor prod_var_cursor = database.query(dbHelper.TABLE_PRODUCT_VARIATION, null, dbHelper.PRODUCT_ID+"=?", new String[]{productId}, null, null, null);

            prod_var_cursor.moveToFirst();
            int totalVariation = prod_var_cursor.getCount();
            Log.e("ProductVariationCount",totalVariation+"");
            if (prod_var_cursor.getCount() > 0) {

                for (int i = 0; i < totalVariation; i++){

                    String variationId = prod_var_cursor.getString(prod_var_cursor.getColumnIndex(dbHelper.VAR_ID));
                    Cursor cursor = database.query(dbHelper.TABLE_VARIATION, null, dbHelper.VARIATION_ID+"=?", new String[]{variationId}, null, null, null);
                    cursor.moveToFirst();

                    if (cursor.getCount() > 0) {
                        productVar=new VariationModel(
                                cursor.getInt(cursor.getColumnIndex(dbHelper.VARIATION_ID)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.VARIATION_NAME)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.VARIATION_DESC)));

                        list.add(productVar);
                        cursor.moveToNext();

                    }

                    cursor.close();
                    prod_var_cursor.moveToNext();
                }


            }

            prod_var_cursor.close();


            this.Close();
            int get = prod_var_cursor.getCount();
            if (get > 0) {
                return list;
            } else {
                return null;
            }

        } catch (Exception e) {
            Log.d(TAG, "getStocks: " + e);
            return null;

        }

    }

    public boolean haveAnyVariation(){
        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_VARIATION, null, null, null, null, null, null);
            cursor.moveToFirst();

            int temp = cursor.getCount();

            cursor.close();
            this.Close();

            if (temp>0){
                return true;
            }else return false;
        }catch (Exception e){

            Log.d(TAG, "haveAnyVariation: "+e);
            return false;
        }

    }
}
