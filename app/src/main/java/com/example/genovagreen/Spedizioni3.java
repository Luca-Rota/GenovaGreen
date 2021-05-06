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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Spedizioni3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private AdapterSpedizioni adapterSpedizioni1,adapterSpedizioni2;
    private ArrayList<Spedizione> list1,list2;
    private ArrayList<MySped> listemail1,listemail2;
    private DatabaseReference ref1,ref2,ref3;
    private RecyclerView recyclerView1,recyclerView2;
    private TextView button, username, nosped1, nosped2, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        ref1= FirebaseDatabase.getInstance().getReference().child("SpedCreate");
        recyclerView1=(RecyclerView)findViewById(R.id.rvMySped3);
        recyclerView1.setHasFixedSize(true);
        ref2= FirebaseDatabase.getInstance().getReference().child("SpedPart");
        ref3=FirebaseDatabase.getInstance().getReference().child("Spedizioni");
        recyclerView2=(RecyclerView)findViewById(R.id.rvSped3);
        recyclerView2.setHasFixedSize(true);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        View view=navigationView.getHeaderView(0);
        username = view.findViewById(R.id.nomeutente);
        id = view.findViewById(R.id.id);
        CommonFunctions.setUsername(username, id, navigationView, user);

        button = findViewById(R.id.textSpedizioni3);
        button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(Spedizioni3.this, Spedizioni4.class));
               }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView1=(RecyclerView)findViewById(R.id.rvMySped3);
        recyclerView2=(RecyclerView)findViewById(R.id.rvSped3);
        nosped1=findViewById(R.id.nosped1);
        nosped2=findViewById(R.id.nosped2);
        final String email=user.getEmail();
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        listemail1= new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if(email.equals(ds.getValue(MySped.class).getEmail()))
                            listemail1.add(ds.getValue(MySped.class));
                        }
                            ref3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        list1=new ArrayList<>();
                                        for(DataSnapshot ds : snapshot.getChildren()){
                                            for(int i=0;i<listemail1.size();i++){
                                                if(listemail1.get(i).getId().equals(ds.getKey())){
                                                    list1.add(ds.getValue(Spedizione.class));
                                                }
                                            }
                                        }
                                        if(list1.isEmpty()){
                                            recyclerView1.setVisibility(View.INVISIBLE);
                                            nosped1.setVisibility(View.VISIBLE);
                                        }else {
                                            adapterSpedizioni1 = new AdapterSpedizioni(list1);
                                            recyclerView1.setAdapter(adapterSpedizioni1);
                                        }
                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Spedizioni3.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                }
            });

            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        listemail2= new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if(email.equals(ds.getValue(MySped.class).getEmail()))
                                listemail2.add(ds.getValue(MySped.class));
                        }
                            ref3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        list2=new ArrayList<>();
                                        for(DataSnapshot ds : snapshot.getChildren()){
                                            for(int i=0;i<listemail2.size();i++){
                                                if(listemail2.get(i).getId().equals(ds.getKey())){
                                                    list2.add(ds.getValue(Spedizione.class));
                                                }
                                            }
                                        }
                                        if(list2.isEmpty()){
                                             recyclerView2.setVisibility(View.INVISIBLE);
                                             nosped2.setVisibility(View.VISIBLE);
                                        }else {
                                            adapterSpedizioni2 = new AdapterSpedizioni(list2);
                                            recyclerView2.setAdapter(adapterSpedizioni2);
                                        }
                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Spedizioni3.this, R.string.errore_db, Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(Spedizioni3.this,Spedizioni2.class));
    }
}