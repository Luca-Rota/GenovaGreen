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


public class Spedizioni4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

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
    private GoogleMap mMap;
    private final static int PLACE_PICKER_REQUEST = 999;
    private final static int LOCATION_REQUEST_CODE = 23;
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
                MapFragment fragment = new MapFragment();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper1, fragment).commit();
                SupportMapFragment mapFragment = new SupportMapFragment();
                mapFragment.getMapAsync((OnMapReadyCallback) v.getContext());
                if (ContextCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                            LOCATION_REQUEST_CODE);
                }
            }
        });
        position4=findViewById(R.id.position);
        descrizione4=findViewById(R.id.descrizione4);
        agg=findViewById(R.id.agg);
        final String descrizione=descrizione4.getText().toString();
        final String posizione=position4.getText().toString();
        final String ora=timeButton.getText().toString();
        final String date=dateButton.getText().toString();
        agg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partecipanti="1";
                Spedizione spedizione=new Spedizione(posizione,descrizione,nomeutente,date,ora,partecipanti);
                ref= FirebaseDatabase.getInstance().getReference("Spedizioni").push();
                ref.setValue(spedizione);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {
                            LatLng ltlng=new LatLng(location.getLatitude(),location.getLongitude());
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                    ltlng, 16f);
                            mMap.animateCamera(cameraUpdate);
                        }
                    });
                    Location location = mMap.getMyLocation();

                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);

                            markerOptions.title(getAddress(latLng));
                            mMap.clear();
                            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                                    latLng, 15);
                            mMap.animateCamera(location);
                            mMap.addMarker(markerOptions);
                        }
                    });



                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    private String getAddress(LatLng latLng){

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {

                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment dialogFragment = new ConfirmAddress();

            Bundle args = new Bundle();
            args.putDouble("lat", latLng.latitude);
            args.putDouble("long", latLng.longitude);
            args.putString("address", address);
            dialogFragment.setArguments(args);
            dialogFragment.show(ft, "dialog");
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "No Address Found";

        }


    }
}