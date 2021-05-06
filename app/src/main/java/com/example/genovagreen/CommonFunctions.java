package com.example.genovagreen;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommonFunctions {
    public static void onNavigationItemSelected(MenuItem item, View v, FirebaseUser user, DrawerLayout drawer) {
        switch (item.getItemId()){
            case R.id.content_main:
                v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
                break;
            case R.id.content_butto:
                v.getContext().startActivity(new Intent(v.getContext(), Butto.class));
                break;
            case R.id.content_pericolosi:
                v.getContext().startActivity(new Intent(v.getContext(), Pericolosi.class));
                break;
            case R.id.content_spedizioni:
                if(user!=null && user.isEmailVerified()) {
                    v.getContext().startActivity(new Intent(v.getContext(), Spedizioni2.class));
                }else{
                    v.getContext().startActivity(new Intent(v.getContext(), Spedizioni.class));
                }
                break;
            case R.id.content_impostazioni:
                v.getContext().startActivity(new Intent(v.getContext(), Impostazioni.class));
                break;
            case R.id.content_informazioni:
                v.getContext().startActivity(new Intent(v.getContext(), Informazioni.class));
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout dl) {
        if(dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        }
    }

    public static void setUsername(TextView username, TextView id, NavigationView navigationView, FirebaseUser user) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Usernames");
        if(user!=null) {
            username.setVisibility(View.VISIBLE);
            id.setVisibility(View.VISIBLE);
            final String email = user.getEmail().trim();
            if (ref != null) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User ogg = ds.getValue(User.class);
                                String email2 = ogg.getEmail().trim();
                                String nomeutente = ogg.getUsername();
                                if (email.equals(email2)) {
                                    username.setText(nomeutente);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }
}
