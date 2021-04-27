package com.example.genovagreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Registrazione extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText emailAdd, password, password2, username;
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser firebaseUser;
    private TextView login;
    private boolean res;
    private TextView username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){
                super.onDrawerSlide(drawerView, slideOffset);
                hideKeyboard(Registrazione.this);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Usernames");
        if(firebaseUser!=null) {
            View view=navigationView.getHeaderView(0);
            username1 = view.findViewById(R.id.nomeutente);
            username1.setVisibility(View.VISIBLE);
            final String email = firebaseUser.getEmail().trim();
            if (ref != null) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User ogg = ds.getValue(User.class);
                                String email2 = ogg.getEmail().trim();
                                String nomeutente = ogg.getUsername();
                                if (email.equals(email2)) {
                                    username1.setText(nomeutente);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Registrazione.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        emailAdd=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        password2=findViewById(R.id.RepeatPassword);
        username=findViewById(R.id.Username);
        login=findViewById(R.id.loginr);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrazione.this,Login.class));
            }
        });
        button=findViewById(R.id.buttonMap);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res = true;
                final String email = emailAdd.getText().toString().trim();
                String pass = password.getText().toString();
                String pass2 = password2.getText().toString();
                final String user = username.getText().toString().trim();
                if (!email.isEmpty() && !pass.isEmpty() && !pass2.isEmpty() && !user.isEmpty()) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Usernames");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    if (ds.getValue(User.class).getUsername().equals(user)) {
                                        res = false;
                                    }
                                }
                                if(!email.isEmpty()&&!pass.isEmpty()&&!pass2.isEmpty()&&!user.isEmpty()){
                                if(res){
                                    if(pass.equals(pass2)){
                                        if(password.length()>5){
                                            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Usernames").push();
                                                                    User users=new User();
                                                                    users.setEmail(email);
                                                                    users.setUsername(user);
                                                                    ref.setValue(users);
                                                                    auth.signOut();
                                                                    Toast.makeText(Registrazione.this, R.string.registrazione_avvenuta,Toast.LENGTH_LONG).show();
                                                                    startActivity(new Intent(Registrazione.this,Login.class));
                                                                }else{
                                                                    Toast.makeText(Registrazione.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }else{
                                                        Toast.makeText(Registrazione.this, R.string.registrazione_storta,Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Registrazione.this, ""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                @Override
                                                public void onCanceled() {
                                                    Toast.makeText(Registrazione.this, R.string.riprova,Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            Toast.makeText(Registrazione.this, R.string.pass_min_6_caratteri,Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(Registrazione.this, R.string.pass_confermapass_non_coincidono,Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Registrazione.this, R.string.username_in_uso,Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Registrazione.this, R.string.no_campi_vuoti,Toast.LENGTH_SHORT).show();
                            }
                         }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Registrazione.this, R.string.errore_db,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Registrazione.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Registrazione.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Registrazione.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(firebaseUser!=null && firebaseUser.isEmailVerified()) {
                    startActivity(new Intent(Registrazione.this, Spedizioni2.class));
                }else{
                    startActivity(new Intent(Registrazione.this, Spedizioni.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Registrazione.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Registrazione.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void ClickLogo(View view){
        closeDrawer(drawer);
    }

    public static void closeDrawer(DrawerLayout dl) {
        if(dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        }

    }
}


