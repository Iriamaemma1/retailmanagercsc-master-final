package csc1304.gr13.retailmanagercsc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import csc1304.gr13.retailmanagercsc.modelClass.InvoiceLvModel;
import csc1304.gr13.retailmanagercsc.modelClass.SellsDatabaseModel;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class SellsInfo {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public static final String TAG = "csc1304.gr13.retailmanagercsc";

    public SellsInfo(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){
        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }


//
//    public static final String COL_SELL_SELLS_CODE = "sells_code";
//    public static final String COL_SELL_CUSTOMER_ID = "customer_id";
//    public static final String COL_SELL_AMOUNT = "total_amount";
//    public static final String COL_SELL_DISCOUNT = "discount";
//    public static final String COL_SELL_PAY_AMOUNT = "pay_amount";
//    public static final String COL_SELL_PAYMENT_TYPE = "payment_type";
//    public static final String COL_SELL_SELL_DATE = "sell_date";
//    public static final String COL_SELL_PAYMENT_STATUS = "payment_status";
//    public static final String COL_SELL_SELL_BY = "sell_by";


    public boolean storeSellDetails(SellsDatabaseModel sell){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COL_SELL_SELLS_CODE,sell.getSellsCode());
        contentValues.put(dbHelper.COL_SELL_CUSTOMER_ID,sell.getCustomerId());
        contentValues.put(dbHelper.COL_SELL_AMOUNT,sell.getTotalAmount());
        contentValues.put(dbHelper.COL_SELL_DISCOUNT,sell.getDiscount());
        contentValues.put(dbHelper.COL_SELL_PAY_AMOUNT,sell.getPayAmount());
        contentValues.put(dbHelper.COL_SELL_PAYMENT_TYPE,sell.getPaymentType());
        contentValues.put(dbHelper.COL_SELL_SELL_DATE,sell.getSellDate());
        contentValues.put(dbHelper.COL_SELL_PAYMENT_STATUS,sell.getPaymentStatus());
        contentValues.put(dbHelper.COL_SELL_SELL_BY,sell.getSellBy());

        long id = database.insert(dbHelper.TABLE_SELL_NAME,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "Sell: ------------ new Sell info inserted");
            return true;
        }else {
            Log.d(TAG, "Sell: ------------ new Sell insertion failed");

            return false;
        }
    }


    public long storeSellDetails2(SellsDatabaseModel sell){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COL_SELL_SELLS_CODE,sell.getSellsCode());
        contentValues.put(dbHelper.COL_SELL_CUSTOMER_ID,sell.getCustomerId());
        contentValues.put(dbHelper.COL_SELL_AMOUNT,sell.getTotalAmount());
        contentValues.put(dbHelper.COL_SELL_DISCOUNT,sell.getDiscount());
        contentValues.put(dbHelper.COL_SELL_PAY_AMOUNT,sell.getPayAmount());
        contentValues.put(dbHelper.COL_SELL_PAYMENT_TYPE,sell.getPaymentType());
        contentValues.put(dbHelper.COL_SELL_SELL_DATE,sell.getSellDate());
        contentValues.put(dbHelper.COL_SELL_PAYMENT_STATUS,sell.getPaymentStatus());
        contentValues.put(dbHelper.COL_SELL_SELL_BY,sell.getSellBy());

        long id = database.insert(dbHelper.TABLE_SELL_NAME,null,contentValues);

        this.Close();
        if(id > 0){
            Log.d(TAG, "Sell: ------------ new Sell info inserted");
            return id;
        }else {
            Log.d(TAG, "Sell: ------------ new Sell insertion failed");

            return 0;
        }
    }

    public int getLastSellItemCode(){

        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_SELL_NAME, null, null, null, null, null, null);
            cursor.moveToLast();
            int temp = cursor.getCount();
            String data[] = cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_SELLS_CODE)).split("in-");

            cursor.close();
            this.Close();

            if (temp > 0) {
                return Integer.parseInt(data[1]);
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;

        }
    }

    public ArrayList<InvoiceLvModel> getSoldProductInfo(String invoiceNo){
        ArrayList<InvoiceLvModel> soldProducts = new ArrayList<>();


         String invoiceSerial = "23011";
         String invoiceProduct;
         String invoiceProductAmount;
         String invoiceId;
         String invoiceProductPrice;
         String invoiceProductSellPrice;
         String invoiceProfit = "0";
         String invoiceDate = null;
         String invoiceTotalProfit = "0";
         String invoiceCustomer;

        this.Open();
        try {
            Log.e("DatabaseQuery","Fetching data...(1)");
            Cursor cursor = database.query(dbHelper.TABLE_SELL_NAME, null, dbHelper.COL_SELL_SELLS_CODE+"=?", new String[]{invoiceNo}, null, null, null);
            int temp = cursor.getCount();

            cursor.moveToFirst();

            if (temp > 0){
                Log.e("DatabaseQuery","Fetching data...(2)");
                Log.e("CustomerId",cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_CUSTOMER_ID)));
                Cursor cursorCustomer = database.query(dbHelper.TABLE_CT_NAME, null, dbHelper.COL_CT_CODE+"=?", new String []{cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_CUSTOMER_ID))}, null, null, null);
                cursorCustomer.moveToFirst();

                invoiceCustomer = cursorCustomer.getString(cursorCustomer.getColumnIndex(dbHelper.COL_CT_NAME));

                Cursor soldProductCursor = database.query(dbHelper.TABLE_SOLD_PRODUCT_NAME,null,dbHelper.COL_SOLD_PRODUCT_SELL_ID+"=?",new String[]{invoiceNo},null,null,null);
                soldProductCursor.moveToFirst();

                int totalSoldProduct = soldProductCursor.getCount();
                Log.e("TotalProduct",totalSoldProduct+"");

                invoiceProductAmount = soldProductCursor.getString(soldProductCursor.getColumnIndex(dbHelper.COL_SOLD_PRODUCT_TOTAL_PRICE));


                Cursor productInfoCursor = database.query(dbHelper.TABLE_P_NAME,null,dbHelper.COL_P_CODE+"=?",new String []{soldProductCursor.getString(soldProductCursor.getColumnIndex(dbHelper.COL_SOLD_PRODUCT_PRODUCT_ID))},null,null,null);
                productInfoCursor.moveToFirst();
                Log.d(TAG, "Product Info: "+productInfoCursor.getString(productInfoCursor.getColumnIndex(dbHelper.COL_P_NAME)));

                invoiceProduct = productInfoCursor.getString(productInfoCursor.getColumnIndex(dbHelper.COL_P_NAME));
                invoiceProductPrice = productInfoCursor.getString(productInfoCursor.getColumnIndex(dbHelper.COL_P_PRICE));
                invoiceProductSellPrice = productInfoCursor.getString(productInfoCursor.getColumnIndex(dbHelper.COL_P_SELL_PRICE));
                invoiceId = invoiceNo;

              soldProducts.add(new InvoiceLvModel(

                      invoiceSerial, invoiceProduct, invoiceProductAmount, invoiceId, invoiceProductPrice, invoiceProductSellPrice, invoiceProfit, invoiceDate, invoiceTotalProfit, invoiceCustomer));

            }



            cursor.close();
            this.Close();

            if (temp > 0) {
                return soldProducts;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;

        }
    }


