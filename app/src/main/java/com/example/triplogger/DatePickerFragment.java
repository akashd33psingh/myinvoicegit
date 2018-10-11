package com.example.invoiceinvoiceger;

import android.app.Activity;
import android.app.Diainvoice;
import android.content.DiainvoiceInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DiainvoiceFragment;
import android.support.v7.app.AlertDiainvoice;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DiainvoiceFragment {

    public static final String EXTRA_DATE =
            "com.example.invoiceinvoiceger.date";

    private static final String ARG_DATE = "date";

    private DatePicker mDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Diainvoice onCreateDiainvoice(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.diainvoice_date, null);

        mDate = (DatePicker) v.findViewById(R.id.mDate);
        mDate.init(year, month, day, null);

        return new AlertDiainvoice.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DiainvoiceInterface.OnClickListener() {
                            @Override
                            public void onClick(DiainvoiceInterface diainvoice, int which) {
                                int year = mDate.getYear();
                                int month = mDate.getMonth();
                                int day = mDate.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
