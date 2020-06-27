package com.example.dentalplus.DoctorInterface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_SeeReports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_SeeReports extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_SeeReports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_SeeReports.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_SeeReports newInstance(String param1, String param2) {
        fragment_SeeReports fragment = new fragment_SeeReports();
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
    TextView tvRap;
    Button btnRap1, btnRap2, btnRap3;
    public static ArrayList<Service> listaServicii;
    public static ArrayList<Doctor> listaDoctori;
    public static ArrayList<Appointment> listaProgramari;
    DatabaseReference dbServices, dbDoctors, dbAppointments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment__see_reports, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvRap=(TextView)view.findViewById(R.id.tvRap);
        btnRap1=(Button)view.findViewById(R.id.btnRap1);
        btnRap2=(Button)view.findViewById(R.id.btnRap2);
        btnRap3=(Button)view.findViewById(R.id.btnRap3);

        listaServicii=new ArrayList<>();
        dbServices= FirebaseDatabase.getInstance().getReference("services");

        listaDoctori=new ArrayList<>();
        dbDoctors=FirebaseDatabase.getInstance().getReference("doctors");

        dbAppointments= FirebaseDatabase.getInstance().getReference("appointments");
        listaProgramari=new ArrayList<>();

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaProgramari.clear();
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    Appointment a =ds.getValue(Appointment.class);
                    listaProgramari.add(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaServicii.clear();

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Service service=ds.getValue(Service.class);
                    listaServicii.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDoctori.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Doctor doctor=ds.getValue(Doctor.class);
                    listaDoctori.add(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnRap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=null;
                if(v== v.findViewById(R.id.btnRap1)){
                    fragment=new Raport1();
                }
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnRap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=null;
                if(v== v.findViewById(R.id.btnRap2)){
                    fragment=new Raport2();
                }
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnRap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=null;
                if(v== v.findViewById(R.id.btnRap3)){
                    fragment=new Raport3();
                }
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
