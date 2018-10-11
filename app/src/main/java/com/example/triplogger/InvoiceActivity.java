package com.example.invoiceinvoiceger;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class invoiceActivity extends AppCompatActivity implements LocationListener {
    private static final String invoice_ID =
            "com.example.invoiceinvoiceger.invoice_id";

    private ViewPager mPager;
    private List<invoice> minvoicesinvoices;

    public static Intent invoice(Context packageContext, UUID invoiceId) {
        Intent intent = new Intent(packageContext, invoiceActivity.class);
        intent.putExtra(invoice_ID, invoiceId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_pager);

        UUID invoiceId = (UUID) getIntent()
                .getSerializableExtra(invoice_ID);

        mPager = (ViewPager) findViewById(R.id.mPager);

        minvoicesinvoices = invoiceManager.get(this).getinvoices();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                invoice invoice = minvoicesinvoices.get(position);
                return invoiceFragment.newInstance(invoice.getId());
            }

            @Override
            public int getCount() {
                return minvoicesinvoices.size();
            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                invoice invoice = minvoicesinvoices.get(position);
                if (invoice.getTitle() != null) {
                    setTitle(invoice.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        for (int i = 0; i < minvoicesinvoices.size(); i++) {
            if (minvoicesinvoices.get(i).getId().equals(invoiceId)) {
                mPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
