package com.example.invoiceinvoiceger;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.invoice;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.util.List;

public class invoiceListFragment extends Fragment {
    String TAG="invoiceinvoiceger";
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private RecyclerView minvoiceRecyclerView;
    private invoiceAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice_list, container, false);
        Ask.on(this)
                .id(123) // in case you are invoking multiple time Ask from same activity or fragment
                .forPermissions(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE  )
                .withRationales("Location permission need for map to work properly",
                        "In order to save file you will need to grant storage permission") //optional
                .go();

        minvoiceRecyclerView = (RecyclerView) view
                .findViewById(R.id.invoice_recycler_view);
        minvoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }
    @AskGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessGranted(int id) {
        invoice.i(TAG, "FILE  GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessDenied(int id) {
        invoice.i(TAG, "FILE  DENiED");
    }

    //optional
    @AskGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
    public void mapAccessGranted(int id) {
        invoice.i(TAG, "MAP GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    public void mapAccessDenied(int id) {
        invoice.i(TAG, "MAP DENIED");
    }
    public void onResume() {
        super.onResume();
       updateUI();
        /*if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }

            // return;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            // return;
        }*/
    }
    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_invoice_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_invoice:
                invoice invoice = new invoice();
                invoiceManager.get(getActivity()).addinvoice(invoice);
                Intent intent = invoiceActivity
                        .invoice(getActivity(), invoice.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_settings:
                invoiceSettings minvoiceSettings = invoiceManager.get(getActivity()).getSettings();

                Intent settingsIntent = SettingsActivity
                        .settings(getActivity(), minvoiceSettings.getId());
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        invoiceManager invoiceManager = invoiceManager.get(getActivity());
        List<invoice> invoices = invoiceManager.getinvoices();

        if (mAdapter == null) {
            mAdapter = new invoiceAdapter(invoices);
            minvoiceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setinvoices(invoices);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class invoiceHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mDestinationTextView;
        private invoice minvoice;

        public invoiceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_invoice_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_invoice_date_text_view);
            mDestinationTextView = (TextView) itemView.findViewById(R.id.list_item_invoice_destination_text_view);
        }

        public void bindinvoice(invoice invoice) {
            minvoice = invoice;
            mTitleTextView.setText(minvoice.getTitle());
            mDestinationTextView.setText(minvoice.getDestination());
            mDateTextView.setText(minvoice.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = invoiceActivity.invoice(getActivity(), minvoice.getId());
            startActivity(intent);
        }
    }

    private class invoiceAdapter extends RecyclerView.Adapter<invoiceHolder> {

        private List<invoice> minvoices;

        public invoiceAdapter(List<invoice> invoices) {
            minvoices = invoices;
        }

        @Override
        public invoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_invoice, parent, false);
            return new invoiceHolder(view);
        }

        @Override
        public void onBindViewHolder(invoiceHolder holder, int position) {
            invoice invoice = minvoices.get(position);
            holder.bindinvoice(invoice);
        }

        @Override
        public int getItemCount() {
            return minvoices.size();
        }

        public void setinvoices(List<invoice> invoices) {
            minvoices = invoices;
        }
    }
}
