package com.example.dentalplus.clase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dentalplus.DoctorInterface.FirstActivityDoctor;
import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends ArrayAdapter {
    public static List appointmentAdapte=new ArrayList();
    public static AppointmentHolder appointmentEditare;

    DatabaseReference dbAppointments= FirebaseDatabase.getInstance().getReference("appointments");

    public AppointmentAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class AppointmentHandler{
        TextView nume;
        TextView data;
        TextView ora;
        TextView serviciu;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        appointmentAdapte.add(object);
    }

    @Override
    public int getCount() {
        return appointmentAdapte.size();
    }

    @Override
    public Object getItem(int position) {
        return appointmentAdapte.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AppointmentHandler appointmentHandler;
        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.appointment_item,parent,false);

            appointmentHandler=new AppointmentHandler();
            appointmentHandler.nume=(TextView)row.findViewById(R.id.tvAppNumePat);
            appointmentHandler.data=(TextView)row.findViewById(R.id.tvAppDataProg);
            appointmentHandler.ora=(TextView)row.findViewById(R.id.tvAppOraSE);
            appointmentHandler.serviciu=(TextView)row.findViewById(R.id.tvAppServiciu);
            row.setTag(appointmentHandler);
        } else appointmentHandler=(AppointmentHandler)row.getTag();

        final AppointmentHolder app=(AppointmentHolder) this.getItem(position);

        String pacient=null;
        for(Patient p: FirstActivityDoctor.patients){
            if (p.getPatientPhone().equals(app.getPatient())){
                pacient=p.getPatientName();
                app.setPatient(p.getPatientName());
            }
        }

        String serviciu=null;
        for(Service s: FirstActivityDoctor.services){
            if(s.getServiceCode().equals(app.getServiceCode())){
                serviciu=s.getServiceName();
                app.setServiceCode(s.getServiceName());
            }
        }

        appointmentHandler.nume.setText("Nume pacient: "+app.getPatient());
        appointmentHandler.serviciu.setText("Serviciu: "+app.getServiceCode());
        appointmentHandler.ora.setText("Ore: "+app.getStartH()+" - "+app.getEndH());
        appointmentHandler.data.setText("Data programare: "+app.getDate());

        return row;
    }

}
