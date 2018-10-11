package com.example.invoicelogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.invoicelogger.database.LogDbSchema.SettingsTable;
import com.example.invoicelogger.database.LogDbSchema.invoiceTable;

public class LogBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "invoiceLogger.db";

    public LogBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + invoiceTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                invoiceTable.Cols.UUID + ", " +
                invoiceTable.Cols.TITLE + ", " +
                invoiceTable.Cols.DESTINATION + ", " +
                invoiceTable.Cols.DATE + ", " +
                invoiceTable.Cols.TYPE + ", " +
                invoiceTable.Cols.COMMENT + ", " +
                invoiceTable.Cols.LAT + ", " +
                invoiceTable.Cols.LON + ", " +
                invoiceTable.Cols.DURATION +
                ")"
        );
        db.execSQL("create table " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.ID + ", " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.GENDER + ", " +
                SettingsTable.Cols.COMMENT +
                ")"
        );
        // only one record/ only updates after
        db.execSQL("insert into " + SettingsTable.NAME + " values ('12345678', 'Abc', 'Abc@mail.com', '1', 'invoice Logger App.')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
