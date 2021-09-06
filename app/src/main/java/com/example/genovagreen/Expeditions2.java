package com.example.genovagreen;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Expeditions2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private AdapterSpedizioni adapterSpedizioni;
    private ArrayList<Expedition> list;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private TextView username, id, nosped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expeditions2);
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
        View view=navigationView.getHeaderView(0);
        username = view.findViewById(R.id.nomeutente);
        id = view.findViewById(R.id.id);
        CommonFunctions.setUsername(username, id, navigationView, user);

        nosped=findViewById(R.id.nosped);
        button= findViewById(R.id.buttonSpedizioni2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Expeditions2.this, Expeditions3.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Calendar calendarSped=Calendar.getInstance();
                        String[] data=ds.getValue(Expedition.class).getData().split("/");
                        String[] tempo=ds.getValue(Expedition.class).getOra().split(":");
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
                            DatabaseReference ref1= FirebaseDatabase.getInstance().getReference().child("SpedCreate");
                            ref1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                        for(DataSnapshot ds1: snapshot1.getChildren()){
                                            if(ds1.getValue(MyExp.class).getId().equals(key)){
                                                String keyCr=ds1.getKey();
                                                ref1.child(keyCr).removeValue();
                                            }
                                        }
                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Expeditions2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                                }
                            });
                            DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child("SpedPart");
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        for(DataSnapshot ds2: snapshot2.getChildren()){
                                            if(ds2.getValue(MyExp.class).getId().equals(key)){
                                                String keyPart=ds2.getKey();
                                                ref2.child(keyPart).removeValue();
                                            }
                                        }
                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Expeditions2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Expeditions2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list= new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if(!ds.getValue(Expedition.class).getLuogo().equals("default")) {
                                list.add(ds.getValue(Expedition.class));
                            }
                        }
                        Collections.sort(list, new Comparator<Expedition>() {
                            @Override
                            public int compare(Expedition o1, Expedition o2) {
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
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Expeditions2.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                }
            });
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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Expeditions2.this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
