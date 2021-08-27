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

import ru.embersoft.expandabletextview.ExpandableTextView;

public class AdapterPericolosi  extends RecyclerView.Adapter<AdapterPericolosi.ViewHolder>{

    private ArrayList<ItemPericolosi> items;
    private Context context;

    public AdapterPericolosi(ArrayList<ItemPericolosi> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPericolosi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_pericolosi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPericolosi.ViewHolder holder, int position) {
        final ItemPericolosi item = items.get(position);
        holder.title.setText(item.getTitles());
        holder.desc.setText(item.getDesc());
        holder.desc.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                ItemPericolosi contentItem = items.get(position);
                contentItem.setShrink(isShrink);
                items.set(position, contentItem);
            }
        });
        holder.desc.setText(item.getDesc());
        holder.desc.resetState(item.isShrink());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ExpandableTextView desc;
        TextView title;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            desc = itemView.findViewById(R.id.txtDesc);
            title = itemView.findViewById(R.id.titleText);
        }
    }
}
