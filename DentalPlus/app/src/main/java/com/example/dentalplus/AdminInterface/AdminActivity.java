package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Specialization;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    Button btnCreateUser, btnSeeUsers, btnAddAservice, bntSeeServices, btnLogOut, btnAddSpecialization, btnSeeSpecialization;

    DatabaseReference dbSpecialization;
    public static ArrayList<Specialization> specializations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnCreateUser=(Button) findViewById(R.id.btnCreateNewDoctor);
        btnSeeUsers=(Button) findViewById(R.id.bntListUsers);
        btnAddAservice=(Button)findViewById(R.id.btnAddServices);
        bntSeeServices=(Button)findViewById(R.id.btnSeeServices);
        btnAddSpecialization=(Button)findViewById(R.id.btnAddSpecialization);
        btnSeeSpecialization=(Button)findViewById(R.id.btnSeeSpecialization);
        btnLogOut=(Button)findViewById(R.id.btnLogOutAdmin);

        specializations=new ArrayList<>();

        dbSpecialization= FirebaseDatabase.getInstance().getReference("specializations");
        dbSpecialization.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                specializations.clear();

                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    Specialization specialization=ds.getValue(Specialization.class);
                    specializations.add(specialization);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CreateUserAdmin.class);
                startActivity(intent);
            }
        });

        btnAddSpecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), AdminAddSpecialization.class);
                startActivity(intent1);
            }
        });

        btnAddAservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(), AdminAddService.class);
                startActivity(intent2);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AdminActivity.this);
                builder.setMessage("Sunteti sigur ca vreti sa va deconectati?");
                builder.setPositiveButton("Deconectare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.setTitle("Deconectare");
                alertDialog.show();
            }
        });

        bntSeeServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getApplicationContext(), AdminSeeServices.class);
                startActivity(intent3);
            }
        });

        btnSeeSpecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(getApplicationContext(),AdminSeeSpecialization.class);
                startActivity(intent4);
            }
        });

        btnSeeUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(getApplicationContext(),AdminSeeUsers.class);
                startActivity(intent5);
            }
        });
    }
}
