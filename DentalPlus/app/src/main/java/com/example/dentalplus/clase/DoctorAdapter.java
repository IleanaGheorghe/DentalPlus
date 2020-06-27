package com.example.dentalplus.clase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dentalplus.AdminInterface.AdminActivity;
import com.example.dentalplus.AdminInterface.AdminSeeUsers;
import com.example.dentalplus.AdminInterface.AdminUpdateDoctor;
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
import java.util.logging.Handler;

public class DoctorAdapter  extends ArrayAdapter {
    public static List doctorsAdapter=new ArrayList<>();
    public static Doctor doctorEditare;

    DatabaseReference dbDoctors= FirebaseDatabase.getInstance().getReference("doctors");


    public DoctorAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class DoctorHandler{
        TextView nume;
        TextView prenume;
        TextView utilizator;
        TextView email;
        TextView parola;
        TextView telefon;
        TextView dataNastere;
        TextView salariu;
        TextView gen;
        TextView contActiv;
        TextView specialitate;
        Button stergere;
        Button editare;
    }
    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        doctorsAdapter.add(object);
    }
    @Override
    public int getCount() {
        return doctorsAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorsAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        DoctorHandler doctorHandler;

        if (row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.doctor_listview,parent,false);
            doctorHandler=new DoctorHandler();
            doctorHandler.nume=(TextView)row.findViewById(R.id.tvlnameDoc);
            doctorHandler.prenume=(TextView)row.findViewById(R.id.tbfnameDoc);
            doctorHandler.utilizator=(TextView)row.findViewById(R.id.tvNumeUtilizatorDoc);
            doctorHandler.email=(TextView)row.findViewById(R.id.tvEmailDoc);
            doctorHandler.parola=(TextView)row.findViewById(R.id.tvParolaDoc);
            doctorHandler.telefon=(TextView)row.findViewById(R.id.tvNrTlfDoc);
            doctorHandler.dataNastere=(TextView)row.findViewById(R.id.tvDataNastereDoc);
            doctorHandler.salariu=(TextView)row.findViewById(R.id.tvSalariulDoc);
            doctorHandler.specialitate=(TextView)row.findViewById(R.id.tvSpecDoc);
            doctorHandler.gen=(TextView)row.findViewById(R.id.tvGenDoc);
            doctorHandler.contActiv=(TextView)row.findViewById(R.id.tvContActiv);
            doctorHandler.stergere=(Button)row.findViewById(R.id.buttonStergereDoc);
            doctorHandler.editare=(Button)row.findViewById(R.id.buttonEditareDoc);

            row.setTag(doctorHandler);
        } else doctorHandler=(DoctorHandler)row.getTag();

        final Doctor doctor=(Doctor) this.getItem(position);
        String special=null;
        for (Specialization spec: AdminActivity.specializations){
            if(spec.getSpecializationCode().equals(doctor.getSpecCode())){
                special=spec.getSpecializationName();
            }
        }
        doctorHandler.nume.setText("Nume: "+doctor.getLastName());
        doctorHandler.prenume.setText("Prenume: "+doctor.getFirstName());
        doctorHandler.utilizator.setText("Nume utilizator: "+doctor.getUsername());
        doctorHandler.specialitate.setText("Specializare: "+special);
        doctorHandler.parola.setText("Parola: "+doctor.getPassword());
        doctorHandler.telefon.setText("Telefon: "+doctor.getPhone());
        doctorHandler.dataNastere.setText("Data nastere: "+doctor.getBirthDate());
        doctorHandler.gen.setText("Gen: "+doctor.getGender());
        doctorHandler.salariu.setText("Salariul: "+doctor.getSalary());
        doctorHandler.email.setText("Email: "+doctor.getEmail());
        String cont=null;
        if (doctor.isEnabled()) cont="Activ";
        else cont="Inactiv";
        doctorHandler.contActiv.setText("Cont: "+cont);

        doctorHandler.stergere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok=true;
                for(Appointment a: MainActivity.appointments){
                    if(a.getDoctor().equals(doctor.getUsername())){
                        ok=false;
                        break;
                    }
                }
                if (ok==true){
                    doctorsAdapter.clear();
                    dbDoctors.child(doctor.getUsername()).removeValue();
                    AdminSeeUsers.adapterUser.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Doctorul a fost sters cu succes!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Doctorii care au programari nu pot fi stersi din sistem!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        doctorHandler.editare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorEditare= (Doctor) doctorsAdapter.get(position);
                Intent intent=new Intent(getContext(), AdminUpdateDoctor.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

        return row;
    }
}
