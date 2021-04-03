package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Pericolosi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pericolosi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_main);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        button=findViewById(R.id.buttonPeriocolosi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Pericolosi.this, Pericolosi2.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                Intent intent=new Intent(Pericolosi.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.dove_lo_butto:
                Intent intent2=new Intent(Pericolosi.this,Butto.class);
                startActivity(intent2);
                break;
            case R.id.pericolosi:
                Intent intent3=new Intent(Pericolosi.this,Pericolosi.class);
                startActivity(intent3);
                break;
            case R.id.spedizioni:
                if(user==null) {
                    Intent intent4=new Intent(Pericolosi.this,Spedizioni.class);
                    startActivity(intent4);
                }else{
                    Intent intent5=new Intent(Pericolosi.this,Spedizioni2.class);
                    startActivity(intent5);
                }
                break;
            case R.id.impostazioni:
                Intent intent6=new Intent(Pericolosi.this,Impostazioni.class);
                startActivity(intent6);
                break;
            case R.id.informazioni:
                Intent intent7=new Intent(Pericolosi.this,Informazioni.class);
                startActivity(intent7);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
