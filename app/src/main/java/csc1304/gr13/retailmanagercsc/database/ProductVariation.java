package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationModel;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class ProductVariation {
    public static final String TAG = "csc1304.gr13.retailmanagercsc";
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public ProductVariation(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


    public Long storeProductVariationInfo(ProductVariationModel productVariation){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.VAR_ID,productVariation.getVariation_id());
        contentValues.put(dbHelper.PRODUCT_ID,productVariation.getProduct_id());
        contentValues.put(dbHelper.P_V_Stock,productVariation.getP_v_Stock());


        long id = database.insert(dbHelper.TABLE_PRODUCT_VARIATION,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "Product: ------------ new Product Variation inserted");
            return id;
        }else {
            Log.d(TAG, "Product: ------------ new Product Variation insert failed");
            return -1L;
        }
    }

public ArrayList<ProductVariationModel> getProductVariations() {
        this.Open();
        ArrayList<ProductVariationModel> productVariation= new ArrayList<>();
        try {

                    int get = 0;
                    Cursor cursor = database.query(dbHelper.TABLE_PRODUCT_VARIATION, null, null, null, null, null, null);
                    cursor.moveToFirst();

                    if (cursor.getCount() > 0) {
                        get++;
                        productVariation.add(new ProductVariationModel(
                                cursor.getString(cursor.getColumnIndex(dbHelper.VAR_ID)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PRODUCT_ID)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.P_V_Stock))));
                        cursor.moveToNext();

                    }

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

    public ProductVariationModel getProductVarDetails(String variationId,String productId) {

        this.Open();

        ProductVariationModel productVar = null;
        try {
            Cursor cursor = database.query(dbHelper.TABLE_PRODUCT_VARIATION, null, dbHelper.VAR_ID+"=? AND "+dbHelper.PRODUCT_ID+"=?", new String[]{variationId,productId}, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                productVar=new ProductVariationModel(
                        cursor.getString(cursor.getColumnIndex(dbHelper.VAR_ID)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(dbHelper.P_V_Stock)));
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

    public boolean updatePvStock(String variationId,String productId,String quantity){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.P_V_Stock,quantity);
            int value = database.update(dbHelper.TABLE_PRODUCT_VARIATION,contentValues,String.format("%s = ? AND %s = ?", dbHelper.VAR_ID,dbHelper.PRODUCT_ID),new String[]{variationId,productId});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            Log.e("UpdateStockError",e.getMessage());
            return false;
        }

    }

    public boolean haveAnyVariation(){
        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_PRODUCT_VARIATION, null, null, null, null, null, null);
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
