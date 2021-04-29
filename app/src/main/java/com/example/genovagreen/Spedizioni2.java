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
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Spedizioni2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private AdapterSpedizioni adapterSpedizioni;
    private ArrayList<Spedizione> list;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private TextView username, nosped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        recyclerView=(RecyclerView)findViewById(R.id.rvsped2);
        recyclerView.setHasFixedSize(true);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        ref=FirebaseDatabase.getInstance().getReference("Usernames");
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
                        Toast.makeText(Spedizioni2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        nosped=findViewById(R.id.nosped);
        button= findViewById(R.id.buttonSpedizioni2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Spedizioni2.this, Spedizioni3.class));
            }
        });

        ref= FirebaseDatabase.getInstance().getReference().child("Spedizioni");
        cancellaDati();
    }

    private void cancellaDati() {
        Calendar calendarNow=Calendar.getInstance();
        int mese=calendarNow.get(Calendar.MONTH);
        mese++;
        calendarNow.set(calendarNow.MONTH, mese);
        ref.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Calendar calendarSped=Calendar.getInstance();
                        String[] data=ds.getValue(Spedizione.class).getData().split("/");
                        String[] tempo=ds.getValue(Spedizione.class).getOra().split(":");
                        calendarSped.set(Calendar.YEAR, Integer.parseInt(data[2]));
                        calendarSped.set(Calendar.MONTH, Integer.parseInt(data[1]));
                        calendarSped.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[0]));
                        int ora=Integer.parseInt(tempo[0]);
                        ora++;
                        calendarSped.set(Calendar.HOUR_OF_DAY, ora);
                        calendarSped.set(Calendar.MINUTE, Integer.parseInt(tempo[1]));
                        calendarSped.set(Calendar.SECOND, 0);
                        if(calendarNow.compareTo(calendarSped)==1){
                            String key=ds.getKey();
                            ref.child(key).removeValue();
                            ref= FirebaseDatabase.getInstance().getReference().child("SpedCreate");
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot ds: snapshot.getChildren()){
                                            if(ds.getValue(MySped.class).getId()==key){
                                                ref.child(ds.getKey()).removeValue();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Spedizioni2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                                }
                            });
                            ref= FirebaseDatabase.getInstance().getReference().child("SpedPart");
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot ds: snapshot.getChildren()){
                                            if(ds.getValue(MySped.class).getId()==key){
                                                ref.child(ds.getKey()).removeValue();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Spedizioni2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Spedizioni2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ref!=null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        list= new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if(!ds.getValue(Spedizione.class).getLuogo().equals("default")) {
                                list.add(ds.getValue(Spedizione.class));
                            }
                        }
                        Collections.sort(list, new Comparator<Spedizione>() {
                            @Override
                            public int compare(Spedizione o1, Spedizione o2) {
                                String[] data1=o1.getData().trim().split("/");
                                String[] data2=o2.getData().trim().split("/");
                                for(int i=2;i>-1;i--){
                                    int temp1=Integer.parseInt(data1[i]);
                                    int temp2=Integer.parseInt(data2[i]);
                                    if(temp1>temp2){
                                        return 1;
                                    }
                                    if(temp1<temp2){
                                        return -1;
                                    }
                                }
                                String[] ora1=o1.getOra().trim().split(":");
                                String[] ora2=o2.getOra().trim().split(":");
                                for(int i=0;i<2;i++){
                                    int temp1=Integer.parseInt(ora1[i]);
                                    int temp2=Integer.parseInt(ora2[i]);
                                    if(temp1>temp2){
                                        return 1;
                                    }
                                    if(temp1<temp2){
                                        return -1;
                                    }
                                }
                                return 0;
                            }
                        });
                        if(list.isEmpty()){
                            recyclerView.setVisibility(View.INVISIBLE);
                            nosped.setVisibility(View.VISIBLE);
                        }else {
                            adapterSpedizioni = new AdapterSpedizioni(list);
                            recyclerView.setAdapter(adapterSpedizioni);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Spedizioni2.this, R.string.errore_db, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Spedizioni2.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Spedizioni2.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Spedizioni2.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user!=null && user.isEmailVerified()) {
                    startActivity(new Intent(Spedizioni2.this, Spedizioni2.class));
                }else{
                    startActivity(new Intent(Spedizioni2.this, Spedizioni.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Spedizioni2.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Spedizioni2.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void ClickLogo(View view){
        closeDrawer(drawer);
    }

    public static void closeDrawer(DrawerLayout dl) {
        if(dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        }

    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Spedizioni2.this,MainActivity.class));
    }
}
