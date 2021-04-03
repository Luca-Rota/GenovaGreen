package com.example.genovagreen;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Butto2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butto2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
}

/*private static final String ARG_PARAM1 = "param1";
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