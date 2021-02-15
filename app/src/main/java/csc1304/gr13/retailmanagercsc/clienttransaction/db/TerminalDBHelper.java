package csc1304.gr13.retailmanagercsc.clienttransaction.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class TerminalDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "isw_transactions.db";
    public static final String STAN_TABLE_NAME = "tran_stan";
    public static final String STAN_COLUMN_ID = "tran_stan";
    public static final String STAN_COLUMN_STAN = "stan";
    public static final String STAN_COLUMN_STAN_DATE = "date";
    private HashMap hp;

    public TerminalDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " +STAN_TABLE_NAME+
                        "(stan integer primary key AUTOINCREMENT,stan_date DATE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS stan");
        onCreate(db);
    }

    public boolean createStan() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stan_date", System.currentTimeMillis());
        db.insert(STAN_TABLE_NAME, null, contentValues);
        return true;
    }




    public Cursor getStan() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select stan from "+STAN_TABLE_NAME+" order by stan desc LIMIT 1", null );
        return res;
    }

    public int getTransactionStan() {
        SQLiteDatabase db = this.getReadableDatabase();
        int stan = 0;
        Cursor localCursor = db.rawQuery("select stan from "+STAN_TABLE_NAME+" order by stan desc LIMIT 1",
                null);
        int i = localCursor.getColumnIndex("stan");
        if (localCursor.moveToFirst()) {
            do {
                stan = Integer.parseInt(localCursor.getString(i));
            } while (localCursor.moveToFirst());
        }
        localCursor.close();
        return stan;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STAN_TABLE_NAME);
        return numRows;
    }

    public boolean updateStan(Integer newStan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stan_date", newStan);
        db.update("tran_stan", contentValues, "id = ? ", new String[] { Integer.toString(newStan) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

}