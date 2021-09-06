package com.example.genovagreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private NavigationView navigationView;
    private ConstraintLayout DoveLoButto, Pericolosi, Spedizioni;
    private FirebaseUser user;
    private TextView username, id;
    Animation anim1;
    Animation anim2;
    Animation anim3;

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

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if(firstStart){
            showStartTutorial();
        }

        if (isOnline()) {
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                View view = navigationView.getHeaderView(0);
                username = view.findViewById(R.id.nomeutente);
                id = view.findViewById(R.id.id);
                CommonFunctions.setUsername(username, id, navigationView, user);

                DoveLoButto = findViewById(R.id.DoveLoButtoMain);
                anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_button1);
                DoveLoButto.setAnimation(anim1);

                DoveLoButto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Butto.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                });

                Pericolosi = findViewById(R.id.PeriocolosiMain);
                anim2 = AnimationUtils.loadAnimation(this, R.anim.anim_button2);
                Pericolosi.setAnimation(anim2);

                Pericolosi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Pericolosi.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                });

                Spedizioni = findViewById(R.id.SpedizioniMain);
                anim3 = AnimationUtils.loadAnimation(this, R.anim.anim_button3);
                Spedizioni.setAnimation(anim3);

                Spedizioni.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user != null && user.isEmailVerified()) {
                            startActivity(new Intent(MainActivity.this, Spedizioni2.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        } else {
                            startActivity(new Intent(MainActivity.this, Spedizioni.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        }
                    }
                });

        } else {
                AlertDialog mBuilder= new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Internet non disponibile.")
                        .setCancelable(false)
                        .setMessage("Verifica la tua connessione e riprova di nuovo.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Riprova", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recreate();
                            }
                        }).show();

        }

    }

    private void showStartTutorial(){
        startActivity(new Intent(MainActivity.this, Tutorial0.class));
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
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
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable())
            return false;

        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog mBuilder= new AlertDialog.Builder(MainActivity.this)
                .setMessage(R.string.exit)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }})
                .setNegativeButton(R.string.no, null).show();


    }
}