//        this.sellsCode = sellsCode;
//        this.customerId = customerId;
//        this.totalAmount = totalAmount;
//        this.discount = discount;
//        this.payAmount = payAmount;
//        this.paymentType = paymentType;
//        this.sellDate = sellDate;
//        this.paymentStatus = paymentStatus;
//        this.sellBy = sellBy;
//
    public ArrayList<SellsDatabaseModel> getAllSellInfo(){

        this.Open();
        ArrayList<SellsDatabaseModel> allSellInfo = new ArrayList<>();
        Cursor cursor = database.query(dbHelper.TABLE_SELL_NAME, null, null,null, null, null, null);
        int temp = cursor.getCount();

        cursor.moveToFirst();

        for (int i = 0; i<temp; i++){

            SellsDatabaseModel sellsDatabase;
            sellsDatabase = new SellsDatabaseModel(cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_SELLS_CODE)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_CUSTOMER_ID)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_DISCOUNT)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_PAY_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_PAYMENT_TYPE)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_SELL_DATE)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_PAYMENT_STATUS)),
                    cursor.getString(cursor.getColumnIndex(dbHelper.COL_SELL_SELL_BY))
                    );
            allSellInfo.add(sellsDatabase);
            cursor.moveToNext();
        }

        this.Close();
        if (temp > 0) return allSellInfo;
        return null;
    }

    public boolean updateSellDetails(String sellCode,String newPaidAmount,String paymentType,String PaymentStatus){
        this.Open();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.COL_SELL_PAY_AMOUNT,newPaidAmount);
            contentValues.put(dbHelper.COL_SELL_PAYMENT_TYPE,paymentType);
            contentValues.put(dbHelper.COL_SELL_PAYMENT_STATUS,paymentType);


            int value = database.update(dbHelper.TABLE_SELL_NAME,contentValues,String.format("%s = ?", dbHelper.COL_SELL_SELLS_CODE),new String[]{sellCode});
            this.Close();
            if (value > 0) return true;
            else return false;
        }catch (Exception e){
            return false;
        }

    }

    public boolean haveAnyData(){

        this.Open();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_SELL_NAME, null, null, null, null, null, null);
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
