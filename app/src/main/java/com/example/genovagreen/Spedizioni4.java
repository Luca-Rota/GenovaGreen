package com.example.genovagreen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class Spedizioni4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseUser user;
    private TextView dateButton, username, position4, timeButton, id;
    private Button map, agg;
    private EditText descrizione4;
    private DatabaseReference ref;
    private String nomeutente;
    private boolean ok;
    public Calendar calendar = Calendar.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){
                super.onDrawerSlide(drawerView, slideOffset);
                hideKeyboard(Spedizioni4.this);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.content_spedizioni);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        View view=navigationView.getHeaderView(0);
        username = view.findViewById(R.id.nomeutente);
        id = view.findViewById(R.id.id);
        CommonFunctions.setUsername(username, id, navigationView, user);

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

        Button annulla = findViewById(R.id.annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Spedizioni4.this, Spedizioni3.class));
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
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        User ogg = ds.getValue(User.class);
                                        String email2 = ogg.getEmail().trim();
                                        nomeutente = ogg.getUsername();
                                        if (email.equals(email2)) {
                                            String descrizione=descrizione4.getText().toString();
                                            String posizione=position4.getText().toString();
                                            String ora=timeButton.getText().toString();
                                            String date=dateButton.getText().toString();

                                            Calendar currentTime = Calendar.getInstance();
                                            int month = currentTime.get(Calendar.MONTH) + 1;
                                            currentTime.set(Calendar.MONTH, month);
                                            if (calendar.compareTo(currentTime) == -1 ) {
                                                Toast.makeText(getApplicationContext(), "Orario non valido", Toast.LENGTH_LONG).show();
                                            }

                                            else if(!descrizione.isEmpty()&&!posizione.isEmpty()&&!ora.isEmpty()&&!date.isEmpty()) {
                                                String partecipanti = "1";
                                                Random myRandom=new Random();
                                                int idNotifica=myRandom.nextInt(99999);
                                                Spedizione sped = new Spedizione(posizione, descrizione, nomeutente, date, ora, partecipanti, idNotifica);
                                                ref = FirebaseDatabase.getInstance().getReference("Spedizioni").push();
                                                ref.setValue(sped);
                                                ref = FirebaseDatabase.getInstance().getReference("Spedizioni");
                                                ref.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                                if(sped.getOrganizzatore().trim().equals(ds.getValue(Spedizione.class).getOrganizzatore().trim())&&
                                                                        sped.getData().trim().equals(ds.getValue(Spedizione.class).getData().trim())&&
                                                                        sped.getLuogo().trim().equals(ds.getValue(Spedizione.class).getLuogo().trim())&&
                                                                        sped.getOra().trim().equals(ds.getValue(Spedizione.class).getOra().trim())){
                                                                    if(ok==true){
                                                                        startActivity(new Intent(Spedizioni4.this, Spedizioni3.class));
                                                                        finish();
                                                                    }else {
                                                                        String id = ds.getKey();
                                                                        ref = FirebaseDatabase.getInstance().getReference("SpedCreate").push();
                                                                        MySped mySped = new MySped(id, email);
                                                                        ref.setValue(mySped);
                                                                        ok = true;
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
                                                Toast.makeText(Spedizioni4.this, R.string.no_campi_vuoti, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Spedizioni4.this, R.string.errore_db, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
        });
    }


    private void handleDateButton() {
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Spedizioni4.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String dateText = dayOfMonth+"/"+month+"/"+year;
                dateButton.setText(dateText);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    private void handleTimeButton() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Spedizioni4.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                SimpleDateFormat h_mm_a   = new SimpleDateFormat("h:mm a");
                SimpleDateFormat hh_mm = new SimpleDateFormat("HH:mm");
                if (timeText.length()>5){
                    try {
                        Date d1 = h_mm_a.parse(timeText);
                        timeButton.setText(hh_mm.format(d1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    timeButton.setText(timeText);
            }
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(Spedizioni4.this));
        timePickerDialog.show();
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