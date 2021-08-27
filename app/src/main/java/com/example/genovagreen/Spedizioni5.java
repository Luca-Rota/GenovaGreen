package com.example.genovagreen;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Spedizioni5 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private String luogo, ora, data, organizzatore, partecipanti, descrizione, id;
    private TextView luogo5,ora5,data5,organizzatore5,partecipanti5,descrizione5,username, id2;
    private Button annulla,partecipa;
    private String id1;
    public int idNotify;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni5);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        View view=navigationView.getHeaderView(0);
        username = view.findViewById(R.id.nomeutente);
        id2 = view.findViewById(R.id.id);
        CommonFunctions.setUsername(username, id2, navigationView, user);

        final String email=user.getEmail();

        luogo = getIntent().getStringExtra("luogo");
        ora = getIntent().getStringExtra("ora");
        data = getIntent().getStringExtra("data");
        organizzatore = getIntent().getStringExtra("organizzatore");
        partecipanti = getIntent().getStringExtra("partecipanti");
        descrizione = getIntent().getStringExtra("descrizione");
        idNotify = getIntent().getIntExtra("idNotifica",0);
        luogo5=findViewById(R.id.placeholder);
        SpannableString content = new SpannableString(luogo);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        luogo5.setText(content);
        ora5=findViewById(R.id.ora5);
        ora5.setText(ora);
        data5=findViewById(R.id.email_utente);
        data5.setText(data);
        organizzatore5=findViewById(R.id.organizzatore5);
        organizzatore5.setText(organizzatore);
        partecipanti5=findViewById(R.id.partecipanti5);
        partecipanti5.setText(String.valueOf(partecipanti));
        descrizione5=findViewById(R.id.descrizione5);
        descrizione5.setText(descrizione);
        annulla=findViewById(R.id.annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Spedizione sped=new Spedizione(luogo, descrizione, organizzatore, data, ora, partecipanti, idNotify);
        partecipa = findViewById(R.id.partecipa);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                             if(sped.getOrganizzatore().trim().equals(ds.getValue(Spedizione.class).getOrganizzatore().trim())&&
                                     sped.getData().trim().equals(ds.getValue(Spedizione.class).getData().trim())&&
                                             sped.getLuogo().trim().equals(ds.getValue(Spedizione.class).getLuogo().trim())&&
                                                     sped.getOra().trim().equals(ds.getValue(Spedizione.class).getOra().trim())) {
                                 id = ds.getKey();
                                 DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SpedCreate");
                                     ref.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 for (DataSnapshot ds : snapshot.getChildren()) {
                                                     id1 = ds.getValue(MySped.class).getId().trim();
                                                     String email1 = ds.getValue(MySped.class).getEmail().trim();
                                                     if (id.equals(id1) && email.equals(email1)) {
                                                         final String idCr=ds.getKey().trim();
                                                         partecipa.setText(R.string.elimina);
                                                         partecipa.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                             AlertDialog mBuilder= new AlertDialog.Builder(Spedizioni5.this)
                                                                 .setMessage(R.string.popup_sped2)
                                                                 .setIcon(android.R.drawable.ic_dialog_alert)
                                                                 .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int whichButton) {
                                                                     ref.child(idCr).removeValue();
                                                                     DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Spedizioni");
                                                                     ref1.child(id).removeValue();
                                                                     DatabaseReference ref3=FirebaseDatabase.getInstance().getReference().child("SpedPart");
                                                                     ref3.addValueEventListener(new ValueEventListener() {
                                                                         @Override
                                                                         public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                                             for(DataSnapshot ds1:snapshot1.getChildren()){
                                                                                 if(ds1.getValue(MySped.class).getId().equals(id)){
                                                                                     String idPart=ds1.getKey();
                                                                                     ref3.child(idPart).removeValue();
                                                                                 }
                                                                             }
                                                                         }
                                                                         @Override
                                                                         public void onCancelled(@NonNull DatabaseError error) {
                                                                         }
                                                                     });
                                                                     startActivity(new Intent(Spedizioni5.this, Spedizioni3.class));
                                                                 }})
                                                                 .setNegativeButton(R.string.no, null).show();
                                                             }
                                                         });
                                                     }
                                                 }
                                             }
                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {
                                             Toast.makeText(Spedizioni5.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                                         }
                                     });
                             }
                                 DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("SpedPart");

                                     ref1.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 for (DataSnapshot ds : snapshot.getChildren()) {
                                                     id1=ds.getValue(MySped.class).getId().trim();
                                                     String email1=ds.getValue(MySped.class).getEmail().trim();
                                                     if(id.equals(id1)&&email.equals(email1)){
                                                         final String idPart=ds.getKey().trim();
                                                         partecipa.setText(R.string.non_partecipare);
                                                         partecipa.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                                 AlertDialog mBuilder= new AlertDialog.Builder(Spedizioni5.this)
                                                                     .setMessage(R.string.popup_sped1)
                                                                     .setIcon(android.R.drawable.ic_dialog_alert)
                                                                     .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog, int whichButton) {
                                                                         DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("SpedPart").child(idPart);
                                                                         ref.removeValue();
                                                                         sped.setPartecipanti(String.valueOf(Integer.parseInt(sped.getPartecipanti())-1));
                                                                         DatabaseReference update2=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
                                                                         update2.child(id).setValue(sped);
                                                                         cancelAlarm();
                                                                         startActivity(new Intent(Spedizioni5.this, Spedizioni3.class));
                                                                     }})
                                                                     .setNegativeButton(R.string.no, null).show();
                                                             }
                                                         });
                                                     }
                                                 }
                                             }
                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {
                                             Toast.makeText(Spedizioni5.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                                         }
                                     });
                                 }
                                 partecipa.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         DatabaseReference update2=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
                                         int newPartecipanti=new Integer(partecipanti)+1;
                                         String part=String.valueOf(newPartecipanti);
                                         Spedizione updateSped=new Spedizione(luogo,descrizione,organizzatore,data, ora, part, idNotify);
                                         update2.child(id).setValue(updateSped);
                                         DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("SpedPart").push();
                                         MySped users=new MySped(id,email);
                                         ref.setValue(users);
                                         createNotificationChannel();
                                         startAlarm(setAlarm());
                                         startActivity(new Intent(Spedizioni5.this, Spedizioni3.class));
                                     }
                                 });
                             }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Spedizioni5.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                }
            });

        TextView mappa = findViewById(R.id.placeholder);
        mappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+luogo));
                startActivity(intent);
            }
        });
    }

    private Calendar setAlarm() {
        String[] timeL = ora.split(":");
        String[] dateL = data.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeL[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(timeL[1]));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateL[0]));
        c.set(Calendar.MONTH, Integer.parseInt(dateL[1])-1);
        c.set(Calendar.YEAR, Integer.parseInt(dateL[2]));
        return c;
    }

    private void startAlarm(Calendar c) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        Toast.makeText(this, ""+idNotify, Toast.LENGTH_SHORT).show();
        intent.putExtra("idNotifica", idNotify);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Notifica messa",Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm() {
        /*
        TODO
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarmManager==null){
            alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Notifica tolta",Toast.LENGTH_SHORT).show();
         */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        View v=new View(this);
        CommonFunctions.onNavigationItemSelected(item,v,user, drawer);
        return true;
    }

    public void ClickLogo(View view){
        CommonFunctions.closeDrawer(drawer);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            CharSequence name = Integer.toString(idNotify);
            String id = Integer.toString(idNotify);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.i("prova","Creato canale: "+channel);
        }
    }
}