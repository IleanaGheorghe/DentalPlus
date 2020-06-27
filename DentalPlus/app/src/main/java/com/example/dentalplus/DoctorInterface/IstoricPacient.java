package com.example.dentalplus.DoctorInterface;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.AppointmentAdapter;
import com.example.dentalplus.clase.AppointmentHolder;
import com.example.dentalplus.clase.Invoice;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.PatientAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IstoricPacient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IstoricPacient extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IstoricPacient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IstoricPacient.
     */
    // TODO: Rename and change types and number of parameters
    public static IstoricPacient newInstance(String param1, String param2) {
        IstoricPacient fragment = new IstoricPacient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView tvInfoPat,tvEditPat,tvIstoricPat,tvDeletePat;
    EditText etNumeEditPat,etTelefonEditPat,etEmailEditPat,etObservatiiEditPat;
    Button btnAPEL, btnEmail;

    ListView lvIstoric;

    DatabaseReference dbPatientHolder, dbAppointments, dbInvoices, dbRAppoint;
    ArrayList<Patient> pacienti;
    public static ArrayList<AppointmentHolder> appointments;
    public static ArrayList<Appointment> appointmentsI;
    public static ArrayList<Invoice> invoices;
    public static AppointmentAdapter appointmentAdapter;
    public static String appIstoric;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_istoric_pacient, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvInfoPat=(TextView)view.findViewById(R.id.tvInfoPat);
        tvEditPat=(TextView)view.findViewById(R.id.tvEditPat);
        tvIstoricPat=(TextView)view.findViewById(R.id.tvIstoricPat);
        tvDeletePat=(TextView)view.findViewById(R.id.tvDeletePat);

        etNumeEditPat=(EditText)view.findViewById(R.id.etNumeEditPat);
        etTelefonEditPat=(EditText)view.findViewById(R.id.etTelefonEditPat);
        etEmailEditPat=(EditText)view.findViewById(R.id.etEmailEditPat);
        etObservatiiEditPat=(EditText)view.findViewById(R.id.etObservatiiEditPat);

        btnAPEL=(Button)view.findViewById(R.id.btnAPEL);
        btnEmail=(Button)view.findViewById(R.id.btnEmail);

        lvIstoric=(ListView)view.findViewById(R.id.lvIstoric);

        etTelefonEditPat.setEnabled(false);

        for(Patient p: FirstActivityDoctor.patients){
            if(p.getPatientPhone().equals(fragment_addPatient.patient.getPatientPhone())){
                etTelefonEditPat.setText(p.getPatientPhone());
                etNumeEditPat.setText(p.getPatientName());
                etEmailEditPat.setText(p.getPatientEmail());
                etObservatiiEditPat.setText(p.getPatientObservations());
            }
        }

        btnAPEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: " +etTelefonEditPat.getText().toString()));
                startActivity(callIntent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , etEmailEditPat.getText().toString());
                i.putExtra(Intent.EXTRA_SUBJECT, "DentalPlus - info");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                i.setData(Uri.parse("mailto:"+etEmailEditPat.getText().toString()));
                try {
                    startActivity(Intent.createChooser(i, "Trimitere email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Nu sunt instalate aplicatii pentru trimitere email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dbPatientHolder= FirebaseDatabase.getInstance().getReference("patients").child(etTelefonEditPat.getText().toString());

        tvEditPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Sigur doriti sa editati acest pacient?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean ok=true;
                        if(etNumeEditPat.getText().toString().isEmpty()||etNumeEditPat.getText().toString().equals("")){
                            Toast.makeText(getActivity(),"Numele trebuie completat!",Toast.LENGTH_SHORT).show();
                            ok=false;
                        }
                        if(etEmailEditPat.getText().toString().isEmpty()||etEmailEditPat.getText().toString().equals("")|| !Patterns.EMAIL_ADDRESS.matcher(etEmailEditPat.getText().toString()).matches()){
                            Toast.makeText(getActivity(), "Completati cu un email valid!", Toast.LENGTH_SHORT).show();
                            ok=false;
                        }

                        if(ok==true){
                            dbPatientHolder.child("patientEmail").setValue(etEmailEditPat.getText().toString());
                            dbPatientHolder.child("patientObservations").setValue(etObservatiiEditPat.getText().toString());
                            dbPatientHolder.child("patientName").setValue(etNumeEditPat.getText().toString());

                            Toast.makeText(getActivity(),"Pacientul a fost actualizat cu succesc!",Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Editare pacient");
                alertDialog.show();
            }
        });

        AppointmentAdapter.appointmentAdapte.clear();

        dbAppointments=FirebaseDatabase.getInstance().getReference("appointments");
        appointments=new ArrayList<>();
        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                appointmentAdapter.clear();
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    AppointmentHolder app=ds.getValue(AppointmentHolder.class);
                    if (app.getPatient().equals(etTelefonEditPat.getText().toString())) {
                        appointmentAdapter.add(app);
                        appointments.add(app);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbInvoices=FirebaseDatabase.getInstance().getReference("invoices");
        invoices=new ArrayList<>();
        dbInvoices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Invoice inv=ds.getValue(Invoice.class);
                    if (inv.getPatient().equals(etTelefonEditPat.getText().toString())){
                        invoices.add(inv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRAppoint=FirebaseDatabase.getInstance().getReference("appointments");
        appointmentsI=new ArrayList<>();
        dbRAppoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentsI.clear();
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    Appointment a=ds.getValue(Appointment.class);
                    appointmentsI.add(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        appointmentAdapter=new AppointmentAdapter(getContext(),R.layout.appointment_item);
        lvIstoric.setAdapter(appointmentAdapter);

        lvIstoric.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.codFragment=2;
                appIstoric=appointments.get(position).getAppId();
                Fragment fragment=null;
                fragment=new EditAppointment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        tvDeletePat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Sigur doriti sa stergeti acest pacient?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for(AppointmentHolder a:appointments){
                            dbAppointments.child(a.getAppId()).removeValue();
                        }

                        for(Invoice i:invoices){
                            dbInvoices.child(i.getAppointmentNr()).removeValue();
                        }

                        dbPatientHolder.removeValue();
                        Toast.makeText(getActivity(),"Pacientul a fost sters!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Fragment fragment=null;
                        fragment=new fragment_addPatient();
                        PatientAdapter.patientsAdapte.clear();
                        fragment_addPatient.patientAdapter.clear();
                        fragment_addPatient.patientsH.clear();
                        MainActivity.patients1.clear();
                        FirstActivityDoctor.patients.clear();
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container,fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Stergere pacient");
                alertDialog.show();
            }
        });
        return view;
    }
}
