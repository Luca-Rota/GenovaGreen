package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText emailAdd, password;
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private TextView cambiopass, registrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        cambiopass=findViewById(R.id.cambiopass);
        cambiopass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Password.class));
            }
        });
        registrati=findViewById(R.id.registratil);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registrazione.class));
            }
        });

        emailAdd=findViewById(R.id.EmailAddress1);
        password=findViewById(R.id.Password1);
        button=findViewById(R.id.accedi);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailAdd.getText().toString();
                String pass=password.getText().toString();
                if(!email.isEmpty()&&!pass.isEmpty()){
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Login eseguito con successo",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, Spedizioni2.class));
                            }else{
                                Toast.makeText(Login.this, "Qualcosa è andato storto con il login",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Errore "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(Login.this, "Riprova",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "Non possono esserci campi vuoti", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                startActivity(new Intent(Login.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Login.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Login.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user==null) {
                    startActivity(new Intent(Login.this, Spedizioni.class));
                }else{
                    startActivity(new Intent(Login.this, Spedizioni2.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Login.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Login.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

