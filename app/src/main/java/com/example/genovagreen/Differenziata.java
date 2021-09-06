package com.example.genovagreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class Differenziata extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private TextView username, id;
    private ArrayList<ItemPericolosi> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_differenziata);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_pericolosi);
        String apri = getIntent().getStringExtra("apri");
        if(apri.equals(null))
            apri="niente";

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        View view=navigationView.getHeaderView(0);
        username = view.findViewById(R.id.nomeutente);
        id = view.findViewById(R.id.id);
        CommonFunctions.setUsername(username, id, navigationView, user);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        SetDati();
        AdapterPericolosi adapterPericolosi=new AdapterPericolosi(items, apri);
        recyclerView.setAdapter(adapterPericolosi);

    }


    private void SetDati(){
        items=new ArrayList<>();
        items.add(new ItemPericolosi(getString(R.string.differenziata1_tit), getString(R.string.differenziata1_desc)));
        items.add(new ItemPericolosi(getString(R.string.differenziata2_tit), getString(R.string.differenziata2_desc)));
        items.add(new ItemPericolosi(getString(R.string.differenziata3_tit), getString(R.string.differenziata3_desc)));
        items.add(new ItemPericolosi(getString(R.string.differenziata4_tit), getString(R.string.differenziata4_desc)));
        items.add(new ItemPericolosi(getString(R.string.differenziata5_tit), getString(R.string.differenziata5_desc)));
        items.add(new ItemPericolosi(getString(R.string.differenziata6_tit), getString(R.string.differenziata6_desc)));
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
        startActivity(new Intent(Differenziata.this, Butto.class));
    }

}