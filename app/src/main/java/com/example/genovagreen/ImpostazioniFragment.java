package com.example.genovagreen;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class ImpostazioniFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loadLocale();
        View view=inflater.inflate(R.layout.fragment_impostazioni, container,  false);

        Button changeLang=view.findViewById(R.id.lingua);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
        return view;
    }

    private void showChangeLanguageDialog() {
        final String[] listItems={"Italiano", "English"};
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(getContext());
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    setLocale("it");
                    int id=getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ImpostazioniFragment()).commit();
                    ImpostazioniFragment impostazioniFragment = (ImpostazioniFragment)
                            getActivity().getSupportFragmentManager()
                                    .findFragmentById(R.id.fragment_container);
                    final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.detach(impostazioniFragment);
                    fragmentTransaction.attach(impostazioniFragment);
                    fragmentTransaction.commit();

                }
                if(i==1){
                    setLocale("en");
                    int id=getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ImpostazioniFragment()).commit();
                    ImpostazioniFragment impostazioniFragment = (ImpostazioniFragment)
                            getActivity().getSupportFragmentManager()
                                    .findFragmentById(R.id.fragment_container);
                    final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.detach(impostazioniFragment);
                    fragmentTransaction.attach(impostazioniFragment);
                    fragmentTransaction.commit();

                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale= new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config,getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs=getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language=prefs.getString("My_Lang","");
        setLocale(language);
    }
}