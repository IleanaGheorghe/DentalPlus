package com.example.dentalplus.clase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dentalplus.DoctorInterface.fragment_addPatient;
import com.example.dentalplus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter  extends ArrayAdapter implements Filterable {
    public static List patientsAdapte=new ArrayList();
    public static PatientHolder patientEditare;

    DatabaseReference dbPatients= FirebaseDatabase.getInstance().getReference("patients");

    public PatientAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class PatientHandler{
        TextView nume;
        TextView telefon;
        TextView dataInreg;
    }
    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        patientsAdapte.add(object);
    }
    @Override
    public int getCount() {
        return patientsAdapte.size();
    }

    @Override
    public Object getItem(int position) {
        return patientsAdapte.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PatientHandler patientHandler;
        if (row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.patient_item,parent,false);

            patientHandler=new PatientHandler();
            patientHandler.nume=(TextView)row.findViewById(R.id.tvNumePat);
            patientHandler.telefon=(TextView)row.findViewById(R.id.tvTelefonPat);
            patientHandler.dataInreg=(TextView)row.findViewById(R.id.tvDataReg);

            row.setTag(patientHandler);
        }
        else patientHandler=(PatientHandler)row.getTag();

        final PatientHolder patientHolder=(PatientHolder)this.getItem(position);

        patientHandler.nume.setText("Nume: "+patientHolder.getPatientName());
        patientHandler.telefon.setText("Telefon: "+patientHolder.getPatientPhone());
        patientHandler.dataInreg.setText("Data inregistrare: "+patientHolder.getPatientRegisterDate());
        return  row;
    }

    CustomFilter cf;
    @NonNull
    @Override
    public Filter getFilter() {
        if (cf==null){
            cf=new CustomFilter();
        }
        return cf;
    }
    public static ArrayList<PatientHolder> filters;
    class CustomFilter extends  Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                constraint=constraint.toString().toUpperCase();

                filters=new ArrayList<>();

                for(int i = 0; i< fragment_addPatient.patientsH.size(); i++){
                    if(fragment_addPatient.patientsH.get(i).getPatientName().toUpperCase().contains(constraint)){
                        PatientHolder pa =new PatientHolder(fragment_addPatient.patientsH.get(i).getPatientName(), fragment_addPatient.patientsH.get(i).getPatientPhone(), fragment_addPatient.patientsH.get(i).getPatientRegisterDate());
                        filters.add(pa);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }
            else
            {
                results.count=fragment_addPatient.patientsH.size();
                results.values=fragment_addPatient.patientsH;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            patientsAdapte=(ArrayList<PatientHolder>)results.values;
            fragment_addPatient.patientAdapter.notifyDataSetChanged();
        }
    }
}
