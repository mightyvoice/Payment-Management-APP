package com.example.lj.paymentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lj on 15/9/30.
 */

public class MyData extends SQLiteOpenHelper{

    public static ArrayList<String> accountList = new ArrayList<String>();
    public static ArrayAdapter<String> accountListAdapter;

    public static ArrayList<String> paymentList = new ArrayList<String>();
    public static ArrayAdapter<String> paymentListAdapter;

    public static boolean confirmedToDelete = false;

    public static Integer clickedItemID = -1;

    //database information
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myAccountsAndPayments.db";

    //column names for the account table
    public static final String TABLE_ACCOUNTS = "myAccounts";
    public static final String COLUMN_ACCOUNT_ID = "_id";
    public static final String COLUMN_ACCOUNT_NAME = "accountName";
    public static final String COLUMN_ACCOUNT_BANK = "accountBank";
    public static final String COLUMN_ACCOUNT_DUEDAY = "accountDueDay";
    public static final String COLUMN_ACCOUNT_TOPAY = "accountToPay";

    //column names for the payment table
    public static final String TABLE_PAYMENTS = "myPayments";
    public static final String COLUMN_PAYMENT_ID = "_id";
    public static final String COLUMN_PAY_ACCOUNT = "payAccount";
    public static final String COLUMN_PAID_ACCOUNT = "paidAccount";
    public static final String COLUMN_PAY_AMOUNT = "payAmount";
    public static final String COLUMN_PAY_DATE = "payDate";
    public static final String COLUMN_PAY_TOTAL_THIS_MONTH = "payTotalThisMonth";

