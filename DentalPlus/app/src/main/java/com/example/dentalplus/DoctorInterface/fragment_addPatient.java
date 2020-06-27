package com.example.dentalplus.DoctorInterface;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.PatientAdapter;
import com.example.dentalplus.clase.PatientHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_addPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_addPatient extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_addPatient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_addPatient.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_addPatient newInstance(String param1, String param2) {
        fragment_addPatient fragment = new fragment_addPatient();
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

    public TextView tvAddPat, tvDatabase, tvPatAct;
    EditText etCautaNume;
    ListView lvPatients;
    public static ArrayList<PatientHolder> patientsH;
    public static PatientAdapter patientAdapter;
    public static PatientHolder patient;


    DatabaseReference dbPatients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_patient, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvAddPat = (TextView) view.findViewById(R.id.tvAddPat);
        tvDatabase= (TextView)view.findViewById(R.id.tvPacienti);
        tvPatAct=(TextView)view.findViewById(R.id.tvPatAct);
        etCautaNume=(EditText)view.findViewById(R.id.etCautaNume);
        lvPatients=(ListView)view.findViewById(R.id.lvPatients);
        patientsH=new ArrayList<>();


        PatientAdapter.patientsAdapte.clear();
        dbPatients=FirebaseDatabase.getInstance().getReference("patients");
        dbPatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientAdapter.clear();
                patientsH.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    PatientHolder pat=ds.getValue(PatientHolder.class);
                    patientAdapter.add(pat);
                    patientsH.add(pat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        etCautaNume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                patientAdapter.getFilter().filter(s);
                patientAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        patientAdapter=new PatientAdapter(getContext(), R.layout.patient_item);
        lvPatients.setAdapter(patientAdapter);

        lvPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //patient=FirstActivityDoctor.patients.get(position);
               /* if (PatientAdapter.filters.isEmpty()){
                    patient = (PatientHolder) patientAdapter.getItem(position);
                }else {
                    patient=PatientAdapter.filters.get(position);
                }*
                */
               patient= (PatientHolder) PatientAdapter.patientsAdapte.get(position);
                Fragment fragment=null;
                fragment=new IstoricPacient();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Resources res=getResources();
        String text2= res.getString(R.string.pacientiDB, MainActivity.patients1.size());
        tvDatabase.setText(text2);


        tvAddPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.patients1.clear();
                FirstActivityDoctor.patients.clear();
                Fragment fragment=null;
                if(v== v.findViewById(R.id.tvAddPat)){
                    fragment=new RealAddPatient();
                }
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return  view;
    }
}
