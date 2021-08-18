package com.example.genovagreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button NewPass,Annulla;
    private EditText Email;
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){
                super.onDrawerSlide(drawerView, slideOffset);
                hideKeyboard(Password.this);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        Email=findViewById(R.id.EmailAddress1);
        NewPass=findViewById(R.id.newpass1);
        Annulla=findViewById(R.id.annulla);
        Annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        NewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Email.getText().toString().matches(""))
                    Toast.makeText(Password.this, "Inserire email valida", Toast.LENGTH_SHORT).show();

                else {
                    auth.sendPasswordResetEmail(Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Password.this, R.string.email_inviata, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Password.this, Login.class));
                                finish();
                            } else {
                                Toast.makeText(Password.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

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
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}



