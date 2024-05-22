package com.example.group2_bigproject;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class ReportFragment extends DialogFragment {

    private EditText reasonInput;
    private EditText descriptionInput;
    Button confirmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        reasonInput = view.findViewById(R.id.reasonInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        confirmButton = view.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> {
            String routeName = reasonInput.getText().toString();
            String description = descriptionInput.getText().toString();
            //Code
        });
        return view;
    }
}
