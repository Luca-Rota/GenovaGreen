package com.example.genovagreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends FirebaseRecyclerAdapter<Oggetto,AdapterClass.myviewholder>
{

    public AdapterClass(@NonNull FirebaseRecyclerOptions<Oggetto> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final Oggetto model) {
        holder.rifiutotext.setText(model.getRifiuto());
        holder.cassonettotext.setText(model.getCassonetto());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView rifiutotext, cassonettotext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            ;
            rifiutotext=itemView.findViewById(R.id.rifiutoId);
            cassonettotext=itemView.findViewById(R.id.cassonettoId);

        }
    }

}

