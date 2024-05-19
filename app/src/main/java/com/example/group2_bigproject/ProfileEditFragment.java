package com.example.group2_bigproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class ProfileEditFragment extends DialogFragment {
    private SharedPreferencesHelper spHelper;
    private FirebaseHelper fbHelper;
    private String userID;

    private EditText profileEditFragmentNameInput;
    private EditText profileEditFragmentGenderInput;
    private EditText profileEditFragmentBirthdayInput;
    private EditText profileEditFragmentPhoneInput;
    private EditText profileEditFragmentAddressInput;
    private EditText profileEditFragmentHeightInput;
    private EditText profileEditFragmentWeightInput;
    private Button profileEditFragmentCancelButton;
    private Button profileEditFragmentConfirmButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        spHelper = new SharedPreferencesHelper(getActivity());
        userID = spHelper.getSessionID();
        fbHelper = new FirebaseHelper(getActivity());
        profileEditFragmentNameInput = view.findViewById(R.id.profileEditFragmentNameInput);
        profileEditFragmentGenderInput = view.findViewById(R.id.profileEditFragmentGenderInput);
        profileEditFragmentBirthdayInput = view.findViewById(R.id.profileEditFragmentBirthdayInput);
        profileEditFragmentPhoneInput = view.findViewById(R.id.profileEditFragmentPhoneInput);
        profileEditFragmentAddressInput = view.findViewById(R.id.profileEditFragmentAddressInput);
        profileEditFragmentHeightInput = view.findViewById(R.id.profileEditFragmentHeightInput);
        profileEditFragmentWeightInput = view.findViewById(R.id.profileEditFragmentWeightInput);
        profileEditFragmentCancelButton = view.findViewById(R.id.profileEditFragmentCancelButton);
        profileEditFragmentConfirmButton = view.findViewById(R.id.profileEditFragmentConfirmButton);

        profileEditFragmentConfirmButton.setOnClickListener(v -> {
            String name = profileEditFragmentNameInput.getText().toString();
            String gender = profileEditFragmentGenderInput.getText().toString();
            String birthday = profileEditFragmentBirthdayInput.getText().toString();
            String phone = profileEditFragmentPhoneInput.getText().toString();
            String address = profileEditFragmentAddressInput.getText().toString();
            String height = profileEditFragmentHeightInput.getText().toString();
            String weight = profileEditFragmentWeightInput.getText().toString();
            fbHelper.updateUser(name, gender, birthday, phone, address, height, weight, userID);
            dismiss();
        });

        profileEditFragmentCancelButton.setOnClickListener(v -> {
            dismiss();
        });
        return view;
    }
}
