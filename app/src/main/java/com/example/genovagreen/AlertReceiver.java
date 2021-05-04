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
    DatabaseReference ref;

    @Override
    public void onReceive(Context context, Intent intent) {
        ref=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.getValue(Spedizione.class).getIdNotifica()==(/*inserisci qui l'id che hai preso dell'extra*/)){
                        NotificationHelper notificationHelper = new NotificationHelper(context);
                        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();/* questa funzione ha anche .getExtra(); per recuperare gli extra
                                                                                                       che avrai messo da NotificationHelper, io non ho ancora capito
                                                                                                       al 100% come funzioni
                                                                                                       il codice che hai scritto ma queste 2 righe andranno prima del ref
                                                                                                       per settare l'id da mettere nell'if
                                                                                                       metterai le 2 righe e poi tipo nb.getExtra("id");*/
                        notificationHelper.getManager().notify(Spedizioni5.idNotify, nb.build());/* non credo che Spedizione5.idNotify vada bene perchè ad app chiusa
                                                                                                    non c'è nessuna Spedizione5 aperta, anche qua mettici l'id dell'extra
                                                                                                    */
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


