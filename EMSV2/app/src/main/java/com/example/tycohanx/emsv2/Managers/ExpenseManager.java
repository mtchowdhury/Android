package com.example.tycohanx.emsv2.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.tycohanx.emsv2.DataBase.DataBaseHelper;
import com.example.tycohanx.emsv2.Models.Balance;
import com.example.tycohanx.emsv2.Models.Category;
import com.example.tycohanx.emsv2.Models.Expense;
import com.example.tycohanx.emsv2.Models.MainBalance;
import com.example.tycohanx.emsv2.Models.SubCategory;
import java.util.ArrayList;

public class ExpenseManager {
    private Category category;
    private SQLiteDatabase database;
    private Expense expense;
    private DataBaseHelper helper;
    private MainBalance mainBalance;
    private SubCategory subCat;

    public ExpenseManager(Context context) {
        this.helper = new DataBaseHelper(context);
    }

    private void openDatabase() {
        this.database = this.helper.getWritableDatabase();
    }

    private void closeDatabase() {
        this.helper.close();
    }

    public boolean addExpense(Expense expense) {
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put("category", expense.getCategory());
        cv.put(DataBaseHelper.COL_SUB_CATEGORY, expense.getSubCategory());
        cv.put("amount", Integer.valueOf(expense.getAmount()));
        cv.put("date", Long.valueOf(expense.getDate()));
        cv.put("description", expense.getDescription());
        cv.put("balance", Integer.valueOf(expense.getBalance()));
        cv.put(DataBaseHelper.COL_IS_SECONDARY, Integer.valueOf(expense.getIs_secondary()));
        long inserted = this.database.insert(DataBaseHelper.TABLE_EXPENSE, null, cv);
        closeDatabase();
        return inserted > 0;
    }

    public boolean addCategory(Category category) {
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", category.getName());
        long inserted = this.database.insert("category", null, cv);
        closeDatabase();
        return inserted > 0;
    }

