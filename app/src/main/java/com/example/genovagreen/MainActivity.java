package com.example.genovagreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Connection conn;
    public static String ip="192.168.1.7";
    public static String user="Angelo Morro";
    public static String password="barcollomanonMorro";
    public static String port="1433";
    public static String database="GenovaGreen";
    public static String ConnURL="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ContentFragment()).commit();
            navigationView.setCheckedItem(R.id.content_main);
        }
    }

   public static Connection StabilireConnessione(){
       StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
       try{
           Class.forName("net.sourceforge.jtds.jdbc.Driver");
           conn= DriverManager.getConnection(ConnURL, user, password);
           Log.e("ASK","Connection Called");
       }
       catch(Exception e){
           Log.e("ASK","EXCEPTION "+e.getMessage());
       }
       return null;
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
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}