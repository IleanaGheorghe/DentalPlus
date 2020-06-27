package com.example.dentalplus.clase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dentalplus.AdminInterface.AdminSeeServices;
import com.example.dentalplus.AdminInterface.AdminSeeUsers;
import com.example.dentalplus.AdminInterface.AdminUpdateService;
import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter  extends ArrayAdapter {
    public static List services=new ArrayList();
    public static Service serviceEditate;

    DatabaseReference dbServices = FirebaseDatabase.getInstance().getReference("services");

    public ServiceAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class ServiceHandler {
        TextView code;
        TextView name;
        TextView price;
        Button editare;
        Button stergere;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        services.add(object);
    }
    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ServiceHandler serviceHandler;

        if (row==null){
           LayoutInflater layoutInflater=(LayoutInflater)this.getContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           row=layoutInflater.inflate(R.layout.service_listview,parent,false);
            serviceHandler=new ServiceHandler();
            serviceHandler.code=(TextView)row.findViewById(R.id.tvCodServ);
            serviceHandler.name=(TextView)row.findViewById(R.id.tvNumeServ);
            serviceHandler.price=(TextView)row.findViewById(R.id.tvPretServ);
            serviceHandler.editare=(Button)row.findViewById(R.id.buttonEditare);
            serviceHandler.stergere=(Button)row.findViewById(R.id.buttonStergere);

            row.setTag(serviceHandler);
        } else serviceHandler=(ServiceHandler)row.getTag();

        final Service service=(Service)this.getItem(position);

        serviceHandler.code.setText("Cod serviciu: "+service.getServiceCode());
        serviceHandler.name.setText("Nume serviciu: "+service.getServiceName());
        serviceHandler.price.setText("Pret serviciu: "+service.getServicePrice());

        serviceHandler.stergere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean ok=true;
                for(Appointment a: MainActivity.appointments){
                    if(a.getServiceCode().equals(service.getServiceCode())){
                        ok=false;
                        break;
                    }
                }
                if (ok==true){
                    services.clear();
                    dbServices.child(service.getServiceCode()).removeValue();
                    AdminSeeServices.adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Serviciul a fost sters", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Acest serviciu se gaseste pe programari. Nu poate fi sters!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        serviceHandler.editare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceEditate= (Service) services.get(position);
                Intent intent=new Intent(getContext(), AdminUpdateService.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

        return row;
    }
}

