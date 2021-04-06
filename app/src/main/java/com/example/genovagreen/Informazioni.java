package com.example.genovagreen;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

public class Informazioni extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private NavigationView navigationView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informazioni);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_informazioni);
        TextView share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                //shareIntent.putExtra(Intent.EXTRA_SUBJECT, "GenovaGreen");
                String shareMessage = "GenovaGreen \n https://play.google.com/store/apps/details?="+ BuildConfig.APPLICATION_ID;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Condividi"));
            }
        });

        TextView support =findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:genovagreen2020@gmail.com" +
                        "?subject=" + Uri.encode("GenovaGreen - Assistenza")));
                startActivity(Intent.createChooser(intent, "Scegli un'applicazione"));
            }
        });

        ImageView fb = findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("https://www.facebook.com/genova.green.7/"));
                startActivity(in);
            }
        });
        ImageView insta = findViewById(R.id.insta);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("https://www.instagram.com/genovagreen_app/"));
                startActivity(in);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Informazioni.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Informazioni.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Informazioni.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user==null) {
                    startActivity(new Intent(Informazioni.this, Spedizioni.class));
                }else{
                    startActivity(new Intent(Informazioni.this, Spedizioni2.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Informazioni.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Informazioni.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}




