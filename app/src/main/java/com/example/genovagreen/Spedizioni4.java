package com.example.genovagreen;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Spedizioni4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private TextView timeButton;
    private int tHour, tMinute;
    private TextView dateButton, username, position4;
    private Button map, agg;
    private TextView mapText;
    private String date, time;
    private EditText descrizione4;
    private DatabaseReference ref;
    private String nomeutente;

    private static final int REQUEST_CODE = 5678;
    private TextView selectedLocationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        dateButton = findViewById(R.id.data4);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        timeButton = findViewById(R.id.time);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        ref=FirebaseDatabase.getInstance().getReference("Usernames");
        if(user!=null) {
            View view=navigationView.getHeaderView(0);
            username = view.findViewById(R.id.nomeutente);
            username.setVisibility(View.VISIBLE);
            final String email = user.getEmail().trim();
            if (ref != null) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User ogg = ds.getValue(User.class);
                                String email2 = ogg.getEmail().trim();
                                nomeutente = ogg.getUsername();
                                if (email.equals(email2)) {
                                    username.setText(nomeutente);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Spedizioni4.this, "errore db", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }


        Button annulla = findViewById(R.id.annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        map = findViewById(R.id.buttonMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Spedizioni4.this, MapSpedizioni.class));

            }
        });
        String luogo=getIntent().getStringExtra("luogo");
        position4=findViewById(R.id.position);
        position4.setText(luogo);
        descrizione4=findViewById(R.id.descrizione4);
        agg=findViewById(R.id.agg);
        agg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref=FirebaseDatabase.getInstance().getReference("Usernames");
                    final String email = user.getEmail().trim();
                    if (ref != null) {
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        User ogg = ds.getValue(User.class);
                                        String email2 = ogg.getEmail().trim();
                                        nomeutente = ogg.getUsername();
                                        if (email.equals(email2)) {
                                            String descrizione=descrizione4.getText().toString();
                                            String posizione=position4.getText().toString();
                                            String ora=timeButton.getText().toString();
                                            String date=dateButton.getText().toString();
                                            if(!descrizione.isEmpty()&&!posizione.isEmpty()&&!ora.isEmpty()&&!date.isEmpty()) {
                                                String partecipanti = "1";
                                                Spedizione sped = new Spedizione(posizione, descrizione, nomeutente, date, ora, partecipanti);
                                                ref = FirebaseDatabase.getInstance().getReference("Spedizioni").push();
                                                ref.setValue(sped);
                                                ref = FirebaseDatabase.getInstance().getReference("Spedizioni");
                                                ref.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists()){
                                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                                if(sped.getOrganizzatore().trim().equals(ds.getValue(Spedizione.class).getOrganizzatore().trim())&&
                                                                        sped.getData().trim().equals(ds.getValue(Spedizione.class).getData().trim())&&
                                                                        sped.getLuogo().trim().equals(ds.getValue(Spedizione.class).getLuogo().trim())&&
                                                                        sped.getOra().trim().equals(ds.getValue(Spedizione.class).getOra().trim())){
                                                                    String id=ds.getKey();
                                                                    ref = FirebaseDatabase.getInstance().getReference("SpedCreate").push();
                                                                    MySped mySped= new MySped(id, email);
                                                                    ref.setValue(mySped);
                                                                    startActivity(new Intent(Spedizioni4.this, Spedizioni3.class));
                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }else{
                                                Toast.makeText(Spedizioni4.this,"Tutti i campi devono essere riempiti", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Spedizioni4.this, "errore db", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


            }
        });
    }


    private String handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Spedizioni4.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                month = month + 1;
                date = dayOfMonth + "/" + month + "/" + year;
                dateButton.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
        return date;
    }

    private String handleTimeButton() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Spedizioni4.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                String timeText = java.text.DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
                timeButton.setText(timeText);
            }
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(Spedizioni4.this));
        timePickerDialog.show();
        return time;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.content_main:
                startActivity(new Intent(Spedizioni4.this, MainActivity.class));
                break;
            case R.id.content_butto:
                startActivity(new Intent(Spedizioni4.this, Butto.class));
                break;
            case R.id.content_pericolosi:
                startActivity(new Intent(Spedizioni4.this, Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if (user != null && user.isEmailVerified()) {
                    startActivity(new Intent(Spedizioni4.this, Spedizioni2.class));
                } else {
                    startActivity(new Intent(Spedizioni4.this, Spedizioni.class));
                }
                break;
            case R.id.content_impostazioni:
                startActivity(new Intent(Spedizioni4.this, Impostazioni.class));
                break;
            case R.id.content_informazioni:
                startActivity(new Intent(Spedizioni4.this, Informazioni.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}