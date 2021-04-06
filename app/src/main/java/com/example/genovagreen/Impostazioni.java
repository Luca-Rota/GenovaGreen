package com.example.genovagreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Impostazioni extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView logout,lingua;
    private Switch notifiche;
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private NavigationView navigationView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_impostazioni);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        logout=findViewById(R.id.LogoutImpostazioni);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mBuilder= new AlertDialog.Builder(Impostazioni.this)
                        .setMessage("Sei sicuro di voler uscire dall'account?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                auth.signOut();
                                Toast.makeText(Impostazioni.this, "Logout avvenuto con successo",Toast.LENGTH_SHORT).show();
                                recreate();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        notifiche=findViewById(R.id.notificheImpostazioni);

        lingua=findViewById(R.id.linguaImpostazioni);
        lingua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] listItems={"Italiano", "English"};
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(Impostazioni.this);
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            setLocale("values");
                            startActivity(new Intent(Impostazioni.this, Impostazioni.class));
                        }
                        if(i==1){
                            setLocale("en");
                            startActivity(new Intent(Impostazioni.this, Impostazioni.class));
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
            }
        });


    }

    private void setLocale(String lang) {
        Locale locale= new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Impostazioni.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Impostazioni.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Impostazioni.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user==null) {
                    startActivity(new Intent(Impostazioni.this, Spedizioni.class));
                }else{
                    startActivity(new Intent(Impostazioni.this, Spedizioni2.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Impostazioni.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Impostazioni.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
