package com.example.genovagreen;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;

public class Butto2Fragment extends Fragment {
    Connection conn=MainActivity.StabilireConnessione();
    SearchView trova=ButtoFragment.ricerca;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_butto2, container,  false);
    }
}