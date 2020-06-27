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
import com.example.dentalplus.clase.Service;
import com.example.dentalplus.clase.Specialization;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAddService extends AppCompatActivity {

    EditText etCodeSrv, etNameSrv, etPriceSrv;
    Button btnAddServ;

    DatabaseReference dbReference;
    ArrayList<Service> services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_service);

        Intent intent1= getIntent();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        etCodeSrv=(EditText)findViewById(R.id.etCodeServ);
        etNameSrv=(EditText)findViewById(R.id.etNameSrv);
        etPriceSrv=(EditText)findViewById(R.id.etPriceSrv);
        btnAddServ=(Button)findViewById(R.id.btnAddServ);

        services=new ArrayList<>();

        dbReference= FirebaseDatabase.getInstance().getReference("services");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Service service=ds.getValue(Service.class);
                    services.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnAddServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=etCodeSrv.getText().toString();
                String name =etNameSrv.getText().toString();
                String price=etPriceSrv.getText().toString();
                if(etCodeSrv.equals("")|| etNameSrv.equals("") || etPriceSrv.equals("")||code.isEmpty()||name.isEmpty()||price.isEmpty()){
                    Toast.makeText(AdminAddService.this,"Toate campurile trebuie completate!",Toast.LENGTH_LONG).show();
                }
                else {
                    boolean okName=true, okCode=true;
                    for (Service spec :services){
                        if(etCodeSrv.getText().toString().equals(spec.getServiceCode())){
                            Toast.makeText(AdminAddService.this, "Acest cod de serviciu exista", Toast.LENGTH_SHORT).show();
                            okCode=false;
                            break;
                        } else if (etNameSrv.getText().toString().equals(spec.getServiceName())){
                            Toast.makeText(AdminAddService.this,"Aceasta denumire de serviciu exista ", Toast.LENGTH_SHORT).show();
                            okName=false;
                        }
                    }
                    if (okCode==true && okName==true){
                            dbReference.child(code).child("serviceCode").setValue(code);
                            dbReference.child(code).child("serviceName").setValue(name);
                            dbReference.child(code).child("servicePrice").setValue(price);
                            etNameSrv.setText("");
                            etCodeSrv.setText("");
                            etPriceSrv.setText("");
                            Toast.makeText(AdminAddService.this,"Serviciu adaugat cu succes!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
