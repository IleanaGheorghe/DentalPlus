package com.example.dentalplus.DoctorInterface;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.PatientAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealAddPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealAddPatient extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RealAddPatient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RealAddPatient.
     */
    // TODO: Rename and change types and number of parameters
    public static RealAddPatient newInstance(String param1, String param2) {
        RealAddPatient fragment = new RealAddPatient();
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

    TextView tvNumeAdd, tvEmailAdd, tvPhoneAdd, tvBirthDateAdd, tvObservationsAdd;
    EditText etNumeAdd, etEmailAdd, etTelefonAdd, etBirthDateAdd, etObservationsAdd;
    RadioButton rbFemininAdd, rbMasculinAdd;
    Button btnAddPatient;
    private DatePickerDialog.OnDateSetListener datePickerDialog;

    boolean okGender=true;
    String gender;
    ArrayList<Patient> patients;

    DatabaseReference dbReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_real_add_patient, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvNumeAdd=(TextView)view.findViewById(R.id.tvNumeAdd);
        tvEmailAdd=(TextView)view.findViewById(R.id.tvEmailAdd);
        tvPhoneAdd=(TextView)view.findViewById(R.id.tvPhoneAdd);
        tvBirthDateAdd=(TextView)view.findViewById(R.id.tvBirthDateAdd);
        tvObservationsAdd=(TextView)view.findViewById(R.id.tvObservationsAdd);

        etNumeAdd=(EditText) view.findViewById(R.id.etNumeAdd);
        etEmailAdd=(EditText) view.findViewById(R.id.etEmailAdd);
        etTelefonAdd=(EditText)view.findViewById(R.id.etTelefonAdd);
        etBirthDateAdd=(EditText)view.findViewById(R.id.etBirthDateAdd);
        etObservationsAdd=(EditText)view.findViewById(R.id.etObservationsAdd);
        btnAddPatient=(Button)view.findViewById(R.id.btnAddPatient);

        rbFemininAdd=(RadioButton)view.findViewById(R.id.rbFemininAdd);
        rbMasculinAdd=(RadioButton)view.findViewById(R.id.rbMasculinAdd);

        etBirthDateAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerDialog,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.show();
            }
        });

        datePickerDialog=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"-"+month+"-"+year;
                etBirthDateAdd.setText(date);
            }
        };

        patients=new ArrayList<>();
        dbReference= FirebaseDatabase.getInstance().getReference("patients");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patients.clear();

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Patient patient=ds.getValue(Patient.class);
                    patients.add(patient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbFemininAdd.isChecked()){
                    okGender=true;
                    gender="Feminin";
                } else if (rbMasculinAdd.isChecked()){
                    okGender=true;
                    gender="Masculin";
                } else if (!(rbFemininAdd.isChecked()) || (rbMasculinAdd.isChecked())){
                    okGender=false;
                    Toast.makeText(getActivity(), "Trebuie selectat genul!", Toast.LENGTH_SHORT).show();
                }

                if(okGender==true){
                    String nume=etNumeAdd.getText().toString();
                    String email=etEmailAdd.getText().toString();
                    String telefon=etTelefonAdd.getText().toString();
                    String dataN=etBirthDateAdd.getText().toString();
                    String obs=etObservationsAdd.getText().toString();
                    Date c=Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
                    String registDate=df.format(c);
                    //Toast.makeText(getActivity(),registDate,Toast.LENGTH_SHORT).show();

                    if(nume.equals("")|| email.equals("")||telefon.equals("")||dataN.equals("")||obs.equals("")
                            ||nume.isEmpty()||email.isEmpty()||telefon.isEmpty()||dataN.isEmpty()||obs.isEmpty()){
                        Toast.makeText(getActivity(), "Toate campurile trebuie completate!",Toast.LENGTH_SHORT).show();
                    } else{
                        if(telefon.length()>10 ||telefon.length()<10)
                        {
                            Toast.makeText(getActivity(), "Numarul de telefon trebuie sa aiba 10 cifre!",Toast.LENGTH_SHORT).show();
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(getActivity(), "Introduceti un email valid!",Toast.LENGTH_SHORT).show();
                        } else{
                            boolean okPatient=true;
                            for (Patient patient : patients) {
                                if (etTelefonAdd.getText().toString().equals(patient.getPatientPhone())) {
                                    Toast.makeText(getActivity(), "Exista deja un pacient cu acest numar de telefon!", Toast.LENGTH_SHORT).show();
                                    okPatient=false;
                                    break;
                                }
                            }
                            if(okPatient==true){
                                dbReference.child(telefon).child("patientBirthDate").setValue(dataN);
                                dbReference.child(telefon).child("patientName").setValue(nume);
                                dbReference.child(telefon).child("patientEmail").setValue(email);
                                dbReference.child(telefon).child("patientPhone").setValue(telefon);
                                dbReference.child(telefon).child("patientObservations").setValue(obs);
                                dbReference.child(telefon).child("patientSex").setValue(gender);
                                dbReference.child(telefon).child("patientRegisterDate").setValue(registDate);

                                etTelefonAdd.setText(""); etBirthDateAdd.setText("");
                                etEmailAdd.setText("");etNumeAdd.setText("");etObservationsAdd.setText("");
                                Toast.makeText(getActivity(), "Pacientul a fost adaugat!", Toast.LENGTH_SHORT).show();
                                Fragment fragment=null;
                                if(v== v.findViewById(R.id.btnAddPatient)){
                                    fragment=new fragment_addPatient();
                                }
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
                        }
                    }

                }
            }
        });
        return  view;
    }
}
