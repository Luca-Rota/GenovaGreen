package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class Password extends AppCompatActivity {
    private Button NewPass,Annulla;
    private EditText Email;
    String codice;
    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        main=new MainActivity();
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
                main.codicepass=codice;
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


}



