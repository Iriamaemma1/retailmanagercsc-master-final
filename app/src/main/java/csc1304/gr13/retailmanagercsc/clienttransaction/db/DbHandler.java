package csc1304.gr13.retailmanagercsc.clienttransaction.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class DbHandler extends SQLiteOpenHelper {
    //all constants as they are static and final(Db=Database)
    //Db Version
    private static final int Db_Version=2;
    //Db Name
    private static final String Db_Name="merchant";
    //table name
    private static final String Table_Name="terminal";
    //Creating mycontacts Columns
    private static final String Terminal_Id="terminal_id";
    private static final String Merchant_Id="merchant_id";
    private static final String Merchant_Name="merchant_Name";
    private static final String Merchant_Name_Loc="merchant_name_loc";
    private static final String Contact="contact";
    //constructor here
    public DbHandler(Context context)
    {
        super(context,Db_Name,null,Db_Version);
    }
    //creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // writing command for sqlite to create table with required columns
        String Create_Table="CREATE TABLE " + Table_Name + "(" + Terminal_Id + " TEXT PRIMARY KEY,"+Contact+" TEXT," + Merchant_Name + " TEXT,"+ Merchant_Id + " TEXT," + Merchant_Name_Loc+" TEXT"+ ")";
        db.execSQL(Create_Table);
    }
    //Upgrading the Db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        //create the table again
        onCreate(db);
    }
    //Add new Terminal by calling this method
    public void addTerminal(Terminal usr)
    {
        // getting db instance for writing the user
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(Terminal_Id,usr.getTerminalId());
        cv.put(Merchant_Id,usr.getMerchantId());
        cv.put(Merchant_Name_Loc,usr.getMerchantNameLocation());
        cv.put(Merchant_Name,usr.getMerchantName());
        cv.put(Contact,usr.getMerchantContact());
        //inserting row
        db.insert(Table_Name, null, cv);
        //close the database to avoid any leak
        db.close();
    }
    public String checkTerminal(Terminal us)
    {
        String terminal= null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT terminal_id FROM "+Table_Name,new String[]{});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            terminal=cursor.getString(0);
            cursor.close();
        }
        return terminal;
    }

    public List<Terminal>   getAllTerminalDetails(){
        List<Terminal> terminalList = new ArrayList<Terminal>();

        String selectQuery = "SELECT * FROM "+Table_Name+" ORDER BY "+Terminal_Id+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Terminal terminal = new Terminal();

                terminal.setTerminalId(cursor.getString(0));
                terminal.setMerchantContact(cursor.getString(1));
                terminal.setMerchantName(cursor.getString(2));
                terminal.setMerchantId(cursor.getString(3));
                terminal.setMerchantNameLocation(cursor.getString(4));
                terminalList.add(terminal);

            }while (cursor.moveToNext());
        }
        db.close();
        return  terminalList;
    }

    public boolean haveAnyTerminal(){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor terminalTableCursor = db.query(Table_Name, null, null, null, null, null, null);
            terminalTableCursor.moveToFirst();

            int temp = terminalTableCursor.getCount();

            terminalTableCursor.close();

            db.close();

            if (temp>0){
                return true;
            }else return false;
        }catch (Exception e){

            Log.d("TerminalHandler", "haveAnyTerminal: "+e);
            return false;
        }

    }
}