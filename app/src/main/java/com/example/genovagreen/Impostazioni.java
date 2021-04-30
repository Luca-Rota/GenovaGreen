package com.example.genovagreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.res.Configuration;
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
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private ConstraintLayout layout;
    private TextView username;
    private TextView notify;

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
        View view=navigationView.getHeaderView(0);
        username = view.findViewById(R.id.nomeutente);
        CommonFunctions.setUsername(username, navigationView, user);

        layout=findViewById(R.id.LayoutLogout);
        if(user!=null){
            layout.setVisibility(View.VISIBLE);
        }else{
            layout.setVisibility(View.INVISIBLE);
        }
        logout=findViewById(R.id.LogoutImpostazioni);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mBuilder= new AlertDialog.Builder(Impostazioni.this)
                        .setMessage(R.string.popup_impostazioni)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                auth.signOut();
                                Toast.makeText(Impostazioni.this, R.string.logout_toast,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Impostazioni.this, MainActivity.class));
                            }})
                        .setNegativeButton(R.string.no, null).show();
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

        notify=findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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
        startActivity(new Intent(Impostazioni.this,MainActivity.class));
    }
}
