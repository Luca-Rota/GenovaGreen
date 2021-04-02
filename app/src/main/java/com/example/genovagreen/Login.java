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
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Button button;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView registrati;

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
        navigationView.setCheckedItem(R.id.content_main);

        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        emailAdd=findViewById(R.id.EmailAddress1);
        password=findViewById(R.id.Password1);
        registrati=findViewById(R.id.Registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registrazione.class);
                startActivity(intent);
            }
        });
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
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new Spedizioni2Fragment()).commit();
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
                    Toast.makeText(Login.this, "Non possono esserci campi vuoti",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContentFragment()).commit();
                break;
            case R.id.dove_lo_butto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ButtoFragment()).commit();
                break;
            case R.id.pericolosi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PericolosiFragment()).commit();
                break;
            case R.id.spedizioni:
                if(user==null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SpedizioniFragment()).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Spedizioni2Fragment()).commit();
                }
                break;
            case R.id.impostazioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ImpostazioniFragment()).commit();
                break;
            case R.id.informazioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
