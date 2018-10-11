package com.example.invoiceinvoiceger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.invoice;

import com.example.invoiceinvoiceger.database.invoiceBaseHelper;
import com.example.invoiceinvoiceger.database.invoiceCursorWrapper;
import com.example.invoiceinvoiceger.database.invoiceDbSchema.SettingsTable;
import com.example.invoiceinvoiceger.database.invoiceDbSchema.invoiceTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class invoiceManager {
    private static invoiceManager sinvoiceManager;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static invoiceManager get(Context context) {
        if (sinvoiceManager == null) {
            sinvoiceManager = new invoiceManager(context);
        }
        return sinvoiceManager;
    }

    private invoiceManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new invoiceBaseHelper(mContext)
                .getWritableDatabase();
    }

    // working with invoices
    private static ContentValues getinvoiceValues(invoice invoice) {
        ContentValues values = new ContentValues();
        values.put(invoiceTable.Cols.UUID, invoice.getId().toString());
        values.put(invoiceTable.Cols.TITLE, invoice.getTitle());
        values.put(invoiceTable.Cols.DESTINATION, invoice.getDestination());
        values.put(invoiceTable.Cols.DATE, invoice.getDate().getTime());
        values.put(invoiceTable.Cols.DURATION, invoice.getDuration());
        values.put(invoiceTable.Cols.TYPE, invoice.getType());
        values.put(invoiceTable.Cols.COMMENT, invoice.getComment());
        values.put(invoiceTable.Cols.LAT, invoice.getLatitude());
        values.put(invoiceTable.Cols.LON, invoice.getLongtitude());

        return values;
    }

    public void addinvoice(invoice c) {
        ContentValues values = getinvoiceValues(c);

        mDatabase.insert(invoiceTable.NAME, null, values);
    }

    public void updateinvoice(invoice invoice) {
        String uuidString = invoice.getId().toString();
        ContentValues values = getinvoiceValues(invoice);
        invoice.d("UPDATE", "" + values.toString());
        mDatabase.update(invoiceTable.NAME, values,
                invoiceTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteinvoice(invoice invoice) {
        String uuidString = invoice.getId().toString();
        ContentValues values = getinvoiceValues(invoice);

        mDatabase.delete(invoiceTable.NAME, invoiceTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private invoiceCursorWrapper queryinvoices(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                invoiceTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new invoiceCursorWrapper(cursor);
    }


    public List<invoice> getinvoices() {
        List<invoice> invoices = new ArrayList<>();

        invoiceCursorWrapper cursor = queryinvoices(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            invoices.add(cursor.getinvoice());
            cursor.moveToNext();
        }
        cursor.close();

        return invoices;
    }

    public invoice getinvoice(UUID id) {
        invoiceCursorWrapper cursor = queryinvoices(
                invoiceTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getinvoice();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(invoice invoice) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, invoice.getPhotoFileName());
    }

    // working with settings - only one record

    private ContentValues getSettingsValues(invoiceSettings invoiceSettings) {
        ContentValues values = new ContentValues();

        values.put(SettingsTable.Cols.ID, invoiceSettings.getId());
        values.put(SettingsTable.Cols.NAME, invoiceSettings.getName());
        values.put(SettingsTable.Cols.EMAIL, invoiceSettings.getEmail());
        values.put(SettingsTable.Cols.GENDER, invoiceSettings.getGender());
        values.put(SettingsTable.Cols.COMMENT, invoiceSettings.getComment());

        return values;
    }

    public void updateSettings(invoiceSettings invoiceSettings) {
        ContentValues values = getSettingsValues(invoiceSettings);
        String id = invoiceSettings.getId();
        mDatabase.update(SettingsTable.NAME, values,
                SettingsTable.Cols.ID + " = ?",
                new String[]{id});
    }

    private invoiceCursorWrapper querySettings() {
        Cursor cursor = mDatabase.query(
                SettingsTable.NAME,
                null, // Columns - null selects all columns
                null,
                null,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new invoiceCursorWrapper(cursor);
    }

    public invoiceSettings getSettings() {
        invoiceCursorWrapper cursor = querySettings();

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSettings();
        } finally {
            cursor.close();
        }
    }


}