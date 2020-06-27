package com.example.dentalplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.AdminInterface.LogInAdministrator;
import com.example.dentalplus.DoctorInterface.FirstActivityDoctor;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp, bntLogIn;
    EditText etUser, etPassword;
    TextView tvForgetPassword;
    CheckBox rememberMe;

    DatabaseReference dbDoctors;
    private static String PREF = "PREF";

    public static ArrayList<Doctor> doctorList;
    public static Doctor doctorLogin=new Doctor();

    DatabaseReference dbPatients;
    public static ArrayList<Patient> patients1;
    public static int codFragment;

    DatabaseReference dbappointments;
    public static ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnSignUp =(Button)findViewById(R.id.btnSignUp);
        bntLogIn=(Button) findViewById(R.id.btnLogIn);
        etUser=(EditText) findViewById(R.id.etUsernameMA);
        etPassword=(EditText) findViewById(R.id.etPassword);
        tvForgetPassword=(TextView) findViewById(R.id.tvForgetPassword);
        rememberMe=(CheckBox)findViewById(R.id.cbrememberMe);

        receiveData();
        dbDoctors=FirebaseDatabase.getInstance().getReference("doctors");
        doctorList=new ArrayList<>();

        dbPatients= FirebaseDatabase.getInstance().getReference("patients");
        patients1=new ArrayList<>();
        dbPatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patients1.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Patient patient=ds.getValue(Patient.class);
                    patients1.add(patient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbappointments=FirebaseDatabase.getInstance().getReference("appointments");
        appointments=new ArrayList<>();
        dbappointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Appointment a=ds.getValue(Appointment.class);
                    appointments.add(a);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbservices;
                dbservices=FirebaseDatabase.getInstance().getReference("services");
                dbservices.child("srv1").child("serviceCode").setValue("srv1");
                dbservices.child("srv1").child("serviceName").setValue("Control");
                dbservices.child("srv1").child("servicePrice").setValue("50");
                Intent intent=new Intent(getApplicationContext(), LogInAdministrator.class);
                startActivity(intent);
            }
        });

        dbDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Doctor doc = (Doctor) ds.getValue(Doctor.class);
                    doctorList.add(doc);
                }
                bntLogIn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String strPassword = etPassword.getText().toString();
                        String strUsername = etUser.getText().toString();

                        for (Doctor user : doctorList) {
                            if (etUser.getText().toString().equals(user.getUsername())) {
                                doctorLogin = user;
                                break;
                            } else doctorLogin.setPassword("");
                        }

                        if (doctorLogin.isEnabled() == true) {
                            if (etUser.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                                Toast.makeText(MainActivity.this, "Toate campurile trebuie completate!", Toast.LENGTH_SHORT).show();
                            } else if (etPassword.getText().toString().equals(doctorLogin.getPassword())) {
                                if (rememberMe.isChecked()) {
                                    saveData();
                                } else {
                                    etUser.setText("");
                                    etPassword.setText("");
                                    saveData();
                                }
                                Intent intent = new Intent(getApplicationContext(), FirstActivityDoctor.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Sigur aveti cont de utilizator?", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(MainActivity.this, "Cont dezactivat. Contatati administratorii!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Pentru a reseta parola, va rugam ca contactati administratorii.\n" +
                        "Numar de telefon: 0758074764 (Echipa DentalPlus)");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Resetare parola");
                alertDialog.show();
            }
        });

    }

    public void receiveData() {
        SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String user_name = preferences.getString("USERNAME", null);
        String pass_word = preferences.getString("PASSWORD", null);

        etUser.setText(user_name);
        etPassword.setText(pass_word);
    }

    public void saveData() {
        String user_name = etUser.getText().toString();
        String pass_word = etPassword.getText().toString();

        SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("USERNAME", user_name);
        editor.putString("PASSWORD", pass_word);
        editor.commit();
    }

}
