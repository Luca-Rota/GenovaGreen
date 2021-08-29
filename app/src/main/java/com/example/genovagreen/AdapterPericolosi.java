package com.example.genovagreen;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterPericolosi  extends RecyclerView.Adapter<AdapterPericolosi.ViewHolder>{

    private ArrayList<ItemPericolosi> items;

    public AdapterPericolosi(ArrayList<ItemPericolosi> items){
        this.items = items;
    }

    @NonNull
    @Override
    public AdapterPericolosi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_pericolosi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPericolosi.ViewHolder holder, int position) {
        ItemPericolosi item = items.get(position);
        holder.title.setText(item.getTitles());
        holder.desc.setText(item.getDesc());
        boolean isExpanded =items.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView desc, title;
        ConstraintLayout expandableLayout;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            desc = itemView.findViewById(R.id.Descrizione);
            title = itemView.findViewById(R.id.title);
            expandableLayout=itemView.findViewById(R.id.expandableLayout);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemPericolosi itemPericolosi=items.get(getAdapterPosition());
                    itemPericolosi.setExpanded(!itemPericolosi.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
