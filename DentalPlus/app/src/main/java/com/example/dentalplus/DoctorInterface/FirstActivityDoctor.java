package com.example.dentalplus.DoctorInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.PatientHolder;
import com.example.dentalplus.clase.Service;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirstActivityDoctor extends AppCompatActivity {


    DatabaseReference dbPatients, dbService;
    public static ArrayList<Patient> patients;
    public static ArrayList<PatientHolder> patientHolders;
    public static ArrayList<Service> services;
    public static String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_doctor);

        Intent intent=getIntent();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dbPatients= FirebaseDatabase.getInstance().getReference("patients");
        patients=new ArrayList<>();
        patientHolders=new ArrayList<>();
        dbPatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patients.clear();
                patientHolders.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Patient patient=ds.getValue(Patient.class);
                    PatientHolder patient1=ds.getValue(PatientHolder.class);
                    patients.add(patient);
                    patientHolders.add(patient1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbService=FirebaseDatabase.getInstance().getReference("services");
        services=new ArrayList<>();
        dbService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Service service=ds.getValue(Service.class);
                    services.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_addPatient()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_appointments:
                            selectedFragment = new fragment_AddAppoinments();

                            break;
                        case R.id.nav_invoices:
                            selectedFragment = new fragment_addInvoices();

                            break;
                        case R.id.nav_reports:
                            selectedFragment = new fragment_SeeReports();

                            break;
                        case R.id.nav_settings:
                            selectedFragment = new fragment_settings();

                            break;
                        case R.id.nav_patients:
                            selectedFragment = new fragment_addPatient();

                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
    }
