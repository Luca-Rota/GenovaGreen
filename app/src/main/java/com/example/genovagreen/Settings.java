package com.example.genovagreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView logout,lingua, username, id, notify, modify;
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private NavigationView navigationView;
    private FirebaseUser user;
    private ConstraintLayout layout, layoutProfile;

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
        id = view.findViewById(R.id.id);
        CommonFunctions.setUsername(username, id, navigationView, user);

        layoutProfile=findViewById(R.id.layoutProfile);
        if(user!=null){
            layoutProfile.setVisibility(View.VISIBLE);
        }else{
            layoutProfile.setVisibility(View.INVISIBLE);
        }

        modify=findViewById(R.id.modificaProfilo);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, ModifyProfile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

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
                AlertDialog mBuilder= new AlertDialog.Builder(Settings.this)
                        .setMessage(R.string.popup_impostazioni)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                auth.signOut();
                                Toast.makeText(Settings.this, R.string.logout_toast,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Settings.this, MainActivity.class));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                            }})
                        .setNegativeButton(R.string.no, null).show();
            }
        });


        lingua=findViewById(R.id.linguaImpostazioni);
        lingua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] listItems={"Italiano", "English"};
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(Settings.this);
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            setLocale("values");
                            startActivity(new Intent(Settings.this, Settings.class));
                        }
                        if(i==1){
                            setLocale("en");
                            startActivity(new Intent(Settings.this, Settings.class));
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
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
        startActivity(new Intent(Settings.this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
