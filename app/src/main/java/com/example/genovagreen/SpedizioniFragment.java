package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SpedizioniFragment extends Fragment {
    private Button loginbt;
    private TextView registrati;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_spedizioni, container, false);
        registrati=view.findViewById(R.id.registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Registrazione.class);
                startActivity(intent);
            }
        });
        loginbt=view.findViewById(R.id.loginbutton);
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });

        return view;
    }
}