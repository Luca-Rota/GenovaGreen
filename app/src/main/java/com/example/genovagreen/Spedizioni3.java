package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Spedizioni3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView but = findViewById(R.id.textView3);
        but.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent in = new Intent(Spedizioni3.this, Spedizioni4.class);
                   startActivity(in);
               }
        });
    }

}