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
        navigationView.setCheckedItem(R.id.content_main);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        logout=findViewById(R.id.LogoutImpostazioni);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                navigationView.setCheckedItem(R.id.impostazioni);
                recreate();
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
                            navigationView.setCheckedItem(R.id.impostazioni);
                            recreate();
                        }
                        if(i==1){
                            setLocale("en");
                            navigationView.setCheckedItem(R.id.impostazioni);
                            recreate();
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
                Intent intent=new Intent(Impostazioni.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.dove_lo_butto:
                Intent intent2=new Intent(Impostazioni.this,Butto.class);
                startActivity(intent2);
                break;
            case R.id.pericolosi:
                Intent intent3=new Intent(Impostazioni.this,Pericolosi.class);
                startActivity(intent3);
                break;
            case R.id.spedizioni:
                if(user==null) {
                    Intent intent4=new Intent(Impostazioni.this,Spedizioni.class);
                    startActivity(intent4);
                }else{
                    Intent intent5=new Intent(Impostazioni.this,Spedizioni2.class);
                    startActivity(intent5);
                }
                break;
            case R.id.impostazioni:
                Intent intent6=new Intent(Impostazioni.this,Impostazioni.class);
                startActivity(intent6);
                break;
            case R.id.informazioni:
                Intent intent7=new Intent(Impostazioni.this,Informazioni.class);
                startActivity(intent7);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language=prefs.getString("My_Lang","");
        setLocale(language);
    }
}
