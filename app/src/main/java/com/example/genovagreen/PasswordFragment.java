package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class PasswordFragment extends Fragment {
    private Button NewPass;
    private EditText Email;
    String codice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_password, container, false);
        Email=view.findViewById(R.id.EmailAddress1);
        NewPass=view.findViewById(R.id.newpass);
        NewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to=Email.getText().toString();
                Intent sendemail = new Intent(Intent.ACTION_SEND);
                sendemail.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                sendemail.putExtra(Intent.EXTRA_SUBJECT, "modifica password GenovaGreen");
                sendemail.putExtra(Intent.EXTRA_TEXT, "Il codice per cambiare password è "+codice);
            }
        });

        return view;
    }
}