package com.example.dentalplus.DoctorInterface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.HttpParse;
import com.example.dentalplus.clase.Invoice;
import com.example.dentalplus.clase.InvoiceAdapter;
import com.example.dentalplus.clase.InvoiceHolder;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_addInvoices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_addInvoices extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_addInvoices() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_addInvoices.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_addInvoices newInstance(String param1, String param2) {
        fragment_addInvoices fragment = new fragment_addInvoices();
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

    TextView tvAddInvoice, tvInvtext;
    EditText etCautaPacient;
    Button InvSortCresc, InvSortDesc;
    ListView lvInvoices;

    DatabaseReference dbInvoices;
    public static ArrayList<Invoice> invoiceList;
    public static ArrayList<InvoiceHolder> invoiceHolders;
    public static InvoiceAdapter invoiceAdapter;
    static Invoice invoice;

    DatabaseReference dbDoctors, dbPatients, dbServices, dbAppointments;
    public static ArrayList<Doctor> doctorsInv;
    public static ArrayList<Patient> patientsInv;
    public static ArrayList<Service> servicesInv;
    public static ArrayList<Appointment> appointmentInv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_invoices, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvAddInvoice=(TextView)view.findViewById(R.id.tvAddInvoice);
        tvInvtext=(TextView)view.findViewById(R.id.tvInvtext);
        etCautaPacient=(EditText)view.findViewById(R.id.etCautaDPacient);
        InvSortCresc=(Button)view.findViewById(R.id.InvSortCresc);
        InvSortDesc=(Button)view.findViewById(R.id.InvSortDesc);
        lvInvoices=(ListView)view.findViewById(R.id.lvInvoices);

        invoiceList=new ArrayList<>();
        invoiceHolders=new ArrayList<>();

        InvoiceAdapter.invoiceAdapte.clear();
        dbInvoices= FirebaseDatabase.getInstance().getReference("invoices");

        dbInvoices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoiceAdapter.clear();
                invoiceList.clear();
                invoiceHolders.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Invoice inv=ds.getValue(Invoice.class);
                    InvoiceHolder inVH=ds.getValue(InvoiceHolder.class);
                    invoiceList.add(inv);
                    invoiceAdapter.add(inVH);
                    invoiceHolders.add(inVH);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        invoiceAdapter=new InvoiceAdapter(getContext(), R.layout.invoice_listview_item);
        lvInvoices.setAdapter(invoiceAdapter);

        dbDoctors= FirebaseDatabase.getInstance().getReference("doctors");
        dbPatients=FirebaseDatabase.getInstance().getReference("patients");
        dbServices=FirebaseDatabase.getInstance().getReference("services");
        dbAppointments=FirebaseDatabase.getInstance().getReference("appointments");

        doctorsInv=new ArrayList<>();
        patientsInv=new ArrayList<>();
        servicesInv=new ArrayList<>();
        appointmentInv=new ArrayList<>();

        dbDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorsInv.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Doctor doctor=ds.getValue(Doctor.class);
                    doctorsInv.add(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbPatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientsInv.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Patient patient=ds.getValue(Patient.class);
                    patientsInv.add(patient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesInv.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Service service=ds.getValue(Service.class);
                    servicesInv.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentInv.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Appointment appointment=ds.getValue(Appointment.class);
                    appointmentInv.add(appointment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        etCautaPacient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                invoiceAdapter.getFilter().filter(s);
                invoiceAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        InvSortCresc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(InvoiceAdapter.invoiceAdapte);
                invoiceAdapter.notifyDataSetChanged();
            }
        });
        InvSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(InvoiceAdapter.invoiceAdapte,Collections.<InvoiceHolder>reverseOrder());
                invoiceAdapter.notifyDataSetChanged();
            }
        });
        tvAddInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=null;
                if(v== v.findViewById(R.id.tvAddInvoice)){
                    fragment=new RealAddInvoices();
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
