package com.example.genovagreen;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Registrazione extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
}
/* private EditText emailAdd, password, password2;
    private Button button;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registrazione, container, false);

        auth=FirebaseAuth.getInstance();
        emailAdd=view.findViewById(R.id.EmailAddress);
        password=view.findViewById(R.id.Password);
        password2=view.findViewById(R.id.RepeatPassword);
        button=view.findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailAdd.getText().toString();
                String pass=password.getText().toString();
                String pass2=password2.getText().toString();
                if(!email.isEmpty()&&!pass.isEmpty()&&!pass2.isEmpty()){
                    if(pass.equals(pass2)){
                        if(password.length()>5){
                            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        auth.signOut();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                new LoginFragment()).commit();
                                    }else{
                                        Toast.makeText(getContext(), "Qualcosa è andato storto con la registrazione",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Errore "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Toast.makeText(getContext(), "Riprova",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), "La password deve avere almeno 6 caratteri",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getContext(), "Password e Conferma password non corrispondono",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Non possono esserci campi vuoti",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }