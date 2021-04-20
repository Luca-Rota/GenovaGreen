package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Spedizioni5 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private String luogo, ora, data, organizzatore, partecipanti, descrizione, id;
    private TextView luogo5,ora5,data5,organizzatore5,partecipanti5,descrizione5,username;
    private Button annulla,partecipa;
    private String id1;
    private boolean bottone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni5);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Usernames");
        if(user!=null) {
            View view=navigationView.getHeaderView(0);
            username = view.findViewById(R.id.nomeutente);
            username.setVisibility(View.VISIBLE);
            final String email = user.getEmail().trim();
            if (ref != null) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User ogg = ds.getValue(User.class);
                                String email2 = ogg.getEmail().trim();
                                String nomeutente = ogg.getUsername();
                                if (email.equals(email2)) {
                                    username.setText(nomeutente);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Spedizioni5.this, "errore db", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        final String email=user.getEmail();

        luogo = getIntent().getStringExtra("luogo");
        ora = getIntent().getStringExtra("ora");
        data = getIntent().getStringExtra("data");
        organizzatore = getIntent().getStringExtra("organizzatore");
        partecipanti = getIntent().getStringExtra("partecipanti");
        descrizione = getIntent().getStringExtra("descrizione");
        luogo5=findViewById(R.id.luogo5);
        luogo5.setText(luogo);
        ora5=findViewById(R.id.ora5);
        ora5.setText(ora);
        data5=findViewById(R.id.data5);
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
        final Spedizione sped=new Spedizione(luogo, descrizione, organizzatore, data, ora, partecipanti);
        partecipa = findViewById(R.id.partecipa);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
        if(reference!=null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for (DataSnapshot ds : snapshot.getChildren()) {
                             if(sped.getOrganizzatore().trim().equals(ds.getValue(Spedizione.class).getOrganizzatore().trim())&&
                                     sped.getData().trim().equals(ds.getValue(Spedizione.class).getData().trim())&&
                                             sped.getLuogo().trim().equals(ds.getValue(Spedizione.class).getLuogo().trim())&&
                                                     sped.getOra().trim().equals(ds.getValue(Spedizione.class).getOra().trim())) {
                                 id = ds.getKey();
                                 final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SpedCreate");
                                 if (ref != null) {
                                     ref.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                             if (snapshot.exists()) {
                                                 for (DataSnapshot ds : snapshot.getChildren()) {
                                                     id1 = ds.getValue(SpedPersonali.class).getId().trim();
                                                     String email1 = ds.getValue(SpedPersonali.class).getEmail().trim();
                                                     if (id.equals(id1) && email.equals(email1)) {
                                                         final String idCr=ds.getKey().trim();
                                                         partecipa.setText(" Elimina ");
                                                         partecipa.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                                 ref.child(idCr).removeValue();
                                                                 DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Spedizioni");
                                                                 ref1.child(id).removeValue();
                                                                 startActivity(new Intent(Spedizioni5.this, Spedizioni3.class));
                                                             }
                                                         });
                                                     }
                                                 }
                                             }
                                         }
                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {
                                             Toast.makeText(Spedizioni5.this, "errore db", Toast.LENGTH_LONG).show();
                                         }
                                     });
                                 }
                             }
                                 DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("SpedPart");
                                 if(ref1!=null) {
                                     ref1.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                             if(snapshot.exists()){
                                                 for (DataSnapshot ds : snapshot.getChildren()) {
                                                     id1=ds.getValue(SpedPersonali.class).getId().trim();
                                                     String email1=ds.getValue(SpedPersonali.class).getEmail().trim();
                                                     if(id.equals(id1)&&email.equals(email1)){
                                                         final String idPart=ds.getKey().trim();
                                                         partecipa.setText("Non partecipare");
                                                         partecipa.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                                 DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("SpedPart").child(idPart);
                                                                 ref.removeValue();
                                                                 sped.setPartecipanti(String.valueOf(Integer.parseInt(sped.getPartecipanti())-1));
                                                                 DatabaseReference update2=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
                                                                 update2.child(id).setValue(sped);
                                                                 startActivity(new Intent(Spedizioni5.this, Spedizioni3.class));
                                                             }
                                                         });
                                                     }
                                                 }
                                             }
                                         }
                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {
                                             Toast.makeText(Spedizioni5.this, "errore db", Toast.LENGTH_LONG).show();
                                         }
                                     });
                                 }
                                 partecipa.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         DatabaseReference update2=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
                                         int newPartecipanti=new Integer(partecipanti)+1;
                                         String part=String.valueOf(newPartecipanti);
                                         Spedizione updateSped=new Spedizione(luogo,descrizione,organizzatore,data, ora, part);
                                         update2.child(id).setValue(updateSped);
                                         DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("SpedPart").push();
                                         SpedPersonali users=new SpedPersonali(id,email);
                                         ref.setValue(users);
                                         startActivity(new Intent(Spedizioni5.this, Spedizioni3.class));
                                     }
                                 });
                             }
                        }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Spedizioni5.this, "errore db", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Spedizioni5.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Spedizioni5.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Spedizioni5.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user!=null && user.isEmailVerified()) {
                    startActivity(new Intent(Spedizioni5.this, Spedizioni2.class));
                }else{
                    startActivity(new Intent(Spedizioni5.this, Spedizioni.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Spedizioni5.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Spedizioni5.this, Informazioni.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}