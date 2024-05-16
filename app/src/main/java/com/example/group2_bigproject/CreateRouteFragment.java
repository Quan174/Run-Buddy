package com.example.group2_bigproject;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class CreateRouteFragment extends DialogFragment {

    private EditText createRouteFragmentRouteNameInput;
    private EditText createRouteFragmentDescriptionInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_route, container, false);
        createRouteFragmentRouteNameInput = view.findViewById(R.id.createRouteFragmentRouteNameInput);
        createRouteFragmentDescriptionInput = view.findViewById(R.id.createRouteFragmentDescriptionInput);
        Button createRouteFragmentConfirmButton = view.findViewById(R.id.createRouteFragmentConfirmButton);
        createRouteFragmentConfirmButton.setOnClickListener(v -> {
            String username = createRouteFragmentRouteNameInput.getText().toString();
            String password = createRouteFragmentDescriptionInput.getText().toString();
            // Thực hiện kiểm tra đăng nhập ở đây
        });
        return view;
    }
}
