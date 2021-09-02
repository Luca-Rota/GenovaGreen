package com.example.genovagreen;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterPericolosi  extends RecyclerView.Adapter<AdapterPericolosi.ViewHolder>{

    private ArrayList<ItemPericolosi> items;
    private String apri;
    private String titles;

    public AdapterPericolosi(ArrayList<ItemPericolosi> items, String apri){
        this.items = items;
        this.apri=apri;
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
        titles=item.getTitles().trim();
        Log.i("prova", titles+" "+apri);
        holder.desc.setText(item.getDesc());
        boolean isExpanded =items.get(position).isExpanded();
        switch(apri){
            case "carta":
                if(titles.equals("Carta e cartone")||titles.equals("Paper and cardboard"))
                    isExpanded=true;
                break;
            case "plastica":
                if(titles.equals("Plastica")||titles.equals("Metalli")||titles.equals("Plastic")||titles.equals("Metals"))
                    isExpanded=true;
                break;
            case "umido":
                if(titles.equals("Umido")||titles.equals("Horganic wet"))
                    isExpanded=true;
                break;
            case "abiti":
                if(titles.equals("Abiti usati")||titles.equals("Used clothes"))
                    isExpanded=true;
                break;
            case "vetro":
                if(titles.equals("Vetro")||titles.equals("Glass"))
                    isExpanded=true;
                break;
            case "niente":
                break;
            default:
                break;
        }
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView desc, title;
        ConstraintLayout expandableLayout;
        ConstraintLayout constraintLayout;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            desc = itemView.findViewById(R.id.Descrizione);
            title = itemView.findViewById(R.id.title);
            constraintLayout=itemView.findViewById(R.id.constraintLayout);
            expandableLayout=itemView.findViewById(R.id.expandableLayout);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
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
