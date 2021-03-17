package com.example.basicbankingapp.Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    final static int DB_VERSION = 1;
    final static String DB_NAME = "Users.db";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE CUSTOMERS" +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "EMAIL VARCHAR," +
                "ACCOUNT_NO VARCHAR," +
                "BANK_NAME VARCHAR," +
                "IFSC_CODE VARCHAR," +
                "BALANCE DECIMAL)");

        db.execSQL("CREATE TABLE TRANSACTIONS" +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DATE TEXT," +
                "FROM_USER TEXT," +
                "TO_USER TEXT," +
                "AMOUNT DECIMAL," +
                "TRANSACTION_STATUS TEXT," +
                "TRANSACTION_ID TEXT)");

        String Pushpendra = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                "'Pushpendra'," +
                "'pushpendra@gmail.com'," +
                "'529576252341'," +
                "'State Bank of India'," +
                "'SBIN0012345'," +
                "2000)",

                Anuj = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Anuj'," +
                        "'anuj@gmail.com'," +
                        "'726576551341'," +
                        "'Bank of Baroda'," +
                        "'BBIN0012345'," +
                        "100000)",

                Arnav = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Arnav'," +
                        "'arnav@gmail.com'," +
                        "'656776458341'," +
                        "'Yes Bank'," +
                        "'YSIN0012345'," +
                        "500000)",

                Neeraj = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Neeraj'," +
                        "'neeraj@gmail.com'," +
                        "'356756758621'," +
                        "'Axis Bank'," +
                        "'ABIN0012345'," +
                        "200000)",

                Rishabh = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Rishabh'," +
                        "'rishabh@gmail.com'," +
                        "'557787758521'," +
                        "'Axis Bank'," +
                        "'AB0012345'," +
                        "1000000)",

                Siddharth = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Siddharth'," +
                        "'siddharth@gmail.com'," +
                        "'775587758721'," +
                        "'Punjab National Bank'," +
                        "'PNB0012345'," +
                        "200)",

                Lekhansh = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Lekhansh'," +
                        "'lekhansh@gmail.com'," +
                        "'874587658521'," +
                        "'Punjab National Bank'," +
                        "'PNB0012345'," +
                        "50000)",

                Harsh = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Harsh'," +
                        "'harsh@gmail.com'," +
                        "'984567631021'," +
                        "'State Bank of India'," +
                        "'SBI0012345'," +
                        "1000)",

                Anirudh = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Anirudh'," +
                        "'anirudh@gmail.com'," +
                        "'604267437010'," +
                        "'Punjab National Bank'," +
                        "'PNB0012345'," +
                        "20000)",

                Aditya = "INSERT INTO CUSTOMERS(NAME, EMAIL, ACCOUNT_NO, BANK_NAME, IFSC_CODE, BALANCE) VALUES(" +
                        "'Aditya'," +
                        "'aditya@gmail.com'," +
                        "'763567227010'," +
                        "'HDFC Bank'," +
                        "'HDB0012345'," +
                        "200000)";

        db.execSQL(Pushpendra);
        db.execSQL(Anuj);
        db.execSQL(Arnav);
        db.execSQL(Neeraj);
        db.execSQL(Rishabh);
        db.execSQL(Siddharth);
        db.execSQL(Lekhansh);
        db.execSQL(Harsh);
        db.execSQL(Anirudh);
        db.execSQL(Aditya);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS  CUSTOMERS");
        db.execSQL("DROP TABLE IF EXISTS TRANSACTIONS");
        onCreate(db);

    }

    public void updateBal(int id, double updatedBalance) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE CUSTOMERS " +
                "SET BALANCE = " + String.valueOf(updatedBalance) +
                " WHERE ID = " + String.valueOf(id));

    }

    public Cursor getTransactions(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TRANSACTIONS", null);
        return cursor;

    }

    public boolean transactionStatement(String date, String fromUser, String toUser, String amount, String transactionStatus, long transactionId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("DATE", date);
        values.put("FROM_USER", fromUser);
        values.put("TO_USER", toUser);
        values.put("AMOUNT", amount);
        values.put("TRANSACTION_STATUS", transactionStatus);
        values.put("TRANSACTION_ID", Long.toString(transactionId));

        Long result = db.insert("TRANSACTIONS", null, values);

        if(result == -1) {

            return false;

        } else {

            return true;

        }

    }

    public Cursor transactToData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMERS where ID = " + id, null);
        return cursor;

    }

    public Cursor showCustomers() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMERS", null);
        return cursor;

    }

    public Cursor showCustomerData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMERS WHERE ID = " + String.valueOf(id), null);
        return cursor;

    }

    public Cursor removeHolder(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMERS " +
                "EXCEPT SELECT * FROM CUSTOMERS " +
                "WHERE ID = " + String.valueOf(id), null);
        return cursor;

    }

}
