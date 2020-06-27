package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Specialization;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAddSpecialization extends AppCompatActivity {

    EditText etCodeSpec, etNameSpec;
    Button btnAddSpec;

    DatabaseReference dbReference;
    ArrayList<Specialization> specializations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_specialization);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        etCodeSpec=(EditText)findViewById(R.id.etCodeSpec);
        etNameSpec=(EditText)findViewById(R.id.etNameSpec);
        btnAddSpec=(Button)findViewById(R.id.btnAddSpeci);

        specializations=new ArrayList<>();

        dbReference= FirebaseDatabase.getInstance().getReference("specializations");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                specializations.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Specialization specialization=ds.getValue(Specialization.class);
                    specializations.add(specialization);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAddSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=etCodeSpec.getText().toString();
                String name =etNameSpec.getText().toString();

                if(etCodeSpec.equals("")|| etNameSpec.equals("")||code.equals("Cod specializare : spc1, spc2..")|| name.equals("Nume specializare")||code.isEmpty()||name.isEmpty()){
                    Toast.makeText(AdminAddSpecialization.this,"Toate campurile trebuie completate!",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean okcode=true, okname=true;
                    for (Specialization spec :specializations){
                        if(etCodeSpec.getText().toString().equals(spec.getSpecializationCode())){
                            Toast.makeText(AdminAddSpecialization.this, "Acest cod de specializare exista", Toast.LENGTH_SHORT).show();
                            okcode=false;
                            break;
                        } else if (etNameSpec.getText().toString().equals(spec.getSpecializationName())){
                            Toast.makeText(AdminAddSpecialization.this,"Aceasta denumire de specializare exista ", Toast.LENGTH_SHORT).show();
                            okname=false;
                        }
                    }

                    if(okcode==true && okname==true){
                            dbReference.child(code).child("specializationCode").setValue(code);
                            dbReference.child(code).child("specializationName").setValue(name);

                            etNameSpec.setText("");
                            etCodeSpec.setText("");

                            Toast.makeText(AdminAddSpecialization.this,"Specializare adaugata cu succes!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
