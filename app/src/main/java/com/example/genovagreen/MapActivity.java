package com.example.genovagreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    ArrayList<String> title = new ArrayList<>();
    ArrayList<LatLng> arrayList = new ArrayList<>();
    LatLng isolaEcologica1 = new LatLng(44.42769182891236, 8.796290480577571);
    LatLng amiu1 = new LatLng(44.43119054178913, 8.834668975362476);
    LatLng ecolegno = new LatLng(44.425832057000136, 8.886227992847063);
    LatLng amiu2 = new LatLng(44.42716514243485, 8.887297472641704);
    LatLng ecopunto = new LatLng(44.41304054072918, 8.892925320860591);
    LatLng discarica1 = new LatLng(44.40812426817897, 8.901099046033009);
    LatLng manutencoop = new LatLng(44.41007505458493, 8.927507394587934);
    LatLng amiu3 = new LatLng(44.407254583060855, 8.935747140635796);
    LatLng isolaEcologica2 = new LatLng(44.43251135164932, 8.959608072030354);
    LatLng discarica2 = new LatLng(44.43552251153035, 8.962855877476503);
    LatLng isolaEcologica3 = new LatLng(44.43273952581004, 8.960834985376513);
    LatLng areaEcologica = new LatLng(44.388286069727, 9.073623011583228);
    LatLng discarica3 = new LatLng(44.40716592436927, 8.908112995601162);
    LatLng discarica4 = new LatLng(44.4078594386742, 8.930743370015112);
    LatLng discarica5 = new LatLng(44.41414073200417, 8.939129265073419);
    LatLng sgomberiGenova = new LatLng(44.4086378887034, 8.93426033011987);
    LatLng ecoGea = new LatLng(44.39879042398218, 9.003619234434806);
    LatLng raccoltaRifiuti = new LatLng(44.39437486108722, 8.998898546594885);
    LatLng reVetro = new LatLng(44.4282681937939, 8.838302375625819);
    LatLng ferrotrade = new LatLng(44.412329569584884, 8.904906989512705);
    LatLng speltaRottami = new LatLng(44.423609660302525, 8.948852302041386);
    LatLng amiu4 = new LatLng(44.43939371237608, 8.886334929553165);
    LatLng rottamiMetallici = new LatLng(44.44701985488982, 8.9042996025031);
    LatLng docksLanterna = new LatLng(44.40367625780342, 8.93634469418444);
    LatLng alpEcologia = new LatLng(44.401074620004394, 8.94629809387334);
    LatLng ecover = new LatLng(44.445806671503654, 8.965719361559001);
    LatLng isolaEcologica4 = new LatLng(44.48762950153952, 8.897787148115768);
    LatLng amiu5 = new LatLng(44.49252783691238, 8.903795296275666);

    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    private GoogleMap nMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listInit();

        final LocationManager manager = (LocationManager) getSystemService( LOCATION_SERVICE );
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            getCurrentLocation();
        }
        else{
            buildAlertMessageNoGps();
            getOnlyMarker();
        }
    }

    private void getOnlyMarker() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                for(int i=0;i<arrayList.size();i++){
                    for (int j=0;j<title.size();j++){
                        googleMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))));
                    }
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), 11f));
                }
            }
        });
    }

    private void listInit(){
        arrayList.add(isolaEcologica1);
        arrayList.add(isolaEcologica2);
        arrayList.add(isolaEcologica3);
        arrayList.add(discarica1);
        arrayList.add(discarica2);
        arrayList.add(discarica3);
        arrayList.add(discarica4);
        arrayList.add(discarica5);
        arrayList.add(amiu1);
        arrayList.add(amiu2);
        arrayList.add(amiu3);
        arrayList.add(amiu4);
        arrayList.add(ecoGea);
        arrayList.add(ecolegno);
        arrayList.add(ecopunto);
        arrayList.add(ecover);
        arrayList.add(docksLanterna);
        arrayList.add(alpEcologia);
        arrayList.add(areaEcologica);
        arrayList.add(manutencoop);
        arrayList.add(sgomberiGenova);
        arrayList.add(speltaRottami);
        arrayList.add(raccoltaRifiuti);
        arrayList.add(reVetro);
        arrayList.add(rottamiMetallici);
        arrayList.add(ferrotrade);
        arrayList.add(isolaEcologica4);
        arrayList.add(amiu5);

        title.add("Isola Ecologica");
        title.add("Isola Ecologica");
        title.add("Isola Ecologica");
        title.add("Azienda Multiservizi E D'Igiene Urbana S.P.A.");
        title.add("Azienda Multiservizi E D'Igiene Urbana S.P.A.");
        title.add("Ge. Am. Gestioni Ambientali S.P.A.");
        title.add("Azienda Multiservizi E D'Igiene Urbana S.P.A.");
        title.add("Azienda Multiservizi E D'Igiene Urbana S.P.A.");
        title.add("AMIU");
        title.add("AMIU");
        title.add("AMIU");
        title.add("AMIU");
        title.add("EcoGea");
        title.add("Ecolegno Genova in liquidazione");
        title.add("Ecopunto");
        title.add("Ecover");
        title.add("Docks Lanterna Spa");
        title.add("ALP Ecologia");
        title.add("Area Ecologica di Bogliasco");
        title.add("Manutencoop Facility Management Spa");
        title.add("SGOMBERIGENOVA");
        title.add("Spelta Rottami");
        title.add("Genova Maceri");
        title.add("Re.Vetro S.r.l.");
        title.add("Cancellieri Giuseppe Rottami Metallici");
        title.add("Ferrotrade");
        title.add("Isola Ecologica");
        title.add("AMIU");
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    if (location != null) {
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                        && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    googleMap.setMyLocationEnabled(true);
                                }
                                for(int i=0;i<arrayList.size();i++){
                                    for (int j=0;j<title.size();j++){
                                        googleMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))));
                                    }
                                }
                            }
                        });
                    }
                }
            });
        } else{
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        nMap= googleMap;
        for(int i=0;i<arrayList.size();i++){
            for (int j=0;j<title.size();j++){
                nMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))));
            }
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), 11f));
        }
    }


}