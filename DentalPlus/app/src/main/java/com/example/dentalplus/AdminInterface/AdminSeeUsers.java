package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.DoctorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminSeeUsers extends AppCompatActivity {

    ListView lvDoctor;
    DatabaseReference dbDoctors;

    ArrayList<Doctor> doctorList;
    public static DoctorAdapter adapterUser;
    static Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_users);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        DoctorAdapter.doctorsAdapter.clear();
        lvDoctor=(ListView)findViewById(R.id.lvDoctor);
        dbDoctors= FirebaseDatabase.getInstance().getReference("doctors");
        doctorList=new ArrayList<>();
        dbDoctors.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapterUser.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Doctor doctor=ds.getValue(Doctor.class);
                    adapterUser.add(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapterUser=new DoctorAdapter(getApplicationContext(), R.layout.doctor_listview);
        lvDoctor.setAdapter(adapterUser);
    }
}
