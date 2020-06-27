package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Specialization;
import com.example.dentalplus.clase.SpecializationAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSeeSpecialization extends AppCompatActivity {

   public static  ListView lvSpecialization;
    DatabaseReference dbSpecializations;

    public static SpecializationAdapter specializationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_specialization);

        Intent i=getIntent();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SpecializationAdapter.list.clear();

        lvSpecialization=(ListView)findViewById(R.id.lvSpecialization);

        dbSpecializations= FirebaseDatabase.getInstance().getReference("specializations");

        dbSpecializations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                specializationAdapter.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Specialization spec=ds.getValue(Specialization.class);
                    specializationAdapter.add(spec);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        specializationAdapter=new SpecializationAdapter(getApplicationContext(),R.layout.specialization_listview);
        lvSpecialization.setAdapter(specializationAdapter);
    }
}
