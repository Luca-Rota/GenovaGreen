package com.example.genovagreen;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;

public class Butto2Fragment extends Fragment {



    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getSupportFragmentManager mi da errore

            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Butto2Fragment()).commit();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.fragment_butto2, container,  false);

        return view;
    }
}