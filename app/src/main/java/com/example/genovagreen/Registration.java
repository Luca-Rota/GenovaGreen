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
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText emailAdd, password, password2, username;
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser firebaseUser;
    private TextView login,Username;
    private boolean res;

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
                hideKeyboard(Registration.this);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();

        emailAdd=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        password2=findViewById(R.id.RepeatPassword);
        username=findViewById(R.id.Username);
        login=findViewById(R.id.loginr);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Login.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
                                                                    Toast.makeText(Registration.this, R.string.registrazione_avvenuta,Toast.LENGTH_LONG).show();
                                                                    startActivity(new Intent(Registration.this,Login.class));
                                                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                                                }else{
                                                                    Toast.makeText(Registration.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }else{
                                                        Toast.makeText(Registration.this, R.string.registrazione_storta,Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Registration.this, ""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                @Override
                                                public void onCanceled() {
                                                    Toast.makeText(Registration.this, R.string.riprova,Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            Toast.makeText(Registration.this, R.string.pass_min_6_caratteri,Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(Registration.this, R.string.pass_confermapass_non_coincidono,Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Registration.this, R.string.username_in_uso,Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Registration.this, R.string.no_campi_vuoti,Toast.LENGTH_SHORT).show();
                            }
                         }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Registration.this, R.string.errore_db,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        View v=new View(this);
        CommonFunctions.onNavigationItemSelected(item,v,firebaseUser, drawer);
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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Registration.this, Expeditions.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}