    public MyData(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create account table;
        String query = "CREATE TABLE " + TABLE_ACCOUNTS + "(" +
                COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCOUNT_NAME + " TEXT, " +
                COLUMN_ACCOUNT_BANK + " TEXT, " +
                COLUMN_ACCOUNT_DUEDAY + " INTEGER, " +
                COLUMN_ACCOUNT_TOPAY + " DOUBLE " +
                ");";
        db.execSQL(query);

        //create payment table;
        query = "CREATE TABLE " + TABLE_PAYMENTS + "(" +
                COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PAY_ACCOUNT + " TEXT, " +
                COLUMN_PAID_ACCOUNT + " TEXT, " +
                COLUMN_PAY_AMOUNT + " DOUBLE, " +
                COLUMN_PAY_DATE + " TEXT, " +
                COLUMN_PAY_TOTAL_THIS_MONTH + " DOUBLE " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS);
        onCreate(db);
    }

    //add a new row to the myAccounts table
    public void addAccountToDatabase(MyAccount myAccount){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT_NAME, myAccount.accountName);
        values.put(COLUMN_ACCOUNT_BANK, myAccount.bankName);
        values.put(COLUMN_ACCOUNT_DUEDAY, myAccount.dueDay);
        values.put(COLUMN_ACCOUNT_TOPAY, myAccount.toPayBalance);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close();
        updateAccountListView();
    }

    public void updateAccountToPayBalance(String accountName, Double paidAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " +
                COLUMN_ACCOUNT_NAME + "=\"" + accountName +"\";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Double currentBalance = 0.0;
        if(!cursor.isAfterLast() && cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_NAME))
                != null){
            currentBalance = cursor.getDouble(
                    cursor.getColumnIndex(COLUMN_ACCOUNT_TOPAY));
        }
        Double newBalance = currentBalance - paidAmount;
        query = "UPDATE "+TABLE_ACCOUNTS+" SET " +
                COLUMN_ACCOUNT_TOPAY + "=" + newBalance.toString() +
                " WHERE " + COLUMN_ACCOUNT_NAME + "=\"" + accountName+"\";";
        db.execSQL(query);
        db.close();
        updateAccountListView();
    }

    public void updateAccountListView(){
        accountList.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + ";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_NAME)) != null) {
                String tmp = cursor.getString(
                        cursor.getColumnIndex(COLUMN_ACCOUNT_NAME))+",";
                tmp += cursor.getString(
                        cursor.getColumnIndex(COLUMN_ACCOUNT_BANK))+",";
                tmp += "    "+ cursor.getString(
                        cursor.getColumnIndex(COLUMN_ACCOUNT_DUEDAY))+",";
                tmp += "    $"+ cursor.getString(
                        cursor.getColumnIndex(COLUMN_ACCOUNT_TOPAY));
                accountList.add(tmp);
            }
            cursor.moveToNext();
        }
        db.close();
        accountList.add("Total Balance Need To Pay: $"
                + getCurrentTotalBalanceNeedToPay().toString());
        accountListAdapter.notifyDataSetChanged();
    }

    //add a new payment to the myAccounts table
    public void addPaymentToDatabase(MyPaymentItem myPayment){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAY_ACCOUNT, myPayment.payAccountName);
        values.put(COLUMN_PAID_ACCOUNT, myPayment.paidAccountName);
        values.put(COLUMN_PAY_AMOUNT, myPayment.payAmount);
        values.put(COLUMN_PAY_DATE, myPayment.payDate);
        values.put(COLUMN_PAY_TOTAL_THIS_MONTH,
                myPayment.payAmount + getCurrentTotalPayThisMonth());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PAYMENTS, null, values);
        db.close();
        updatePaymentListView();
    }

    public void updatePaymentListView(){
        paymentList.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PAYMENTS + ";";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COLUMN_PAY_ACCOUNT)) != null) {
                String tmp = cursor.getString(
                        cursor.getColumnIndex(COLUMN_PAY_ACCOUNT))+",   ";
                tmp += cursor.getString(
                        cursor.getColumnIndex(COLUMN_PAID_ACCOUNT))+",    $";
                tmp += cursor.getString(
                        cursor.getColumnIndex(COLUMN_PAY_AMOUNT))+",   ";
                tmp += cursor.getString(
                        cursor.getColumnIndex(COLUMN_PAY_DATE))+",    $";
                tmp += cursor.getString(
                        cursor.getColumnIndex(COLUMN_PAY_TOTAL_THIS_MONTH));
                paymentList.add(tmp);
            }
            cursor.moveToNext();
        }
        db.close();
        paymentListAdapter.notifyDataSetChanged();
    }

    //delete a row to the myAccounts table
    public void deleteAccountFromDatabase(String accountName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ACCOUNTS + " WHERE "
                + COLUMN_ACCOUNT_NAME + "=\"" + accountName + "\";");
    }

    public MyAccount getMyAccountFromString(String data){
        String[] l = data.split(",");
        MyAccount ans = new MyAccount(l[0],
                l[1],
                MyLib.stringToInteger(l[2]),
                Double.parseDouble(l[3]));
        return ans;
    }

    public MyPaymentItem getMyPaymentItemFromString(String data){
        String[] l = data.split(",");
        MyPaymentItem ans = new MyPaymentItem(l[0], l[1],
                Double.parseDouble(l[2]), l[3]);
        return ans;
    }

    public boolean ifAccountNameAlreadyExist(String accountName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " +
                COLUMN_ACCOUNT_NAME + "=\"" + accountName +"\";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean ifAccountAlreadyExist(String account) {
        String[] tmp = account.split(",");
        if(ifAccountNameAlreadyExist(tmp[0])){
            return true;
        }
        else{
            return false;
        }
    }

    private Double getCurrentTotalPayThisMonth(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PAYMENTS + ";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Double totalBalance = 0.0;
        while(!cursor.isAfterLast()){
            totalBalance += cursor.getDouble(
                    cursor.getColumnIndex(COLUMN_PAY_AMOUNT));
            cursor.moveToNext();
        }
        db.close();
        return totalBalance;
    }

    private Double getCurrentTotalBalanceNeedToPay(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + ";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Double totalToPayBalance = 0.0;
        while(!cursor.isAfterLast()){
            totalToPayBalance += cursor.getDouble(
                    cursor.getColumnIndex(COLUMN_ACCOUNT_TOPAY));
            cursor.moveToNext();
        }
        db.close();
        return totalToPayBalance;
    }

    public static void deleteAccountByCurrentIndex(){
        accountList.remove(clickedItemID);
        accountListAdapter.notifyDataSetChanged();
    }
}
