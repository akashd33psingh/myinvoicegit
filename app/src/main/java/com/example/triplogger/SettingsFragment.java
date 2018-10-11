package com.example.invoiceinvoiceger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsFragment extends Fragment {

    private static final String ARG_SETTINGS_ID = "settings_id";
    private invoiceSettings minvoiceSettings;
    private EditText mIdField;
    private EditText mNameField;
    private EditText mEmailField;
    private Spinner mGenderField;
    private EditText mCommentField;


    public static SettingsFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SETTINGS_ID, id);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        minvoiceSettings = invoiceManager.get(getActivity()).getSettings();
    }

    @Override
    public void onPause() {
        super.onPause();
        invoiceManager.get(getActivity())
                .updateSettings(minvoiceSettings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mIdField = (EditText) v.findViewById(R.id.settings_id);
        mIdField.setText(minvoiceSettings.getId());
        mIdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoiceSettings.setId(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNameField = (EditText) v.findViewById(R.id.settings_name);
        mNameField.setText(minvoiceSettings.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoiceSettings.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailField = (EditText) v.findViewById(R.id.settings_email);
        mEmailField.setText(minvoiceSettings.getEmail());
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoiceSettings.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCommentField = (EditText) v.findViewById(R.id.settings_comments);
        mCommentField.setText(minvoiceSettings.getComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minvoiceSettings.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGenderField = (Spinner) v.findViewById(R.id.invoice_type);
        mGenderField.setSelection(Integer.parseInt(minvoiceSettings.getGender()));

        mGenderField.setOnItemSelectedListener(new invoiceTypeOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                minvoiceSettings.setGender(Integer.toString(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

    }

}
