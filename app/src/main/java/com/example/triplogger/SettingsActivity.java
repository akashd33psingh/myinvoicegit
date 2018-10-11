package com.example.invoiceinvoiceger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {
    private ViewPager mPager;
    private invoiceSettings minvoiceSettings;

    public static Intent settings (Context packageContext, String settingsId) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_pager);

        mPager = (ViewPager) findViewById(R.id.mPager);

        minvoiceSettings = invoiceManager.get(this).getSettings();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                String id = minvoiceSettings.getId();
                return SettingsFragment.newInstance(id);
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

    }
}
