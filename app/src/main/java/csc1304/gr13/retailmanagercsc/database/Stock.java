package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.StockDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockModel;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class Stock {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public static final String TAG = "csc1304.gr13.retailmanagercsc";

    public Stock(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


    public boolean storeStock(StockDatabaseModel stock){

        this.Open();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(dbHelper.COL_ST_ID,stock.getStockId_());
        contentValues.put(dbHelper.COL_ST_PRODUCT_ID,stock.getProductId());
        contentValues.put(dbHelper.COL_ST_PRODUCT_TYPE,stock.getProductType());
        contentValues.put(dbHelper.COL_ST_QUANTITY,stock.getProductQuantity());
        contentValues.put(dbHelper.COL_ST_PRODUCT_FOR,stock.getProductFor());

        long id = database.insert(dbHelper.TABLE_ST_NAME,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "stock:"+id+" ------------ new Stock inserted");
            return true;
        }else {
            Log.d(TAG, "store: ------------ new stock insertion failed");

            return false;
        }
    }

    public ArrayList<StockModel> getStocks() {

        this.Open();

        ArrayList<StockModel> stocks= new ArrayList<>();

        try {
            Cursor stockTableCursor = database.query(dbHelper.TABLE_ST_NAME, null, null, null, null, null, null);

            stockTableCursor.moveToFirst();
            int productSerial=0;

            if (stockTableCursor.getCount() > 0) {
                for (int i = 0; stockTableCursor.getCount() > i; i++) {
                    Cursor productTableCursor = database.query(dbHelper.TABLE_P_NAME, null, dbHelper.COL_P_ID+"=?", new String[] {stockTableCursor.getString(stockTableCursor.getColumnIndex(dbHelper.COL_ST_PRODUCT_ID))}, null, null, null);

                    productTableCursor.moveToFirst();
                    if (productTableCursor.getCount() > 0) {
                            stocks.add(new StockModel(
                                    productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_NAME)),
                                    productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_BRAND)),
                                    ++productSerial+"",
                                    productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_SIZE)),
                                    stockTableCursor.getString(stockTableCursor.getColumnIndex(dbHelper.COL_ST_QUANTITY))+" "+
                                            productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_UNIT)))
                            );

                    }
                    stockTableCursor.moveToNext();

                }
            }

            stockTableCursor.close();
            this.Close();
            int get = stockTableCursor.getCount();
            if (get > 0) {
                return stocks;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getStocks: "+e);
            return null;

        }

    }
    public ArrayList<StockModel> getStocksForSendData() {// TODO: 7/13/2017  don,t need to use stockModel class we just need stock limit and stock code need to update it later


        this.Open();

        ArrayList<StockModel> stocks= new ArrayList<>();

        try {
            Cursor stockTableCursor = database.query(dbHelper.TABLE_ST_NAME, null, null, null, null, null, null);

            stockTableCursor.moveToFirst();
            String productCode;
            if (stockTableCursor.getCount() > 0) {
                for (int i = 0; stockTableCursor.getCount() > i; i++) {
                    Cursor productTableCursor = database.query(dbHelper.TABLE_P_NAME, null, dbHelper.COL_P_CODE+"=?", new String[] {stockTableCursor.getString(stockTableCursor.getColumnIndex(dbHelper.COL_ST_PRODUCT_ID))}, null, null, null);
                    productCode = stockTableCursor.getString(stockTableCursor.getColumnIndex(dbHelper.COL_ST_PRODUCT_ID));
                    Log.d(TAG, "sendDataToWeb: "+productCode);
                    productTableCursor.moveToFirst();
                    if (productTableCursor.getCount() > 0) {
                            stocks.add(new StockModel(
                                    productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_NAME)),
                                    productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_BRAND)),
                                    productCode,
                                    productTableCursor.getString(productTableCursor.getColumnIndex(dbHelper.COL_P_SIZE)),
                                    stockTableCursor.getString(stockTableCursor.getColumnIndex(dbHelper.COL_ST_QUANTITY)))
                            );

                    }
                    stockTableCursor.moveToNext();

                }
            }

            stockTableCursor.close();
            this.Close();
            int get = stockTableCursor.getCount();
            if (get > 0) {
                return stocks;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getStocks: "+e);
            return null;

        }

    }



    public boolean haveAnyStock(){
        this.Open();
        try {
            Cursor stockTableCursor = database.query(dbHelper.TABLE_ST_NAME, null, null, null, null, null, null);
            stockTableCursor.moveToFirst();

            int temp = stockTableCursor.getCount();

            stockTableCursor.close();
            this.Close();

            if (temp>0){
                return true;
            }else return false;
        }catch (Exception e){

            Log.d(TAG, "haveAnyStock: "+e);
            return false;
        }

    }

    public boolean ifStockExists(String productId){
        this.Open();
        try {
            Cursor stockTableCursor = database.query(dbHelper.TABLE_ST_NAME, null, dbHelper.COL_ST_PRODUCT_ID+"=?", new String[] {productId}, null, null, null);
            stockTableCursor.moveToFirst();

            int temp = stockTableCursor.getCount();

            stockTableCursor.close();
            this.Close();

            if (temp>0){
                return true;
            }else return false;
        }catch (Exception e){

            Log.d(TAG, "IfStockExists: "+e);
            return false;
        }

    }

    public String getCurrentTock(String productId){
        this.Open();
        try {
            Cursor stockTableCursor = database.query(dbHelper.TABLE_ST_NAME, null, dbHelper.COL_ST_PRODUCT_ID+"=?", new String[] {productId}, null, null, null);
            stockTableCursor.moveToFirst();

            int temp = stockTableCursor.getCount();

            String current_stock =stockTableCursor.getString(stockTableCursor.getColumnIndex(dbHelper.COL_ST_QUANTITY));
            stockTableCursor.close();
            this.Close();

            if (temp>0){
                return current_stock;
            }else return "-1";
        }catch (Exception e){

            Log.d(TAG, "IfStockExists: "+e);
            return "-1";
        }

    }

    public boolean updateStock(String productId,String quantity){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.COL_ST_QUANTITY,quantity);
            int value = database.update(dbHelper.TABLE_ST_NAME,contentValues,String.format("%s = ?", dbHelper.COL_ST_PRODUCT_ID),new String[]{productId});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }

    }
    public boolean deleteStock(String productId){
        this.Open();
        try {
            int value = database.delete(dbHelper.TABLE_ST_NAME,String.format("%s = ?", dbHelper.COL_ST_PRODUCT_ID),new String[]{productId});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }
    }

}
