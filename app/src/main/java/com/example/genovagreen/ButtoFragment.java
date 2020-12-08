package com.example.genovagreen;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;

public class ButtoFragment extends Fragment {
    public static SearchView ricerca;
    Button cerca;
    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getSupportFragmentManager mi da errore
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ContentFragment()).commit();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_butto, container,  false);
        ricerca=(SearchView)view.findViewById(R.id.searchView);
        cerca=(Button)view.findViewById(R.id.button);
        cerca.setOnClickListener(buttonListener);
    return view;
    }

    private void FaiRicerca(SearchView ricerca) {
    }
}