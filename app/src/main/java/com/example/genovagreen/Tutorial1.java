package com.example.genovagreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Tutorial1 extends AppCompatActivity {

    private ImageView front, next, previous;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial1);

        front = findViewById(R.id.imageView18);
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutorial1.this,Tutorial2.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        back = findViewById(R.id.textView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutorial1.this, Tutorial0.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        previous = findViewById(R.id.imageView);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutorial1.this, Tutorial0.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        next = findViewById(R.id.imageView16);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutorial1.this,Tutorial2.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }
}