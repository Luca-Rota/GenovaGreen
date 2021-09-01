package com.example.genovagreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.myviewholder>  {
    ArrayList<Oggetto> list;
    private TextView txt;
    Activity activity;
    public AdapterClass(ArrayList<Oggetto> list){
        this.list=list;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, final int position) {
        holder.rifiutotext.setText(list.get(position).getRifiuto());
        holder.cassonettotext.setText(list.get(position).getCassonetto());
        String cassonetto=list.get(position).getCassonetto();
        switch(cassonetto) {
            case "Cassonetto dell'indifferenziata":
                holder.imgrec.setImageResource(R.drawable.mixed);
                break;
            case "Cassonetto della carta":
                holder.imgrec.setImageResource(R.drawable.carta);
                break;
            case "Cassonetto della plastica":
                holder.imgrec.setImageResource(R.drawable.plastica);
                break;
            case "Cassonetto del vetro":
                holder.imgrec.setImageResource(R.drawable.vetro);
                break;
            case "Isola Ecologica":
                holder.imgrec.setImageResource(R.drawable.isola);
                break;
            case "Cassonetto dell'umido":
                holder.imgrec.setImageResource(R.drawable.umido);
                break;
            case "Cassonetto degli abiti":
                holder.imgrec.setImageResource(R.drawable.abiti);
                break;
            default:
                break;
        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView=inflater.inflate(R.layout.popup_butto, null);

                final PopupWindow popupWindow=new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setAnimationStyle(R.style.Animation);
                popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
                TextView rifiutopop=popupView.findViewById(R.id.rifiutopop);
                TextView cassonettopop=popupView.findViewById(R.id.cassonettopop);
                String cassonetto=list.get(position).getCassonetto().trim();
                rifiutopop.setText(list.get(position).getRifiuto());
                cassonettopop.setText(list.get(position).getCassonetto());
                txt = popupView.findViewById(R.id.link_isola);
                ImageView imgpop=popupView.findViewById(R.id.imgpop);
                switch(cassonetto) {
                    case "Cassonetto dell'indifferenziata":
                        imgpop.setImageResource(R.drawable.mixed);
                        break;
                    case "Cassonetto della carta":
                        imgpop.setImageResource(R.drawable.carta);
                        break;
                    case "Cassonetto della plastica":
                        imgpop.setImageResource(R.drawable.plastica);
                        break;
                    case "Cassonetto del vetro":
                        imgpop.setImageResource(R.drawable.vetro);
                        break;
                    case "Cassonetto del umido":
                        imgpop.setImageResource(R.drawable.umido);
                        break;
                    case "Cassonetto degli abiti":
                        imgpop.setImageResource(R.drawable.abiti);
                        break;
                    case "Isola Ecologica":
                        imgpop.setImageResource(R.drawable.isola);
                        txt.setVisibility(View.VISIBLE);
                        txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.getContext().startActivity(new Intent(v.getContext(), Pericolosi3.class));
                            }
                        });
                    default:
                        break;
                }

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }


        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class myviewholder extends RecyclerView.ViewHolder {
        TextView rifiutotext, cassonettotext;
        ImageView imgrec;
        CardView card;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            rifiutotext=itemView.findViewById(R.id.rifiutoId);
            cassonettotext=itemView.findViewById(R.id.cassonettoId);
            imgrec=itemView.findViewById(R.id.imgrec);
            card=itemView.findViewById(R.id.card_holder);
        }
    }

}

