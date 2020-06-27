package com.example.dentalplus.clase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dentalplus.AdminInterface.AdminSeeServices;
import com.example.dentalplus.AdminInterface.AdminSeeSpecialization;
import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpecializationAdapter extends ArrayAdapter {

    public static List list=new ArrayList();
    DatabaseReference dbSpecializations, dbDoctors;
    ArrayList<Doctor> doctors;

    public SpecializationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    static class SpecializationHandler
    {
        TextView code;
        TextView name;
        Button stergere;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        SpecializationHandler specializationHandler;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.specialization_listview,parent,false);
            specializationHandler=new SpecializationHandler();
            specializationHandler.code=(TextView)row.findViewById(R.id.tvCodSpec);
            specializationHandler.name=(TextView)row.findViewById(R.id.tvNumeSpec);
            specializationHandler.stergere=(Button) row.findViewById(R.id.buttonStergereSpec);

            row.setTag(specializationHandler);

        }
        else{
            specializationHandler=(SpecializationHandler) row.getTag();

        }
        final Specialization specializationHolder= (Specialization) this.getItem(position);
        specializationHandler.code.setText("Cod specializare: "+specializationHolder.getSpecializationCode());
        specializationHandler.name.setText("Nume specializare: "+specializationHolder.getSpecializationName());
        specializationHandler.stergere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctors=new ArrayList<>();
                dbDoctors=FirebaseDatabase.getInstance().getReference("doctors");
                dbDoctors.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        doctors.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Doctor doc = (Doctor) ds.getValue(Doctor.class);
                            doctors.add(doc);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                boolean ok=true;
                for (Doctor d: MainActivity.doctorList){
                    if(specializationHolder.getSpecializationCode().equals(d.getSpecCode())){
                        ok=false;
                    }
                }
                if (ok){
                list.clear();
                dbSpecializations= FirebaseDatabase.getInstance().getReference("specializations");
                dbSpecializations.child(specializationHolder.getSpecializationCode()).removeValue();
                AdminSeeSpecialization.specializationAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Specializarea a fost stearsa!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Exista doctori cu aceasta specializare!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return row;
    }
}
