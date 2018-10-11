package com.example.invoiceger;

import android.app.AlertDiainvoice;
import android.content.Context;
import android.content.DiainvoiceInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        if(isOnline()) {
            new CountDownTimer(2500, 2500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    Intent intent = new Intent(SplashActivity.this, invoiceListActivity.class);
                    startActivity(intent);
                    finish();

                }
            }.start();


        }
        else {
            AlertDiainvoice.Builder builder1 = new AlertDiainvoice.Builder(this);
            builder1.setMessage("Network Not Available Please Check Your network and Try Again");
            builder1.setCancelable(false);
            builder1.setPositiveButton("OK", new DiainvoiceInterface.OnClickListener() {
                public void onClick(DiainvoiceInterface diainvoice, int id) {
                    diainvoice.cancel();
                    finish();
                }
            });
            AlertDiainvoice alert11 = builder1.create();
            alert11.show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    @Override
    protected void onResume() {
        super.onResume();
     //   Activityinvoicein.imagePathURL=new SharedPreferencesUtils(MainActivity.this).getImage();


    }


}
