package csc1304.gr13.retailmanagercsc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "csc1304.gr13.retailmanagercsc";
    public static final int DB_VERSION = 1;



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public final static String COLUMN_ID = "id";
    //=======================| SuppliersModel |=======================
    public final static String SUPPLIERS_TABLE_NAME = "suppliers_table";
    public final static String SUPPLIERS_COLUMN1 = "supplier_name";
    public final static String SUPPLIERS_COLUMN2 = "supplier_company_name";
    public final static String SUPPLIERS_COLUMN3 = "supplier_contact_person";
    public final static String SUPPLIERS_COLUMN4 = "supplier_phone_number";
    public final static String SUPPLIERS_COLUMN5 = "supplier_address";
    public final static String SUPPLIERS_COLUMN6 = "supplier_bank_name";
    public final static String SUPPLIERS_COLUMN7 = "supplier_bank_account";
    public final static String SUPPLIERS_COLUMN8 = "supplier_email";
    public final static String SUPPLIERS_COLUMN9 = "supplier_website";
    public final static String SUPPLIERS_COLUMN10 = "supplier_description";
    public final static String SUPPLIERS_COLUMN11 = "created_by_id";
    public final static String SUPPLIERS_COLUMN12 = "updated_by_id";
    public final static String SUPPLIERS_COLUMN13 = "created_at";

    protected final static String CREATE_SUPPLIERS_TABLE = "CREATE TABLE " + SUPPLIERS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUPPLIERS_COLUMN1 + " TEXT, " + SUPPLIERS_COLUMN2 + " TEXT, " + SUPPLIERS_COLUMN3 + " TEXT, " + SUPPLIERS_COLUMN4 + " TEXT, " + SUPPLIERS_COLUMN5 + " TEXT, " + SUPPLIERS_COLUMN6 + " TEXT, " + SUPPLIERS_COLUMN7 + " TEXT, " + SUPPLIERS_COLUMN8 + " TEXT, " + SUPPLIERS_COLUMN9 + " TEXT, " + SUPPLIERS_COLUMN10 + " TEXT, " + SUPPLIERS_COLUMN11 + " TEXT, " + SUPPLIERS_COLUMN12 + " TEXT, " + SUPPLIERS_COLUMN13 + " TEXT ) ";
    protected final static String DROP_SUPPLIERS_TABLE = "DROP TABLE IF EXISTS " + SUPPLIERS_TABLE_NAME + " ";
    public final static String SELECT_SUPPLIERS_TABLE = "SELECT * FROM " + SUPPLIERS_TABLE_NAME;


    //Purchase database
    //=======================| PurchasesModel |=======================
    public final static String PURCHASES_TABLE_NAME = "purchases_table";
    public final static String PURCHASES_COLUMN2 = "product_id";
    public final static String PURCHASES_COLUMN3 = "product_variation_id";
    public final static String PURCHASES_COLUMN4 = "supplier_id";
    public final static String PURCHASES_COLUMN5 = "purchase_date";
    public final static String PURCHASES_COLUMN6 = "purchase_product_quantity";
    public final static String PURCHASES_COLUMN7 = "purchase_product_price";
    public final static String PURCHASES_COLUMN8 = "purchase_amount";
    public final static String PURCHASES_COLUMN9 = "purchase_payment";
    public final static String PURCHASES_COLUMN10 = "purchase_balance";
    public final static String PURCHASES_COLUMN11 = "purchase_description";
    public final static String PURCHASES_COLUMN12 = "created_by_id";
    public final static String PURCHASES_COLUMN13 = "updated_by_id";
    public final static String PURCHASES_COLUMN14 = "created_at";

    protected final static String CREATE_PURCHASES_TABLE = "CREATE TABLE " + PURCHASES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  + PURCHASES_COLUMN2 + " TEXT, "+ PURCHASES_COLUMN3 + " TEXT, "+ PURCHASES_COLUMN4 + " TEXT, " + PURCHASES_COLUMN5 + "  TEXT, " + PURCHASES_COLUMN6 + " INTEGER, " + PURCHASES_COLUMN7 + " REAL, " + PURCHASES_COLUMN8 + " REAL, " + PURCHASES_COLUMN9 + " REAL, " + PURCHASES_COLUMN10 + " REAL, " + PURCHASES_COLUMN11 + " TEXT, " + PURCHASES_COLUMN12 + " TEXT, " + PURCHASES_COLUMN13 + " TEXT, " + PURCHASES_COLUMN14 + " TEXT ) ";
    protected final static String DROP_PURCHASES_TABLE = "DROP TABLE IF EXISTS " + PURCHASES_TABLE_NAME + " ";
    public final static String SELECT_PURCHASES_TABLE = "SELECT * FROM " + PURCHASES_TABLE_NAME;


    //stock database required variable name
    public static final String TABLE_ST_NAME = "stock";
    public static final String COL_ST_ID = "Id";
    public static final String COL_ST_PRODUCT_ID = "product_id";
    public static final String COL_ST_PRODUCT_TYPE = "product_type";
    public static final String COL_ST_QUANTITY = "quantity";
    public static final String COL_ST_PRODUCT_FOR = "product_for";

    public static final String STOCK_TABLE_QUERY = "Create Table "+TABLE_ST_NAME+" ( "
            +COL_ST_ID+" Integer primary key AUTOINCREMENT, "+COL_ST_PRODUCT_ID+" Text, "
            +COL_ST_PRODUCT_TYPE+" Text, "+COL_ST_QUANTITY+" Text, "+ COL_ST_PRODUCT_FOR +" Text);";



    //customer database required variable name
    public static final String TABLE_CT_NAME = "customer";
    public static final String COL_CT_ID = "ctId";
    public static final String COL_CT_NAME = "customer_name";
    public static final String COL_CT_CODE = "customer_code";
    public static final String COL_CT_TYPE_ = "customer_type";
    public static final String COL_CT_GENDER = "gender";
    public static final String COL_CT_PHONE = "phone";
    public static final String COL_CT_EMAIL = "email";
    public static final String COL_CT_ADDRESS = "address";
    public static final String COL_CT_DUE_AMOUNT = "due_amount";

    public static final String CUSTOMER_TABLE_QUERY = "Create Table "+ TABLE_CT_NAME +" ( "
            +COL_CT_ID+" Integer primary key AUTOINCREMENT, "+COL_CT_NAME+" Text, "+COL_CT_CODE+" Text, "
            +COL_CT_TYPE_+" Text, "+COL_CT_GENDER+" Text, "+COL_CT_PHONE+" Text, "
            +COL_CT_EMAIL+" Text, "+COL_CT_ADDRESS+" Text, "+COL_CT_DUE_AMOUNT +" Text);";

    //product database required variable name
    public static final String TABLE_CATEGORY_NAME = "product_category";
    public static final String CAT_P_ID = "id";
    public static final String CAT_P_NAME = "category_name";
    public static final String CAT_P_DESC = "category_description";


    public static final String CATEGORY_TABLE_QUERY = "Create Table "+TABLE_CATEGORY_NAME+" ( "
            +CAT_P_ID+" Integer primary key AUTOINCREMENT, "+CAT_P_NAME+" Text, "+CAT_P_DESC+" Text);";


    //product database required variable name
    public static final String TABLE_VARIATION = "variation";
    public static final String VARIATION_ID = "id";
    public static final String VARIATION_NAME = "variation_name";
    public static final String VARIATION_DESC = "variation_description";


    public static final String CREATE_VARIATION_TABLE = "Create Table "+TABLE_VARIATION+"("
            +VARIATION_ID+" Integer primary key AUTOINCREMENT, "+VARIATION_NAME+" Text, "+VARIATION_DESC+" Text);";

    protected final static String DROP_VARIATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_VARIATION + " ";

    //product database required variable name
    public static final String TABLE_P_NAME = "product";
    public static final String COL_P_ID = "id";
    public static final String COL_P_NAME = "product_name";
    public static final String COL_P_CODE = "product_code";
    public static final String COL_P_PRICE = "product_price";
    public static final String COL_P_SELL_PRICE = "sell_price";
    public static final String COL_P_UNIT = "unit";
    public static final String COL_P_BRAND = "brand";
    public static final String COL_P_SIZE = "size";
    public static final String COL_P_CATEGORY = "product_category";

    public static final String PRODUCT_TABLE_QUERY = "Create Table "+TABLE_P_NAME+" ( "
            +COL_P_ID+" Integer primary key AUTOINCREMENT, "+COL_P_NAME+" Text, "+COL_P_CODE+" Text, "
            +COL_P_PRICE+" Text, "+COL_P_SELL_PRICE+" Text, "+COL_P_UNIT+" Text, "
            +COL_P_BRAND+" Text, "+COL_P_SIZE +" Text,"+COL_P_CATEGORY +" Text);";


    public static final String TABLE_PRODUCT_VARIATION = "product_variation";
    public static final String P_V_ID ="id";
    public static final String VAR_ID = "vid";
    public static final String PRODUCT_ID = "pid";
    public static final String P_V_Stock="product_variation_size";



    public static final String PRODUCT_VARIATION_TABLE_QUERY = "Create Table "+TABLE_PRODUCT_VARIATION+" ( "
            +P_V_ID+" Integer primary key AUTOINCREMENT, "+VAR_ID+" Text, "+PRODUCT_ID+" Text, "+P_V_Stock+" Text);";

    //due database required variable name
    public static final String TABLE_DUE_INFO_NAME = "due_info";
    public static final String COL_DUE_ID = "id";
    public static final String COL_DUE_CUSTOMER_ID = "customer_id";
    public static final String COL_DUE_TOTAL_AMOUNT = "total_amount";
    public static final String COL_DUE_PAY_AMOUNT = "pay_amount";
    public static final String COL_DUE_DUE = "due";
    public static final String COL_DUE_SELLS_CODE = "sells_code";
    public static final String COL_DUE_PAY_DUE_DATE = "pay_due_date";
    public static final String COL_DUE_DEPOSIT = "due_deposit";

    public static final String SELL_TABLE_QUERY = "Create Table "+ TABLE_DUE_INFO_NAME +" ( "
            + COL_DUE_ID +" Integer primary key AUTOINCREMENT, "+ COL_DUE_CUSTOMER_ID +" Text, "+ COL_DUE_TOTAL_AMOUNT +" Text, "
            + COL_DUE_PAY_AMOUNT +" Text, "+ COL_DUE_DUE +" Text, "+ COL_DUE_SELLS_CODE +" Text, "
            + COL_DUE_PAY_DUE_DATE +" Text, "+ COL_DUE_DEPOSIT +" Text);";


    //user database required variable name
    public static final String TABLE_USER_NAME = "user";
    public static final String COL_U_ID = "id";
    public static final String COL_U_NAME = "user_name";
    public static final String COL_U_EMAIL = "email";
    public static final String COL_U_PASSWORD = "password";
    public static final String COL_U_PHONE = "phone";
    public static final String COL_U_EMPLOYEE_ID = "employee_id";

    public static final String USER_TABLE_QUERY = "Create Table "+TABLE_USER_NAME+" ( "
            +COL_U_ID+" Integer primary key AUTOINCREMENT, "+COL_U_NAME+" Text, "+COL_U_EMAIL+" Text, "
            +COL_U_PASSWORD+" Text, "+COL_U_PHONE+" Text, "+COL_U_EMPLOYEE_ID +" Text);";



    //sell database database required variable name
    public static final String TABLE_SELL_NAME = "sells";
    public static final String COL_SELL_ID = "id";
    public static final String COL_SELL_SELLS_CODE = "sells_code";
    public static final String COL_SELL_CUSTOMER_ID = "customer_id";
    public static final String COL_SELL_AMOUNT = "total_amount";
    public static final String COL_SELL_DISCOUNT = "discount";
    public static final String COL_SELL_PAY_AMOUNT = "pay_amount";
    public static final String COL_SELL_PAYMENT_TYPE = "payment_type";
    public static final String COL_SELL_SELL_DATE = "sell_date";
    public static final String COL_SELL_PAYMENT_STATUS = "payment_status";
    public static final String COL_SELL_SELL_BY = "sell_by";

    public static final String SELLS_TABLE_QUERY = "Create Table "+TABLE_SELL_NAME+" ( "
            +COL_SELL_ID+" Integer primary key AUTOINCREMENT, "+COL_SELL_SELLS_CODE+" Text, "+COL_SELL_CUSTOMER_ID+" Text, "
            +COL_SELL_AMOUNT+" Text, "+COL_SELL_DISCOUNT+" Text, "+COL_SELL_PAY_AMOUNT+" Text, "
            +COL_SELL_PAYMENT_TYPE+" Text, "+COL_SELL_SELL_DATE+" Text, "+COL_SELL_PAYMENT_STATUS+" Text, "
            +COL_SELL_SELL_BY +" Text);";




    //sell product info database database required variable name
    public static final String TABLE_SOLD_PRODUCT_NAME = "sell_product_info";
    public static final String COL_SOLD_PRODUCT_ID = "id";
    public static final String COL_SOLD_PRODUCT_CODE = "sells_code";
    public static final String COL_SOLD_PRODUCT_SELL_ID = "sells_id";
    public static final String COL_SOLD_PRODUCT_PRODUCT_ID = "sells_product_id";
    public static final String COL_SOLD_PRODUCT_VARIATION_ID = "sells_product_variation_id";
    public static final String COL_SOLD_PRODUCT_PRICE = "product_price";
    public static final String COL_SOLD_PRODUCT_QUANTITY = "quantity";
    public static final String COL_SOLD_PRODUCT_TOTAL_PRICE = "total_price";
    public static final String COL_SOLD_PRODUCT_PENDING_STATUS = "pending_status";

    public static final String SOLD_PRODUCT_TABLE_QUERY = "Create Table "+TABLE_SOLD_PRODUCT_NAME+" ( "
            +COL_SOLD_PRODUCT_ID+" Integer primary key AUTOINCREMENT, "+COL_SOLD_PRODUCT_CODE+" Text, "+COL_SOLD_PRODUCT_SELL_ID+" Text, "
            +COL_SOLD_PRODUCT_VARIATION_ID+" Text, "
            +COL_SOLD_PRODUCT_PRODUCT_ID+" Text, "+COL_SOLD_PRODUCT_PRICE+" Text, "+COL_SOLD_PRODUCT_QUANTITY+" Text, "
            +COL_SOLD_PRODUCT_TOTAL_PRICE+" Text, "+COL_SOLD_PRODUCT_PENDING_STATUS +" Text);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_TABLE_QUERY);
        db.execSQL(CREATE_VARIATION_TABLE);
        db.execSQL(PRODUCT_VARIATION_TABLE_QUERY);
        db.execSQL(CREATE_PURCHASES_TABLE);
        db.execSQL(CREATE_SUPPLIERS_TABLE);
        db.execSQL(STOCK_TABLE_QUERY);
        db.execSQL(CUSTOMER_TABLE_QUERY);
        db.execSQL(PRODUCT_TABLE_QUERY);
        db.execSQL(SELL_TABLE_QUERY);
        db.execSQL(USER_TABLE_QUERY);
        db.execSQL(SELLS_TABLE_QUERY);
        db.execSQL(SOLD_PRODUCT_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PURCHASES_TABLE);
        db.execSQL(DROP_SUPPLIERS_TABLE);
        db.execSQL("Drop table if EXISTS "+STOCK_TABLE_QUERY);
        db.execSQL("Drop table if exist "+CUSTOMER_TABLE_QUERY);
        db.execSQL("Drop table if exist "+PRODUCT_TABLE_QUERY);
        db.execSQL("Drop table if exist "+ SELL_TABLE_QUERY);
        db.execSQL("Drop table if exist "+USER_TABLE_QUERY);
        db.execSQL("Drop table if exist "+SELLS_TABLE_QUERY);
        db.execSQL("Drop table if exist "+SOLD_PRODUCT_TABLE_QUERY);
        db.execSQL("Drop table if exist "+DROP_VARIATION_TABLE);


        onCreate(db);
    }


}
