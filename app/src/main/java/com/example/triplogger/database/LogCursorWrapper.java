package com.example.invoicelogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.invoicelogger.LogSettings;
import com.example.invoicelogger.invoice;
import com.example.invoicelogger.database.LogDbSchema.SettingsTable;
import com.example.invoicelogger.database.LogDbSchema.invoiceTable;

import java.util.Date;
import java.util.UUID;


public class LogCursorWrapper extends CursorWrapper {
    public LogCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public invoice getinvoice() {
        String uuidString = getString(getColumnIndex(invoiceTable.Cols.UUID));
        String title = getString(getColumnIndex(invoiceTable.Cols.TITLE));
        String destination = getString(getColumnIndex(invoiceTable.Cols.DESTINATION));
        long date = getLong(getColumnIndex(invoiceTable.Cols.DATE));
        String duration = getString(getColumnIndex(invoiceTable.Cols.DURATION));
        String comment = getString(getColumnIndex(invoiceTable.Cols.COMMENT));
        String type = getString(getColumnIndex(invoiceTable.Cols.TYPE));
        String lat = getString(getColumnIndex(invoiceTable.Cols.LAT));
        String lon = getString(getColumnIndex(invoiceTable.Cols.LON));

        invoice invoice = new invoice(UUID.fromString(uuidString));
        invoice.setTitle(title);
        invoice.setDestination(destination);
        invoice.setDate(new Date(date));
        invoice.setDuration(duration);
        invoice.setComment(comment);
        invoice.setType(type);
        invoice.setLatitude(lat);
        invoice.setLongtitude(lon);

        return invoice;
    }

    public LogSettings getSettings() {
        String id = getString(getColumnIndex(SettingsTable.Cols.ID));
        String name = getString(getColumnIndex(SettingsTable.Cols.NAME));
        String email = getString(getColumnIndex(SettingsTable.Cols.EMAIL));
        String gender = getString(getColumnIndex(SettingsTable.Cols.GENDER));
        String comment = getString(getColumnIndex(SettingsTable.Cols.COMMENT));

        LogSettings logSettings = new LogSettings();
        logSettings.setId(id);
        logSettings.setName(name);
        logSettings.setEmail(email);
        logSettings.setGender(gender);
        logSettings.setComment(comment);

        return logSettings;
    }
}
