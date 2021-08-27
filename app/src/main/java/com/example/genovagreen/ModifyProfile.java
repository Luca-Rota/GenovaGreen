package com.example.genovagreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button Annulla, NewPass;
    private ImageView NewUser;
    private TextView Email_Utente;
    private EditText Placeholder;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                hideKeyboard(ModifyProfile.this);
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String email = user.getEmail().trim();
        NewPass=findViewById(R.id.invia);
        Placeholder=findViewById(R.id.placeholder);
        NewUser=findViewById(R.id.newuser);

        Email_Utente=findViewById(R.id.email_utente);
        Email_Utente.setText(email);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Usernames");
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
                                Placeholder.setHint(nomeutente);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Usernames");
                if (ref != null) {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    User ogg = ds.getValue(User.class);
                                    String email2 = ogg.getEmail().trim();
                                    String nomeutente = ogg.getUsername();
                                    String id=ds.getKey();
                                    if (email.equals(email2)) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usernames");
                                        User user = new User(Placeholder.getText().toString(),email);
                                        ref.child(id).setValue(user);
                                        finish();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
        Annulla = findViewById(R.id.annulla);

        Annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ModifyProfile.this, R.string.email_inviata, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ModifyProfile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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