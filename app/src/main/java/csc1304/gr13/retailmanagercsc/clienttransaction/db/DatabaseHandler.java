package csc1304.gr13.retailmanagercsc.clienttransaction.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import csc1304.gr13.retailmanagercsc.clienttransaction.model.Transactions;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "opt_trans";
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static String id = "id";
    private static final String tran_type = "tran_type";
    private static final String terminal_id = "terminal_id";
    private static final String merchant_id = "merchant_id";
    private static final String merchant_location = "merchant_location";
    private static final String amount_authorsed = "amount_authorsed";
    private static final String surcharge = "surcharge";
    private static final String device_id = "device_id";
    private static final String tran_currency = "tran_currency";
    private static final String tran_date = "tran_date";
    private static final String stan = "stan";
    private static final String rrn = "rrn";
    private static final String resp_code = "resp_code";
    private static final String resp_desc = "resp_desc";
    private static final String pan = "pan";
    private static final String fullMessageResponse = "fullMessageResponse";
    private static final String staffId = "staffId";
    private static final String structureData = "structureData";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + tran_type + " TEXT," + terminal_id + " TEXT," + merchant_id + " TEXT," + merchant_location + " TEXT," + amount_authorsed + " TEXT," + surcharge + " TEXT," + device_id + " TEXT," + tran_currency + " TEXT,"+tran_date+" TEXT,"+stan+" TEXT,"+rrn+" TEXT,"+resp_code+" TEXT,"+resp_desc+" TEXT,"+pan+" TEXT,"+fullMessageResponse+" TEXT,"+staffId+" TEXT,"+structureData+" TEXT);";

        db.execSQL(CREATE_TRANSACTIONS_TABLE);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTIONS);

        onCreate(db);
    }

   public Long addTransaction(Transactions transaction){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();

        values.put(tran_type,transaction.getTran_type());
        values.put(terminal_id,transaction.getTerminal_id());
        values.put(merchant_id,transaction.getMerchant_id());
        values.put(merchant_location,transaction.getMerchant_location());
        values.put(amount_authorsed,transaction.getAmount_authorsed());
        values.put(surcharge,transaction.getSurcharge());
        values.put(device_id,transaction.getDevice_id());
        values.put(tran_currency,transaction.getTran_currency());
        values.put(tran_date,transaction.getTran_date().toString());
        values.put(stan,transaction.getStan());
        values.put(rrn,transaction.getRrn());
        values.put(resp_code,transaction.getResp_code());
        values.put(resp_desc,transaction.getResp_desc());
        values.put(pan,transaction.getPan());
        values.put(fullMessageResponse,transaction.getFullMessageResponse());
        values.put(staffId,transaction.getStaffId());
       values.put(structureData,transaction.getStructuredData());
       Long id =  db.insert(TABLE_TRANSACTIONS,null,values);

       Log.e("Transaction","Transaction saved with Id: "+id);
        db.close();

        return id;

    }

    public Transactions getTransaction(int tran_id){

        Log.d("SearchingRecord"," WithTranId: "+tran_id);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRANSACTIONS,new String[]{id,
                 tran_type,
                terminal_id,
                merchant_id,
                merchant_location,
                amount_authorsed,
                surcharge,
                device_id,
                tran_currency,
                tran_date,
                stan,
                rrn,
                resp_code,
                resp_desc,
                pan,fullMessageResponse}, id+="=?",new String[]{String.valueOf(tran_id)},null,null,null,null);

        Transactions transaction =null;
         if(cursor != null){
             if(cursor.moveToFirst()){
                 transaction = new Transactions();

                 transaction.setId(cursor.getInt(0));
                 transaction.setTran_type(cursor.getString(1));
                 transaction.setTerminal_id(cursor.getString(2));
                 transaction.setMerchant_id(cursor.getString(3));
                 transaction.setMerchant_location(cursor.getString(4));
                 transaction.setAmount_authorsed(Long.parseLong(cursor.getString(5)));
                 transaction.setSurcharge(Long.parseLong(cursor.getString(6)));
                 transaction.setDevice_id(cursor.getString(7));
                 transaction.setTran_currency(Integer.parseInt(cursor.getString(8)));

                 transaction.setTran_date(cursor.getString(9));

                 transaction.setStan(cursor.getString(10));
                 transaction.setRrn(cursor.getString(11));
                 transaction.setResp_code(cursor.getString(12));
                 transaction.setResp_desc(cursor.getString(13));
                 transaction.setPan(cursor.getString(14));
                 transaction.setFullMessageResponse(cursor.getString(15));

             }
             Log.d("CursorResult","Id Found");
             Log.d("CursorTransactionType",transaction.getTran_type());
         }else{
             Log.d("CursorResult","Id not Found");
         }

         if(cursor != null && !cursor.isClosed()){

             cursor.close();
         }

         return  transaction;
    }

    public List<Transactions> getAllTransactions(){
        List<Transactions> transactionList = new ArrayList<Transactions>();

        String selectQuery = "SELECT * FROM "+TABLE_TRANSACTIONS+" ORDER BY "+id+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Transactions transaction = new Transactions();

                transaction.setId(cursor.getInt(0));
                transaction.setTran_type(cursor.getString(1));
                transaction.setTerminal_id(cursor.getString(2));
                transaction.setMerchant_id(cursor.getString(3));
                transaction.setMerchant_location(cursor.getString(4));
                transaction.setAmount_authorsed(Long.parseLong(cursor.getString(5)));
                transaction.setSurcharge(Long.parseLong(cursor.getString(6)));
                transaction.setDevice_id(cursor.getString(7));
                transaction.setTran_currency(Integer.parseInt(cursor.getString(8)));

                transaction.setTran_date(cursor.getString(9));

                transaction.setStan(cursor.getString(10));
                transaction.setRrn(cursor.getString(11));
                transaction.setResp_code(cursor.getString(12));
                transaction.setResp_desc(cursor.getString(13));
                transaction.setPan(cursor.getString(14));
                transaction.setFullMessageResponse(cursor.getString(15));
                transaction.setStaffId(cursor.getString(16));

                transactionList.add(transaction);

            }while (cursor.moveToNext());
        }
        db.close();
        return  transactionList;
    }

    public int updateTransact(Transactions transaction){

        Log.e("UpdatingTransaction",""+transaction.getId());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();

        values.put(tran_type,transaction.getTran_type());
        values.put(terminal_id,transaction.getTerminal_id());
        values.put(merchant_id,transaction.getMerchant_id());
        values.put(merchant_location,transaction.getMerchant_location());
        values.put(amount_authorsed,transaction.getAmount_authorsed());
        values.put(surcharge,transaction.getSurcharge());
        values.put(device_id,transaction.getDevice_id());
        values.put(tran_currency,transaction.getTran_currency());
        values.put(tran_date,transaction.getTran_date().toString());
        values.put(stan,transaction.getStan());
        values.put(rrn,transaction.getRrn());
        values.put(resp_code,transaction.getResp_code());
        values.put(resp_desc,transaction.getResp_desc());
        values.put(pan,transaction.getPan());
        values.put(fullMessageResponse,transaction.getFullMessageResponse());
        values.put(staffId,transaction.getStaffId());
        values.put(structureData,transaction.getStructuredData());
        return db.update(TABLE_TRANSACTIONS,values,id+"=?", new String[]{String.valueOf(transaction.getId())});
    }

    public void deleteTransaction(Transactions transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTIONS,id+"=?",new String[]{String.valueOf(transaction.getId())});
        db.close();
    }


    public List<Transactions> getAllTransactionsByStaffId(String staff_Id){
        List<Transactions> transactionList = new ArrayList<Transactions>();

        String selectQuery = "SELECT * FROM "+TABLE_TRANSACTIONS+" WHERE "+staffId+"='"+staff_Id+"' ORDER BY "+id+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Transactions transaction = new Transactions();

                transaction.setId(cursor.getInt(0));
                transaction.setTran_type(cursor.getString(1));
                transaction.setTerminal_id(cursor.getString(2));
                transaction.setMerchant_id(cursor.getString(3));
                transaction.setMerchant_location(cursor.getString(4));
                transaction.setAmount_authorsed(Long.parseLong(cursor.getString(5)));
                transaction.setSurcharge(Long.parseLong(cursor.getString(6)));
                transaction.setDevice_id(cursor.getString(7));
                transaction.setTran_currency(Integer.parseInt(cursor.getString(8)));

                transaction.setTran_date(cursor.getString(9));

                transaction.setStan(cursor.getString(10));
                transaction.setRrn(cursor.getString(11));
                transaction.setResp_code(cursor.getString(12));
                transaction.setResp_desc(cursor.getString(13));
                transaction.setPan(cursor.getString(14));
                transaction.setFullMessageResponse(cursor.getString(15));
                transaction.setStaffId(cursor.getString(16));
                transaction.setStructuredData(cursor.getString(17));
                transactionList.add(transaction);

            }while (cursor.moveToNext());
        }
        db.close();
        return  transactionList;
    }

    public int getTransactionsCount(){

        String countQuery = "SELECT * FROM "+TABLE_TRANSACTIONS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery,null);

        cursor.close();

        return  cursor.getCount();
    }

    public void dropTable(){
        Log.e("DropTables","Dropping transaction tables");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop table if EXISTS "+TABLE_TRANSACTIONS);

        onCreate(db);
    }

}
