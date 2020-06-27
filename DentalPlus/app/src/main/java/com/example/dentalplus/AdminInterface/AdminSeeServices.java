package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Service;
import com.example.dentalplus.clase.ServiceAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminSeeServices extends AppCompatActivity  {

    ListView lv;

    DatabaseReference dbReference;

    ArrayList<Service> serviceList;
    public static ServiceAdapter adapter;
    static Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_services);

        Intent i=getIntent();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ServiceAdapter.services.clear();
        lv=(ListView)findViewById(R.id.lvservice);

        dbReference= FirebaseDatabase.getInstance().getReference("services");
        serviceList=new ArrayList<>();

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceList.clear();
                adapter.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Service service=ds.getValue(Service.class);
                    serviceList.add(service);
                    adapter.add(service);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter=new ServiceAdapter(getApplicationContext(),R.layout.service_listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                service=serviceList.get(position);

                Toast.makeText(AdminSeeServices.this,service.getServiceCode(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
