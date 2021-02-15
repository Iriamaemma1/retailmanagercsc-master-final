package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.PurchaseModel;

import java.util.ArrayList;

import static csc1304.gr13.retailmanagercsc.database.Stock.TAG;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class Purchase {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public Purchase(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


    public Long CreateNewPurchase(PurchaseModel purchase){
        this.Open();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(dbHelper.COLUMN_ID,purchase.getId());
        contentValues.put(dbHelper.PURCHASES_COLUMN2,purchase.getProduct_id());
        contentValues.put(dbHelper.PURCHASES_COLUMN3,purchase.getProduct_variation_id());
        contentValues.put(dbHelper.PURCHASES_COLUMN4,purchase.getSupplier_id());
        contentValues.put(dbHelper.PURCHASES_COLUMN5,purchase.getPurchase_date());
        contentValues.put(dbHelper.PURCHASES_COLUMN6,purchase.getPurchase_product_quantity());
        contentValues.put(dbHelper.PURCHASES_COLUMN7,purchase.getPurchase_product_price());
        contentValues.put(dbHelper.PURCHASES_COLUMN8,purchase.getPurchase_amount());
        contentValues.put(dbHelper.PURCHASES_COLUMN9,purchase.getPurchase_payment());
        contentValues.put(dbHelper.PURCHASES_COLUMN10,purchase.getPurchase_balance());
        contentValues.put(dbHelper.PURCHASES_COLUMN11,purchase.getPurchase_description());
        contentValues.put(dbHelper.PURCHASES_COLUMN12,purchase.getCreated_by_id());
        contentValues.put(dbHelper.PURCHASES_COLUMN13,purchase.getUpdated_by_id());
        contentValues.put(dbHelper.PURCHASES_COLUMN14,purchase.getCreated_at());


        long id = database.insert(dbHelper.PURCHASES_TABLE_NAME,null,contentValues);


        this.Close();

        return id;
    }




    public ArrayList<PurchaseModel> getAllPurchases() {
        this.Open();
        ArrayList<PurchaseModel> purchase= new ArrayList<>();
        try {
            Cursor cursor = database.query(dbHelper.PURCHASES_TABLE_NAME, null, null, null, null, null, dbHelper.COLUMN_ID+" DESC");

            cursor.moveToFirst();
            int totalPurchase= cursor.getCount();
            for (int i = 0; i < totalPurchase; i++){
                purchase.add(new PurchaseModel(
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN2)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN3)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN4)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN5)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN6)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN7)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN8)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN9)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN10)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN11)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN12)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN13)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN14))
                ));
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalPurchase > 0) {
                return purchase;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }

    public ArrayList<PurchaseModel> getAllPendingPurchasePayments() {
        this.Open();
        ArrayList<PurchaseModel> purchase= new ArrayList<>();
        try {
            Cursor cursor = database.query(dbHelper.PURCHASES_TABLE_NAME, null, null, null, null, null, dbHelper.COLUMN_ID+" DESC");

            cursor.moveToFirst();
            int totalPurchase= cursor.getCount();
            for (int i = 0; i < totalPurchase; i++){
                int purchaseBalance = Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN10)));
                if(purchaseBalance>0){
                    purchase.add(new PurchaseModel(
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN2)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN3)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN4)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN5)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN6)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN7)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN8)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN9)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN10)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN11)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN12)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN13)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.PURCHASES_COLUMN14))
                    ));
                }
                cursor.moveToNext();
            }

            cursor.close();
            this.Close();
            if (totalPurchase > 0) {
                return purchase;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "getProducts: "+e);
            return null;

        }
    }


    public boolean updateUpdatePurchases(String customerCode,String dueAmount){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.COL_CT_DUE_AMOUNT,dueAmount);
            int value = database.update(dbHelper.PURCHASES_TABLE_NAME,contentValues,String.format("%s = ?", dbHelper.COL_CT_CODE),new String[]{customerCode});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }

    }

    public int getCountPurchases(){
        this.Open();
        Cursor cursor = database.query(dbHelper.PURCHASES_TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        this.Close();
        if (cursor.getCount() > 0){
            return cursor.getCount();
        }else return 0;
    }

    public boolean haveAnyData(){

        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.PURCHASES_TABLE_NAME, null, null, null, null, null, null);
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
