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
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class Password extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button NewPass,Annulla;
    private EditText Email;
    private String codice;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

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

        Email=findViewById(R.id.EmailAddress1);
        NewPass=findViewById(R.id.newpass);
        Annulla=findViewById(R.id.buttonAnnulla);
        Annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Password.this,Login.class);
                startActivity(intent);
            }
        });
        NewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to=Email.getText().toString();
                Intent sendemail = new Intent(Intent.ACTION_SEND);
                sendemail.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                sendemail.putExtra(Intent.EXTRA_SUBJECT, "Modifica password GenovaGreen");
                codice=rndCod();
                sendemail.putExtra(Intent.EXTRA_TEXT, "Il codice per cambiare password è "+codice);
                Intent intent=new Intent(Password.this, Password2.class);
                startActivity(intent);
            }
        });

    }
    public String rndCod(){
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String random_string = sb1.toString();
        return random_string;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                Intent intent=new Intent(Password.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.dove_lo_butto:
                Intent intent2=new Intent(Password.this,Butto.class);
                startActivity(intent2);
                break;
            case R.id.pericolosi:
                Intent intent3=new Intent(Password.this,Pericolosi.class);
                startActivity(intent3);
                break;
            case R.id.spedizioni:
                if(user==null) {
                    Intent intent4=new Intent(Password.this,Spedizioni.class);
                    startActivity(intent4);
                }else{
                    Intent intent5=new Intent(Password.this,Spedizioni2.class);
                    startActivity(intent5);
                }
                break;
            case R.id.impostazioni:
                Intent intent6=new Intent(Password.this,Impostazioni.class);
                startActivity(intent6);
                break;
            case R.id.informazioni:
                Intent intent7=new Intent(Password.this,Informazioni.class);
                startActivity(intent7);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



