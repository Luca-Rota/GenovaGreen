package com.example.genovagreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private NavigationView navigationView;
    private ConstraintLayout DoveLoButto, Pericolosi, Spedizioni;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        DoveLoButto=findViewById(R.id.DoveLoButtoMain);
        DoveLoButto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Butto.class);
                startActivity(intent);
            }
        });
        Pericolosi=findViewById(R.id.PeriocolosiMain);
        Pericolosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Pericolosi.class);
                startActivity(intent);
            }
        });
        Spedizioni=findViewById(R.id.SpedizioniMain);
        Spedizioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Spedizioni.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.content_main:
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.content_butto:
                Intent intent2=new Intent(MainActivity.this,Butto.class);
                startActivity(intent2);
                break;
            case R.id.content_pericolosi:
                Intent intent3=new Intent(MainActivity.this,Pericolosi.class);
                startActivity(intent3);
                break;
            case R.id.content_spedizioni:
                if(user==null) {
                    Intent intent4=new Intent(MainActivity.this,Spedizioni.class);
                    startActivity(intent4);
                }else{
                    Intent intent5=new Intent(MainActivity.this,Spedizioni2.class);
                    startActivity(intent5);
                }
                break;
            case R.id.content_impostazioni:
                Intent intent6=new Intent(MainActivity.this,Impostazioni.class);
                startActivity(intent6);
                break;
            case R.id.content_informazioni:
                Intent intent7=new Intent(MainActivity.this,Informazioni.class);
                startActivity(intent7);
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

}