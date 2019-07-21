package com.example.tycohanx.emsv2.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.tycohanx.emsv2.Managers.ExpenseManager;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String COL_AMOUNT = "amount";
    public static final String COL_BALANCE = "balance";
    public static final String COL_BALANCE_ACCOUNT = "accountbalance";
    public static final String COL_BALANCE_CASH = "cash";
    public static final String COL_BALANCE_ID = "id";
    public static final String COL_BL_AMOUNT = "amount";
    public static final String COL_BL_DATE = "date";
    public static final String COL_BL_DESCRIPTION = "description";
    public static final String COL_BL_ID = "id";
    public static final String COL_BL_IS_SECONDARY = "is_secondary";
    public static final String COL_BL_SOURCE = "source";
    public static final String COL_BL_UID = "uid";
    public static final String COL_CATEGORY = "category";
    public static final String COL_CATEGORY_ID = "catId";
    public static final String COL_CAT_ID = "id";
    public static final String COL_CAT_NAME = "name";
    public static final String COL_DATE = "date";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_ID = "id";
    public static final String COL_IS_SECONDARY = "secondary";
    public static final String COL_SUBCAT_ID = "id";
    public static final String COL_SUBCAT_NAME = "name";
    public static final String COL_SUB_CATEGORY = "subCategory";
    public static final String COL_USERID = "userid";
    private static final String CREATE_BALANCE_MAIN_TABLE = "CREATE TABLE balance_main (  id INTEGER PRIMARY KEY, date INTEGER, source TEXT, description TEXT, uid INTEGER, is_secondary INTEGER, amount DECIMAL )";
    private static final String CREATE_BALANCE_TABLE = "CREATE TABLE balance (  id INTEGER PRIMARY KEY, cash DECIMAL, userid INTEGER, accountbalance DECIMAL )";
    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE category (  id INTEGER PRIMARY KEY, userid INTEGER, name TEXT )";
    private static final String CREATE_EXPENSE_TABLE = "CREATE TABLE expense (  id INTEGER PRIMARY KEY, category TEXT, subCategory TEXT, userid INTEGER, amount INTEGER, date INTEGER, secondary INTEGER, balance INTEGER, description TEXT )";
    private static final String CREATE_SUBCATEGORY_TABLE = "CREATE TABLE subcategory (  id INTEGER PRIMARY KEY, name TEXT, userid INTEGER, catId INTEGER,  FOREIGN KEY (catId) REFERENCES category(id));";
    public static final String DATABASE_NAME = "all_in_One";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_BALANCE = "balance";
    public static final String TABLE_BALANCE_MAIN = "balance_main";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_EXPENSE = "expense";
    public static final String TABLE_SUBCATEGORY = "subcategory";
    private Context context;
    private ExpenseManager manager;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EXPENSE_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_SUBCATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_BALANCE_TABLE);
        sqLiteDatabase.execSQL(CREATE_BALANCE_MAIN_TABLE);
        sqLiteDatabase.execSQL("INSERT INTO balance VALUES ( 1, 0, 0,0 )");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int newVersion, int oldVersion) {
    }
}
