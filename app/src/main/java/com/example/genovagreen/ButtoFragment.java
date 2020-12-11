package com.example.genovagreen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ButtoFragment extends Fragment {
    DatabaseReference ref;
    ArrayList<Oggetto> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ref= FirebaseDatabase.getInstance().getReference().child("genovagreen-e27e2-default-rtdb").child("DoveLoButto");
        View view=inflater.inflate(R.layout.fragment_butto, container,  false);
        recyclerView=view.findViewById(R.id.rv);
        recyclerView.hasFixedSize();
        searchView=view.findViewById(R.id.searchView);
    return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<Oggetto>();
        recyclerView.setAdapter(new AdapterClass(list));
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ref!=null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        list=new ArrayList<Oggetto>();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            list.add(ds.getValue(Oggetto.class));
                        }
                        AdapterClass adapterClass= new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return false;
                }
            });
        }
    }
    private void search(String str){
        ArrayList<Oggetto> myList= new ArrayList<Oggetto>();
        for(Oggetto object:list){
            if(object.getRifiuto().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        AdapterClass adapterClass=new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);
    }

}