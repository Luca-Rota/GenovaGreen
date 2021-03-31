package com.example.genovagreen;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;

public class Butto2Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String rifiuto, cassonetto, descrizione;
    public Butto2Fragment() {

    }

    public Butto2Fragment(String rifiuto, String cassonetto, String descrizione) {
        this.rifiuto=rifiuto;
        this.cassonetto=cassonetto;
        this.descrizione=descrizione;
    }

    public static Butto2Fragment newInstance(String param1, String param2) {
        Butto2Fragment fragment = new Butto2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_butto2, container, false);

        TextView rifiutoholder=view.findViewById(R.id.rifiutoholder);
        TextView cassonettoholder=view.findViewById(R.id.cassonettoholder);
        TextView descrizioneholder=view.findViewById(R.id.descrizioneholder);


        rifiutoholder.setText(rifiuto);
        cassonettoholder.setText(cassonetto);
        descrizioneholder.setText(descrizione);



        return  view;
    }

}