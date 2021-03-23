package com.example.genovagreen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ButtoFragment extends Fragment {
    private AdapterClass adapter;
    private List<Oggetto> list = new ArrayList<>();
    private DatabaseReference ref;
    private View view=null;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_butto, container,  false);
        button=view.findViewById(R.id.prova);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref=FirebaseDatabase.getInstance().getReference("Prova");
                ref.push().setValue("prova");
                Toast.makeText(getContext(),"ok",Toast.LENGTH_LONG).show();
            }
        });
        fillList();
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillList();
    }

    public void fillList() {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        ref=db.getReference("DoveLoButto");
        ref.addValueEventListener(new ValueEventListener() {
            //Non entra qua
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "fillList", Toast.LENGTH_LONG).show();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String rifiuto = ds.child("rifiuto").getValue(String.class);
                    String cassonetto = ds.child("cassonetto").getValue(String.class);
                    Oggetto ogg = new Oggetto(rifiuto, cassonetto);
                    list.add(ogg);

                }
                RecyclerView recyclerView = view.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                adapter = new AdapterClass(list);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getContext(), "adapter settato", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "errore db", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        search();
    }

    public boolean search() {
        SearchView searchView=view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


}