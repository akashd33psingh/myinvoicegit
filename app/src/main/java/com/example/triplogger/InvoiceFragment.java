package com.example.invoiceinvoiceger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.invoice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class invoiceFragment extends Fragment implements android.location.LocationListener{

    private static final String ARG_invoice_ID = "invoice_id";
    private static final String DIAinvoice_DATE = "DiainvoiceDate";
Double latitute,longitute;
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_MAP = 0;

    private LocationManager locationManager;
   // private Location location;

    private invoice minvoice;
    private File mPhotoFile;
    private EditText mTitleField;
    private EditText mDestinationField;
    private EditText mDurationField;
    private Spinner mTypeField;
    private EditText mCommentField;
    private EditText mGpsField;
    private Button mDateButton;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    private ImageView mDeleteButton;
    private ImageView mSaveButton;
    private ImageView mCancelButton;
    private ImageView mGpsButton;

    public static invoiceFragment newInstance(UUID invoiceId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_invoice_ID, invoiceId);

        invoiceFragment fragment = new invoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        UUID invoiceId = (UUID) getArguments().getSerializable(ARG_invoice_ID);
        minvoice = invoiceManager.get(getActivity()).getinvoice(invoiceId);
        mPhotoFile = invoiceManager.get(getActivity()).getPhotoFile(minvoice);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (minvoice.getTitle() != null) {
            invoiceManager.get(getActivity())
                    .updateinvoice(minvoice);
        } else {
            invoiceManager.get(getActivity())
                    .deleteinvoice(minvoice);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_invoice, container, false);
        ViewStub stub = (ViewStub) v.findViewById(R.id.layout_btn);

        if (minvoice.getTitle() != null) {
            stub.setLayoutResource(R.layout.invoice_edit);
            View inflated = stub.inflate();

            mGpsButton = (ImageView) v.findViewById(R.id.invoice_map);
            mGpsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = invoiceMapsActivity
                            .maps(getActivity(), minvoice.getId());

                    startActivityForResult(intent, REQUEST_MAP);
                //    startActivity(intent);
                }
            });

            mDeleteButton = (ImageView) v.findViewById(R.id.invoice_delete);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invoiceManager.get(getActivity())
                            .deleteinvoice(minvoice);
                    getActivity().onBackPressed();
                }
            });
        } else {
            stub.setLayoutResource(R.layout.invoice_add);
            View inflated = stub.inflate();

            mCancelButton= (ImageView) v.findViewById(R.id.invoice_cancel);
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invoiceManager.get(getActivity())
                            .deleteinvoice(minvoice);
                    getActivity().onBackPressed();
                }
            });

            mSaveButton = (ImageView) v.findViewById(R.id.invoice_save);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invoiceManager.get(getActivity())
                            .updateinvoice(minvoice);
                    getActivity().onBackPressed();
                }
            });
        }


        mTitleField = (EditText) v.findViewById(R.id.invoice_title);
        mTitleField.setText(minvoice.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoice.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDestinationField = (EditText) v.findViewById(R.id.invoice_destination);
        mDestinationField.setText(minvoice.getDestination());
        mDestinationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoice.setDestination(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCommentField = (EditText) v.findViewById(R.id.invoice_comments);
        mCommentField.setText(minvoice.getComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoice.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGpsField = (EditText) v.findViewById(R.id.invoice_gps);
        if (minvoice.getLatitude() == null){
          /*  locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            minvoice.setLatitude(Double.toString(latitude));
            minvoice.setLongtitude(Double.toString(longitude));
            invoiceManager.get(getActivity())
                    .updateinvoice(minvoice);*/
            try {
                LocationManager lManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                boolean netEnabled = lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (netEnabled) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (android.location.LocationListener) getActivity());
                    Location location = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
               /* latitude = location.getLatitude();
                longitude = location.getLongitude();*/
                        invoice.e("Latitute", location.getLatitude() + "");
                        invoice.e("Longitute", location.getLongitude() + "");
                        latitute = location.getLatitude();
                        longitute = location.getLongitude();
                        minvoice.setLatitude(String.valueOf(latitute));
                        minvoice.setLongtitude(String.valueOf(longitute));
                        invoiceManager.get(getActivity())
                                .updateinvoice(minvoice);

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mGpsField.setText(minvoice.getLatitude() + ";" + minvoice.getLongtitude());
        mGpsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // minvoice.setGps(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTypeField = (Spinner) v.findViewById(R.id.invoice_type);
        if (minvoice.getType() != null)
            mTypeField.setSelection(Integer.parseInt(minvoice.getType()));

        mTypeField.setOnItemSelectedListener(new invoiceTypeOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                minvoice.setType(Integer.toString(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        mDateButton = (Button) v.findViewById(R.id.invoice_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment diainvoice = DatePickerFragment
                        .newInstance(minvoice.getDate());
                diainvoice.setTargetFragment(invoiceFragment.this, REQUEST_DATE);
                diainvoice.show(manager, DIAinvoice_DATE);
            }
        });

        mDurationField = (EditText) v.findViewById(R.id.invoice_duration);
        mDurationField.setText(minvoice.getDuration());
        mDurationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoice.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPhotoButton = (ImageButton) v.findViewById(R.id.invoice_camera);
        PackageManager packageManager = getActivity().getPackageManager();

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
          //  Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + "com.example.invoiceinvoiceger.fileprovider", mPhotoFile);
          //  Uri uri = Uri.fromFile(mPhotoFile);
            Uri U = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.example.invoiceinvoiceger.fileprovider", mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, U);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.invoice_photo);
        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            minvoice.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        } else if (requestCode == REQUEST_MAP) {
            updateGps();
        }
    }

    private void updateGps() {
        invoice.d("TAGGGG", "" + minvoice.getLongtitude());
    }

    private void updateDate() {
        mDateButton.setText(minvoice.getDate().toString());
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = invoiceImageScale.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
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
