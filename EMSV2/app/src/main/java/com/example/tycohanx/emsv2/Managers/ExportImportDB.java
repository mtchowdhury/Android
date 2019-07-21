package com.example.tycohanx.emsv2.Managers;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import com.example.tycohanx.emsv2.DataBase.CSVReader;
import com.example.tycohanx.emsv2.DataBase.CSVWriter;
import com.example.tycohanx.emsv2.DataBase.DataBaseHelper;
import com.example.tycohanx.emsv2.Models.Balance;
import com.example.tycohanx.emsv2.Models.Category;
import com.example.tycohanx.emsv2.Models.Expense;
import com.example.tycohanx.emsv2.Models.SubCategory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.channels.FileChannel;

public class ExportImportDB {
    Context context;
    ExpenseManager manager;

    public ExportImportDB(Context context) {
        this.context = context;
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                File currentDB = new File(data, "data/com.tycohanx.ems/databases/all_in_One");
                File backupDB = new File(sd.getAbsolutePath(), "db_dump.db");
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
        }
    }

    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                File backupDB = new File(data, "data/com.tycohanx.ems/databases/all_in_One");
                File currentDB = new File(sd.getAbsolutePath(), "db_dump.db");
                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
        }
    }

    public boolean exportDBData() {
        DataBaseHelper dbhelper = new DataBaseHelper(this.context);
        File exportDir = new File(Environment.getExternalStorageDirectory(), "emsBackup");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        for (String tl : new String[]{DataBaseHelper.TABLE_EXPENSE, "category", DataBaseHelper.TABLE_SUBCATEGORY, "balance"}) {
            int obj = -1;
            switch (tl.hashCode()) {
                case -1309357992:
                    if (tl.equals(DataBaseHelper.TABLE_EXPENSE)) {
                        obj = -10000;
                        break;
                    }
                    break;
                case -339185956:
                    if (tl.equals("balance")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 50511102:
                    if (tl.equals("category")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1300380478:
                    if (tl.equals(DataBaseHelper.TABLE_SUBCATEGORY)) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            File file;
            CSVWriter csvWrite;
            Cursor curCSV;
            switch (obj) {
                case -10000:
                    file = new File(exportDir, "Table_expense.csv");
                    try {
                        file.createNewFile();
                        csvWrite = new CSVWriter(new FileWriter(file));
                        curCSV = dbhelper.getReadableDatabase().rawQuery("SELECT * FROM expense", null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while (curCSV.moveToNext()) {
                            csvWrite.writeNext(new String[]{String.valueOf(curCSV.getInt(curCSV.getColumnIndex("id"))), String.valueOf(curCSV.getString(curCSV.getColumnIndex("category"))), String.valueOf(curCSV.getString(curCSV.getColumnIndex(DataBaseHelper.COL_SUB_CATEGORY))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_USERID))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex("amount"))), String.valueOf(curCSV.getString(curCSV.getColumnIndex("date"))), String.valueOf(curCSV.getString(curCSV.getColumnIndex("description"))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex("balance"))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_IS_SECONDARY)))});
                        }
                        csvWrite.close();
                        curCSV.close();
                        break;
                    } catch (Exception e) {
                        return false;
                    }
                case 1:
                    file = new File(exportDir, "Table_category.csv");
                    try {
                        file.createNewFile();
                        csvWrite = new CSVWriter(new FileWriter(file));
                        curCSV = dbhelper.getReadableDatabase().rawQuery("SELECT * FROM category", null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while (curCSV.moveToNext()) {
                            csvWrite.writeNext(new String[]{String.valueOf(curCSV.getInt(curCSV.getColumnIndex("id"))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_USERID))), String.valueOf(curCSV.getString(curCSV.getColumnIndex("name")))});
                        }
                        csvWrite.close();
                        curCSV.close();
                        break;
                    } catch (Exception e2) {
                        return false;
                    }
                case 2:
                    file = new File(exportDir, "Table_subcategory.csv");
                    try {
                        file.createNewFile();
                        csvWrite = new CSVWriter(new FileWriter(file));
                        curCSV = dbhelper.getReadableDatabase().rawQuery("SELECT * FROM subcategory", null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while (curCSV.moveToNext()) {
                            csvWrite.writeNext(new String[]{String.valueOf(curCSV.getInt(curCSV.getColumnIndex("id"))), String.valueOf(curCSV.getString(curCSV.getColumnIndex("name"))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_USERID))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_CATEGORY_ID)))});
                        }
                        csvWrite.close();
                        curCSV.close();
                        break;
                    } catch (Exception e3) {
                        return false;
                    }
                case 3:
                    file = new File(exportDir, "Table_balance.csv");
                    try {
                        file.createNewFile();
                        csvWrite = new CSVWriter(new FileWriter(file));
                        curCSV = dbhelper.getReadableDatabase().rawQuery("SELECT * FROM balance", null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while (curCSV.moveToNext()) {
                            csvWrite.writeNext(new String[]{String.valueOf(curCSV.getInt(curCSV.getColumnIndex("id"))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_BALANCE_CASH))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_USERID))), String.valueOf(curCSV.getInt(curCSV.getColumnIndex(DataBaseHelper.COL_BALANCE_ACCOUNT)))});
                        }
                        csvWrite.close();
                        curCSV.close();
                        break;
                    } catch (Exception e4) {
                        return false;
                    }
                default:
                    break;
            }
        }
        return true;
    }

    public boolean importDBData() {
        this.manager = new ExpenseManager(this.context);
        File file = new File(Environment.getExternalStorageDirectory(), "emsBackup");
        for (String tl : new String[]{DataBaseHelper.TABLE_EXPENSE, "category", DataBaseHelper.TABLE_SUBCATEGORY, "balance"}) {
            int obj = -1;
            switch (tl.hashCode()) {
                case -1309357992:
                    if (tl.equals(DataBaseHelper.TABLE_EXPENSE)) {
                        obj = -10000;
                        break;
                    }
                    break;
                case -339185956:
                    if (tl.equals("balance")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 50511102:
                    if (tl.equals("category")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1300380478:
                    if (tl.equals(DataBaseHelper.TABLE_SUBCATEGORY)) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            CSVReader cSVReader;
            int skipFirst;
            String[] nextLine;
            int id;
            int intValue;
            switch (obj) {
                case -10000:
                    try {
                        cSVReader = new CSVReader(new FileReader(new File(file, "Table_expense.csv")));
                        skipFirst = 0;
                        int nullcounter=0;
                        this.manager.deleteAllExpense();
                        while (true) {
                            nextLine = cSVReader.readNext();
                            if (nextLine == null) {
                                nullcounter++;
                                if(nullcounter>30)break;
                                continue;
                            } else if (skipFirst == 0) {
                                skipFirst++;
                            } else {
                                if (!this.manager.addExpense(new Expense(Integer.valueOf(nextLine[0]).intValue(), Integer.valueOf(nextLine[4]).intValue(), nextLine[1], nextLine[2], nextLine[6], Long.parseLong(nextLine[5]), Integer.valueOf(nextLine[3]).intValue(), Integer.valueOf(nextLine[7]).intValue(), Integer.valueOf(nextLine[8]).intValue()))) {
                                    return false;
                                }
                            }
                        }
                    } catch (Exception e) {

                        break;
                    }
                case 1:
                    try {
                        cSVReader = new CSVReader(new FileReader(new File(file, "Table_category.csv")));
                        skipFirst = 0;
                        this.manager.deleteAllCategory();
                        int nullcounter=0;
                        while (true) {
                            nextLine = cSVReader.readNext();
                            if (nextLine == null) {
                                nullcounter++;
                                if(nullcounter>30)break;
                                continue;
                            } else if (skipFirst == 0) {
                                skipFirst++;
                            } else {
                                id = Integer.valueOf(nextLine[0]).intValue();
                                intValue = Integer.valueOf(nextLine[1]).intValue();
                                if (!this.manager.addCategory(new Category(id, nextLine[2]))) {
                                    return false;
                                }
                            }
                        }
                    } catch (Exception e2) {
                        break;
                    }
                case 2:
                    try {
                        cSVReader = new CSVReader(new FileReader(new File(file, "Table_subcategory.csv")));
                        skipFirst = 0;
                        int nullcounter=0;
                        this.manager.deleteAllsubCategory();
                        while (true) {
                            nextLine = cSVReader.readNext();
                            if (nextLine == null) {
                                nullcounter++;
                                if(nullcounter>30)break;
                                continue;
                            } else if (skipFirst == 0) {
                                skipFirst++;
                            } else {
                                id = Integer.valueOf(nextLine[0]).intValue();
                                String Name = nextLine[1];
                                intValue = Integer.valueOf(nextLine[2]).intValue();
                                if (!this.manager.addSubCategory(new SubCategory(Integer.valueOf(nextLine[3]).intValue(), Name))) {
                                    return false;
                                }
                            }
                        }
                    } catch (Exception e3) {
                        break;
                    }
                case 3:
                    try {
                        cSVReader = new CSVReader(new FileReader(new File(file, "Table_balance.csv")));
                        skipFirst = 0;
                        int nullcounter=0;
                        this.manager.deleteAllBalance();
                        while (true) {
                            nextLine = cSVReader.readNext();
                            if (nextLine == null) {
                                nullcounter++;
                                if(nullcounter>30)break;
                                continue;
                            } else if (skipFirst == 0) {
                                skipFirst++;
                            } else {
                                id = Integer.valueOf(nextLine[0]).intValue();
                                int cash = Integer.valueOf(nextLine[1]).intValue();
                                intValue = Integer.valueOf(nextLine[2]).intValue();
                                if (!this.manager.addBalance(new Balance(id, cash, Integer.valueOf(nextLine[3]).intValue()))) {
                                    return false;
                                }
                            }
                        }
                    } catch (Exception e4) {
                        break;
                    }
                default:
                    break;
            }
        }
        return true;
    }
}
