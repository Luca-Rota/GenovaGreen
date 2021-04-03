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
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class Registrazione extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText emailAdd, password, password2;
    private Button button;
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
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
        emailAdd=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        password2=findViewById(R.id.RepeatPassword);
        button=findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailAdd.getText().toString();
                String pass=password.getText().toString();
                String pass2=password2.getText().toString();
                if(!email.isEmpty()&&!pass.isEmpty()&&!pass2.isEmpty()){
                    if(pass.equals(pass2)){
                        if(password.length()>5){
                            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        auth.signOut();
                                        Intent intent=new Intent(Registrazione.this,Login.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Registrazione.this, "Qualcosa è andato storto con la registrazione",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registrazione.this, "Errore "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Toast.makeText(Registrazione.this, "Riprova",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(Registrazione.this, "La password deve avere almeno 6 caratteri",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(Registrazione.this, "Password e Conferma password non corrispondono",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Registrazione.this, "Non possono esserci campi vuoti",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_main:
                Intent intent=new Intent(Registrazione.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.dove_lo_butto:
                Intent intent2=new Intent(Registrazione.this,Butto.class);
                startActivity(intent2);
                break;
            case R.id.pericolosi:
                Intent intent3=new Intent(Registrazione.this,Pericolosi.class);
                startActivity(intent3);
                break;
            case R.id.spedizioni:
                if(user==null) {
                    Intent intent4=new Intent(Registrazione.this,Spedizioni.class);
                    startActivity(intent4);
                }else{
                    Intent intent5=new Intent(Registrazione.this,Spedizioni2.class);
                    startActivity(intent5);
                }
                break;
            case R.id.impostazioni:
                Intent intent6=new Intent(Registrazione.this,Impostazioni.class);
                startActivity(intent6);
                break;
            case R.id.informazioni:
                Intent intent7=new Intent(Registrazione.this,Informazioni.class);
                startActivity(intent7);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


