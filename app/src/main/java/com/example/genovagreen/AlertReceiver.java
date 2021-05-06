package com.example.genovagreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

public class AlertReceiver extends BroadcastReceiver {
   // DatabaseReference ref;

    @Override
    public void onReceive(Context context, Intent intent) {
        /*ref=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.getValue(Spedizione.class).getIdNotifica()==(inserisci qui l'id che hai preso dell'extra)){
    */


                        NotificationHelper notificationHelper = new NotificationHelper(context);
                        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                        notificationHelper.getManager().notify(Spedizioni5.idNotify, nb.build());


            /*}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

}


