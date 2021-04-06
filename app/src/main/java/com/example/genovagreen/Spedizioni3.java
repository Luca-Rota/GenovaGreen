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
import android.widget.TextView;

public class Spedizioni3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private TextView button;

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

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        button = findViewById(R.id.textSpedizioni3);
        button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(Spedizioni3.this, Spedizioni4.class));
               }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Spedizioni3.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Spedizioni3.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Spedizioni3.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user==null) {
                    startActivity(new Intent(Spedizioni3.this, Spedizioni.class));
                }else{
                    startActivity(new Intent(Spedizioni3.this, Spedizioni2.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Spedizioni3.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Spedizioni3.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}