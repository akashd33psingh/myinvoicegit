package com.example.invoiceinvoiceger;

import android.support.v4.app.Fragment;

public class invoiceListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new invoiceListFragment();
    }
}