    public boolean addBalance(Balance balance) {
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_BALANCE_CASH, Integer.valueOf(balance.getCash()));
        cv.put(DataBaseHelper.COL_BALANCE_ACCOUNT, Integer.valueOf(balance.getAccount()));
        long inserted = this.database.insert("balance", null, cv);
        closeDatabase();
        return inserted > 0;
    }

    public boolean addSubCategory(SubCategory subCategory) {
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", subCategory.getName());
        cv.put(DataBaseHelper.COL_CATEGORY_ID, Integer.valueOf(subCategory.getCatId()));
        long inserted = this.database.insert(DataBaseHelper.TABLE_SUBCATEGORY, null, cv);
        closeDatabase();
        return inserted > 0;
    }

    public boolean addMainBalance(MainBalance balance) {
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", Long.valueOf(balance.getDate()));
        cv.put(DataBaseHelper.COL_BL_SOURCE, balance.getSource());
        cv.put("amount", Integer.valueOf(balance.getAmount()));
        cv.put("description", balance.getDescription());
        cv.put(DataBaseHelper.COL_BL_IS_SECONDARY, Integer.valueOf(balance.getIs_Secondary()));
        cv.put(DataBaseHelper.COL_BL_UID, Integer.valueOf(balance.getUid()));
        long inserted = this.database.insert(DataBaseHelper.TABLE_BALANCE_MAIN, null, cv);
        closeDatabase();
        return inserted > 0;
    }

    public ArrayList<MainBalance> getAllMainBalance() {
        openDatabase();
        Cursor cursor = this.database.query(DataBaseHelper.TABLE_BALANCE_MAIN, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<MainBalance> mainBalances = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int blId = cursor.getInt(cursor.getColumnIndex("id"));
                long blDate = cursor.getLong(cursor.getColumnIndex("date"));
                this.mainBalance = new MainBalance(blId, cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_BL_SOURCE)), cursor.getString(cursor.getColumnIndex("description")), cursor.getInt(cursor.getColumnIndex("amount")), blDate, cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_BL_UID)), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_BL_IS_SECONDARY)));
                mainBalances.add(this.mainBalance);
                cursor.moveToNext();
            }
        }
        closeDatabase();
        return mainBalances;
    }

    public ArrayList<Expense> getAllExpense() {
        openDatabase();
        Cursor cursor = this.database.query(DataBaseHelper.TABLE_EXPENSE, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Expense> expList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                this.expense = new Expense(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("amount")), cursor.getString(cursor.getColumnIndex("category")), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_SUB_CATEGORY)), cursor.getString(cursor.getColumnIndex("description")), cursor.getLong(cursor.getColumnIndex("date")), 0, cursor.getInt(cursor.getColumnIndex("balance")), 0);
                expList.add(this.expense);
                cursor.moveToNext();
            }
        }
        closeDatabase();
        return expList;
    }

    public ArrayList<Expense> getExpenseByFilter(long fromDate, long toDate,String categoryString,String subCategoryString) {
        openDatabase();
        String catQuery=categoryString=="ALL"?"":" AND category = '"+categoryString+"' ";
        String subCatQuery=subCategoryString=="ALL"?"":" AND subCategory = '"+subCategoryString+ "'";
        long to = toDate;
       // Cursor cursor = this.database.rawQuery("SELECT * FROM expense WHERE date>='" + fromDate + "' AND " + "date" + "<='" + toDate + "'"+ catQuery+subCatQuery, null);
        Cursor cursor = this.database.rawQuery("SELECT * FROM expense WHERE date between '" + fromDate + "' AND '"  + toDate + "'"+ catQuery+subCatQuery, null);
        cursor.moveToFirst();
        ArrayList<Expense> expList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                this.expense = new Expense(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("amount")), cursor.getString(cursor.getColumnIndex("category")), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_SUB_CATEGORY)), cursor.getString(cursor.getColumnIndex("description")), cursor.getLong(cursor.getColumnIndex("date")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_USERID)), cursor.getInt(cursor.getColumnIndex("balance")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_IS_SECONDARY)));
                expList.add(this.expense);
                cursor.moveToNext();
            }
            closeDatabase();
        }
        return expList;
    }

    public ArrayList<Category> getAllCategory() {
        openDatabase();
        Cursor cursor = this.database.query("category", null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Category> catList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                this.category = new Category(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")));
                catList.add(this.category);
                cursor.moveToNext();
            }
            closeDatabase();
        }
        return catList;
    }

    public ArrayList<SubCategory> getAllSubCategory() {
        openDatabase();
        Cursor cursor = this.database.query(DataBaseHelper.TABLE_SUBCATEGORY, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<SubCategory> subCatList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                this.subCat = new SubCategory(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_CATEGORY_ID)), cursor.getString(cursor.getColumnIndex("name")));
                subCatList.add(this.subCat);
                cursor.moveToNext();
            }
            closeDatabase();
        }
        return subCatList;
    }

    public ArrayList<SubCategory> getSubCategoryById(int id) {
        openDatabase();
        Cursor cursor = this.database.query(DataBaseHelper.TABLE_SUBCATEGORY, null, "id=" + id, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<SubCategory> subCatList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                this.subCat = new SubCategory(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_CATEGORY_ID)), cursor.getString(cursor.getColumnIndex("name")));
                subCatList.add(this.subCat);
            }
            closeDatabase();
        }
        return subCatList;
    }

    public ArrayList<SubCategory> getSubCategoryByCategoryId(int id) {
        openDatabase();
        Cursor cursor = this.database.query(DataBaseHelper.TABLE_SUBCATEGORY, null, "catId=" + id, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<SubCategory> subCatList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                this.subCat = new SubCategory(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_CATEGORY_ID)), cursor.getString(cursor.getColumnIndex("name")));
                subCatList.add(this.subCat);
                cursor.moveToNext();
            }
            closeDatabase();
        }
        return subCatList;
    }

    public Balance getLastBalance() {
        openDatabase();
        Cursor cursor = this.database.query("balance", null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Balance> BalanceList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                BalanceList.add(new Balance(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_BALANCE_CASH)), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_BALANCE_ACCOUNT))));
                cursor.moveToNext();
            }
            closeDatabase();
        }
        if (BalanceList.size() <= 0) {
            return new Balance(0, 0);
        }
        return (Balance) BalanceList.get(BalanceList.size() - 1);
    }

    public boolean deleteExpenseById(int id) {
        openDatabase();
        int deleted = this.database.delete(DataBaseHelper.TABLE_EXPENSE, "id=" + id, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteCategoryById(int id) {
        openDatabase();
        int deleted = this.database.delete("category", "id=" + id, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteSubCategoryById(int id) {
        openDatabase();
        int deleted = this.database.delete(DataBaseHelper.TABLE_SUBCATEGORY, "id=" + id, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteBalanceById(int id) {
        openDatabase();
        int deleted = this.database.delete("balance", "id=" + id, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteMainBalanceById(int id) {
        openDatabase();
        int deleted = this.database.delete(DataBaseHelper.TABLE_BALANCE_MAIN, "id=" + id, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteAllExpense() {
        openDatabase();
        int deleted = this.database.delete(DataBaseHelper.TABLE_EXPENSE, null, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteAllCategory() {
        openDatabase();
        int deleted = this.database.delete("category", null, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteAllsubCategory() {
        openDatabase();
        int deleted = this.database.delete(DataBaseHelper.TABLE_SUBCATEGORY, null, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean deleteAllBalance() {
        openDatabase();
        int deleted = this.database.delete("balance", null, null);
        closeDatabase();
        return deleted > 0;
    }

    public boolean updateBalance(Balance balance) {
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_BALANCE_CASH, Integer.valueOf(balance.getCash()));
        cv.put(DataBaseHelper.COL_BALANCE_ACCOUNT, Integer.valueOf(balance.getAccount()));
        long updated = 0;
        try {
            updated = (long) this.database.update("balance", cv, "id=" + balance.getId(), null);
        } catch (Exception e) {
        }
        closeDatabase();
        return updated > 0;
    }

    public ArrayList<Expense> getExpenseByDateRange(String sentDate, String toDate) {
        openDatabase();
        Cursor cursor = this.database.query(DataBaseHelper.TABLE_EXPENSE, new String[]{"id", "category", DataBaseHelper.COL_SUB_CATEGORY, "amount", "date", "description"}, "date like " + sentDate, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Expense> expList = new ArrayList();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int expenseId = cursor.getInt(cursor.getColumnIndex("id"));
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String subCategory = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_SUB_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                int date = cursor.getInt(cursor.getColumnIndex("date"));
                long j = (long) date;
                this.expense = new Expense(expenseId, amount, category, subCategory, description, j, cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_USERID)), cursor.getInt(cursor.getColumnIndex("balance")), cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_IS_SECONDARY)));
                expList.add(this.expense);
                cursor.moveToNext();
            }
            closeDatabase();
        }
        return expList;
    }
}
