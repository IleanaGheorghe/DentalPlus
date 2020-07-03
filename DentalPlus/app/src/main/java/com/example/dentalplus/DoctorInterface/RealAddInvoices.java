package com.example.dentalplus.DoctorInterface;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.Invoice;
import com.example.dentalplus.clase.InvoiceAdapter;
import com.example.dentalplus.clase.PatientAdapter;
import com.example.dentalplus.clase.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealAddInvoices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealAddInvoices extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RealAddInvoices() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RealAddInvoices.
     */
    // TODO: Rename and change types and number of parameters
    public static RealAddInvoices newInstance(String param1, String param2) {
        RealAddInvoices fragment = new RealAddInvoices();
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

    TextView tvInv, tvDataInv, etDataInv, tvProgramare;
    TextView tvDoctorInv, tvDoctorSp,tvPacientInv, tvPacientSp, tvServiciuInv, etServiciuInv;
    Spinner etProgramare;
    Button btnSaveInvoice;

    DatabaseReference dbAppointments, dbInvoices;
    ArrayList<Invoice> invoices;
    ArrayList<Appointment> appointments;
    ArrayAdapter<Appointment> appointmentArrayAdapter;

    String doctorName, patientName, ServiceValue, appId;
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_real_add_invoices, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvInv=(TextView)view.findViewById(R.id.tvInv);
        tvDataInv=(TextView)view.findViewById(R.id.tvDataInv);
        etDataInv=(TextView)view.findViewById(R.id.etDataInv);
        tvProgramare=(TextView)view.findViewById(R.id.tvProgramare);
        tvDoctorInv=(TextView)view.findViewById(R.id.tvDoctorInv);
        tvDoctorSp=(TextView)view.findViewById(R.id.tvDoctorSp);
        tvPacientInv=(TextView)view.findViewById(R.id.tvPacientInv);
        tvPacientSp=(TextView)view.findViewById(R.id.tvPacientSp);
        tvServiciuInv=(TextView)view.findViewById(R.id.tvServiciuInv);
        etServiciuInv=(TextView)view.findViewById(R.id.etServiciuInv);
        etProgramare=(Spinner)view.findViewById(R.id.etProgramare);
        btnSaveInvoice=(Button)view.findViewById(R.id.btnSaveInvoice);

        etDataInv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerDialog,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.show();
            }
        });

        datePickerDialog=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"-"+month+"-"+year;
                etDataInv.setText(date);
            }
        };

        appointments=fragment_addInvoices.appointmentInv;
        dbInvoices= FirebaseDatabase.getInstance().getReference("invoices");
        invoices=new ArrayList<>();

        dbInvoices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoices.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Invoice inv=ds.getValue(Invoice.class);
                    invoices.add(inv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for(int i=0;i<fragment_addInvoices.invoiceHolders.size();i++){
            boolean ok=false; int x=0;
            for(int j=0;j<appointments.size();j++){
                if(appointments.get(j).getAppId().toString().equals(fragment_addInvoices.invoiceHolders.get(i).getAppointmentNr().toString())){
                    ok=true;
                    x=j;
                }
            }
            if(ok==true){
                appointments.remove(x);
            }
        }

        loadDataInSpinner();

        btnSaveInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=etDataInv.getText().toString();

                if(data.isEmpty()||data.equals("")){
                    Toast.makeText(getActivity(),"Campul data trebuie completat!",Toast.LENGTH_SHORT).show();
                } else{
                    dbInvoices.child(appId).child("appointmentNr").setValue(appId);
                    dbInvoices.child(appId).child("doctor").setValue(tvDoctorSp.getText().toString());
                    dbInvoices.child(appId).child("patient").setValue(tvPacientSp.getText().toString());
                    dbInvoices.child(appId).child("isPaid").setValue("false");
                    dbInvoices.child(appId).child("invoiceDate").setValue(data);
                    dbInvoices.child(appId).child("invoiceValue").setValue(etServiciuInv.getText().toString());

                    Toast.makeText(getActivity(),"Factura a fost inregistrata cu succes!",Toast.LENGTH_SHORT).show();

                    Fragment fragment=null;
                    if(v== v.findViewById(R.id.btnSaveInvoice)){
                        fragment=new fragment_addInvoices();
                    }
                    InvoiceAdapter.invoiceAdapte.clear();
                    fragment_addInvoices.invoiceList.clear();
                    fragment_addInvoices.invoiceAdapter.clear();
                    fragment_addInvoices.invoiceHolders.clear();
                    InvoiceAdapter.invoiceAdapte.clear();

                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }


    private void loadDataInSpinner(){
        appointmentArrayAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, appointments);
        etProgramare.setAdapter(appointmentArrayAdapter);
        etProgramare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Appointment app=(Appointment) parent.getSelectedItem();

                appId=app.getAppId();
                tvDoctorSp.setText(app.getDoctor());
                tvPacientSp.setText(app.getPatient());

                for(Service srv: fragment_addInvoices.servicesInv){
                    if(srv.getServiceCode().equals(app.getServiceCode())){
                        etServiciuInv.setText(srv.getServicePrice());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
