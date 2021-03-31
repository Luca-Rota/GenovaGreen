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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        switchFragment();
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

    }

    public void switchFragment(){
        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        if(user==null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginFragment()).commit();
        }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContentFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.content_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContentFragment()).commit();
                break;
            case R.id.dove_lo_butto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ButtoFragment()).commit();
                break;
            case R.id.pericolosi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PericolosiFragment()).commit();
                break;
            case R.id.spedizioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SpedizioniFragment()).commit();
                break;
            case R.id.impostazioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ImpostazioniFragment()).commit();
                break;
            case R.id.informazioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).commit();
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



    public void goToDoveLoButto(View v){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ButtoFragment()).commit();
            }

    public void goToPericolosi(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PericolosiFragment()).commit();
    }

    public void goToSpedizioni(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SpedizioniFragment()).commit();
    }

    public void goToRegistrati(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegistrazioneFragment()).commit();
    }

    public void goToLogin(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoginFragment()).commit();
    }
    public void goToCambioPassword(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PasswordFragment()).commit();
    }

    public void showChangeLanguageDialog(View v) {
        final String[] listItems={"Italiano", "English"};
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    setLocale("values");
                    navigationView.setCheckedItem(R.id.content_main);
                    recreate();
                }
                if(i==1){
                    setLocale("en");
                    navigationView.setCheckedItem(R.id.content_main);
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
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

    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language=prefs.getString("My_Lang","");
        setLocale(language);
    }

    public void Logout(View view){
        auth=FirebaseAuth.getInstance();
        auth.signOut();
        navigationView.setCheckedItem(R.id.content_main);
        recreate();

    }
}