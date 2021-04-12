package com.example.genovagreen;

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
                startActivity(new Intent(Registrazione.this,Login.class));
            }
        });
        button=findViewById(R.id.buttonMap);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res=true;
                final String email=emailAdd.getText().toString().trim();
                String pass=password.getText().toString();
                String pass2=password2.getText().toString();
                final String user=username.getText().toString().trim();
                if(!email.isEmpty()&&!pass.isEmpty()&&!pass2.isEmpty()&&!user.isEmpty()){
                   //controllo username unico
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
                                                        Toast.makeText(Registrazione.this, "Registrazione avvenuta. Controlla la tua email per la verificare.",Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(Registrazione.this,Login.class));
                                                    }else{
                                                        Toast.makeText(Registrazione.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
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
                        Toast.makeText(Registrazione.this, "Username già in uso",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Registrazione.this, "Non possono esserci campi vuoti",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void userIsUnique(final String user) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Usernames");
        if(ref!=null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if(user.equals(ds.getValue(User.class).getUsername().toString().trim())){
                                setResFalse();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Registrazione.this, "errore db", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setResFalse() {
        res=false;
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
    public String rndString(){
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 10; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        return sb1.toString();
    }
}


