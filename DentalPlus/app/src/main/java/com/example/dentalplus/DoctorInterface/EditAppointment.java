package com.example.dentalplus.DoctorInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.AppointmentAdapter;
import com.example.dentalplus.clase.AppointmentHolder;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Invoice;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.PatientAdapter;
import com.example.dentalplus.clase.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAppointment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditAppointment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditAppointment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditAppointment newInstance(String param1, String param2) {
        EditAppointment fragment = new EditAppointment();
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

    TextView tvProgEdit, tvDE, etDataEditApp, tvOIE,etOraInceputEditApp,tvOSE,etOraSfarsitEditApp,
            tvDEditApp,tvPEditApp,tvSEApp,tvEditProg,tvDeleteProg,tvNotifPat,spDoctorEditApp,spPacientEditApp;
    Spinner spServiciuEditApp;
    DatabaseReference dbDoctors, dbPatients, dbServices, dbAppointments, dbInvoices;

    ArrayList<Appointment> appointments;
    ArrayList<Invoice> invoices;
    ArrayAdapter<Doctor> doctorArrayAdapter;
    ArrayAdapter<Patient> patientArrayAdapter;
    ArrayAdapter<Service> serviceArrayAdapter;
    String doctorId, patientId, serviceId, patientName;

    private DatePickerDialog.OnDateSetListener datePickerDialog;
    public static Appointment appoint;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_appointment, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tvProgEdit=(TextView)view.findViewById(R.id.tvProgEdit);
        tvDE=(TextView)view.findViewById(R.id.tvDE);
        etDataEditApp=(TextView)view.findViewById(R.id.etDataEditApp);
        tvOIE=(TextView)view.findViewById(R.id.tvOIE);
        etOraInceputEditApp=(TextView)view.findViewById(R.id.etOraInceputEditApp);
        tvOSE=(TextView)view.findViewById(R.id.tvOSE);
        etOraSfarsitEditApp=(TextView)view.findViewById(R.id.etOraSfarsitEditApp);
        tvDEditApp=(TextView)view.findViewById(R.id.tvDEditApp);
        tvPEditApp=(TextView)view.findViewById(R.id.tvPEditApp);
        tvSEApp=(TextView)view.findViewById(R.id.tvSEApp);
        tvEditProg=(TextView)view.findViewById(R.id.tvEditProg);
        tvDeleteProg=(TextView)view.findViewById(R.id.tvDeleteProg);
        tvNotifPat=(TextView)view.findViewById(R.id.tvNotifPat);
        spDoctorEditApp=(TextView)view.findViewById(R.id.spDoctorEditApp);
        spPacientEditApp=(TextView)view.findViewById(R.id.spPacientEditApp);

        spServiciuEditApp=(Spinner) view.findViewById(R.id.spServiciuEditApp);

        appointments=new ArrayList<>();
        invoices=new ArrayList<>();
        dbAppointments= FirebaseDatabase.getInstance().getReference("appointments");
        dbInvoices=FirebaseDatabase.getInstance().getReference("invoices");

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Appointment app=ds.getValue(Appointment.class);
                    appointments.add(app);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

        if(MainActivity.codFragment==1){
            // fragment_AddAppointment
            etDataEditApp.setOnClickListener(new View.OnClickListener() {
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
                    etDataEditApp.setText(date);
                }
            };
            Calendar cal=Calendar.getInstance();
            final int hour=cal.get(Calendar.HOUR_OF_DAY);
            final int minute=cal.get(Calendar.MINUTE);

            etOraInceputEditApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            etOraInceputEditApp.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePickerDialog.show();
                }
            });

            etOraSfarsitEditApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            etOraSfarsitEditApp.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePickerDialog.show();
                }
            });

            loadDataInSpinners();


            for(Appointment a:fragment_AddAppoinments.appointmentsApp){
                if (fragment_AddAppoinments.app.equals(a.getAppId().toString())){
                    appoint=new Appointment(a.getAppId(),a.getDate(), a.getDoctor(),a.getEndH(),a.getPatient(),a.getServiceCode(), a.getStartH());
                }
            }
            etDataEditApp.setText(appoint.getDate().toString());
            etOraInceputEditApp.setText(appoint.getStartH().toString());
            etOraSfarsitEditApp.setText(appoint.getEndH().toString());
            for(Doctor doc: fragment_AddAppoinments.doctorsApp){
                if(doc.getUsername().equals(appoint.getDoctor())){
                    spDoctorEditApp.setText((doc.getFirstName()+" "+doc.getLastName()));
                    doctorId=doc.getUsername();
                }
            }

            for(Patient p :fragment_AddAppoinments.patientsApp){
                if(p.getPatientPhone().equals(appoint.getPatient())){
                    spPacientEditApp.setText(p.getPatientName());
                    patientId=p.getPatientPhone();
                }
            }

            for(int i=0;i<fragment_AddAppoinments.servicesApp.size();i++){
                if(fragment_AddAppoinments.servicesApp.get(i).getServiceCode().equals(appoint.getServiceCode())){
                    spServiciuEditApp.setSelection(i);
                }
            }

            tvEditProg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sigur doriti sa editati aceasta programare?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String data=etDataEditApp.getText().toString();
                            String dataI=etOraInceputEditApp.getText().toString();
                            String dataS=etOraSfarsitEditApp.getText().toString();
                            String serviciu=serviceId;

                            SimpleDateFormat format =new SimpleDateFormat("HH:mm");
                            SimpleDateFormat f=new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());

                            Calendar cal=Calendar.getInstance();
                            int year=cal.get(Calendar.YEAR);
                            int month=cal.get(Calendar.MONTH)+1;
                            int day =cal.get(Calendar.DAY_OF_MONTH);
                            String currentDate=day+"-"+month+"-"+year;

                            try {
                                Date dataIF=format.parse(dataI);
                                Date dataSF=format.parse(dataS);
                                boolean ok=true;

                                if (data.isEmpty()||dataI.isEmpty()|| dataS.isEmpty()||serviceId.isEmpty()||
                                        data.equals("")||dataI.equals("")||dataS.equals("")||serviceId.equals("")){
                                    Toast.makeText(getActivity(),"Toate campurile trebuie completate!",Toast.LENGTH_SHORT).show();
                                    ok=false;
                                } else if(dataI.compareTo(dataS)>0 || dataI.compareTo(dataS)==0){
                                    Toast.makeText(getActivity(),"Ora sfarsit trebuie sa fie mai fare decat ora inceput!",Toast.LENGTH_SHORT).show();
                                    ok=false;
                                }
                                else if(data.compareTo(currentDate)<0){
                                    Toast.makeText(getActivity(),"Data trebuie aleasa in viitor!",Toast.LENGTH_SHORT).show();
                                    ok=false;
                                }
                                else {
                                    boolean app=true;
                                    for(Appointment appointment: appointments){
                                        if(data.equals(appointment.getDate()) && dataI.equals(appointment.getStartH())&&dataS.equals(appointment.getEndH())
                                                && doctorId.equals(appointment.getDoctor())){
                                            Toast.makeText(getActivity(),"Exista deja o programare pentru acest doctor in aceeasi zi!Alegeti alta data!",Toast.LENGTH_SHORT).show();
                                            app=false;
                                            break;
                                        }
                                    }

                                    //verificare interval
                                    //verificare interval
                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if (dataIF.after(format.parse(appointment.getStartH())) && dataIF.before(format.parse(appointment.getEndH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if ((dataIF.after(format.parse(appointment.getStartH())) && dataIF.before(format.parse(appointment.getEndH()))) && dataSF.after(format.parse(appointment.getEndH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                   /* if(app==true){
                        for(Appointment appointment: fragment_AddAppoinments.appointmentsApp){
                            if(data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())){
                                if (dataIF.before(format.parse(appointment.getStartH())) && dataSF.before(format.parse(appointment.getStartH()))) {
                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                    app = false;
                                    break;
                                }
                            }
                        }
                    }*/
                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if (dataIF.before(format.parse(appointment.getStartH())) && dataSF.after(format.parse(appointment.getStartH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if (dataIF.after(format.parse(appointment.getStartH())) && dataSF.before(format.parse(appointment.getEndH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();
                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if(app && ok){
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("date").setValue(data);
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("serviceCode").setValue(serviceId);
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("startH").setValue(dataI);
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("endH").setValue(dataS);
                                        Toast.makeText(getActivity(), "Programarea a fost actualizata",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
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
                    alertDialog.setTitle("Actualizare programare");
                    alertDialog.show();
                }
            });

            tvDeleteProg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sigur doriti sa stergeti aceasta programare?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            for(Invoice i:invoices){
                                dbInvoices.child(appoint.getAppId()).removeValue();
                            }
                            for(Appointment a:fragment_AddAppoinments.appointmentsApp){
                                dbAppointments.child(appoint.getAppId()).removeValue();
                            }

                            Toast.makeText(getActivity(),"Programarea a fost stearsa!",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            Fragment fragment=null;
                            fragment=new fragment_AddAppoinments();
                            AppointmentAdapter.appointmentAdapte.clear();
                            fragment_AddAppoinments.appointmentAdapterApp.clear();
                            fragment_AddAppoinments.appointmentHolderApp.clear();
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
                    alertDialog.setTitle("Stergere programare");
                    alertDialog.show();
                }
            });

            tvNotifPat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sigur doriti sa notificati acest pacient?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {

                                SimpleDateFormat format =new SimpleDateFormat("HH:mm");
                                SimpleDateFormat f=new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());

                                Calendar cal=Calendar.getInstance();
                                int year=cal.get(Calendar.YEAR);
                                int month=cal.get(Calendar.MONTH)+1;
                                int day =cal.get(Calendar.DAY_OF_MONTH);
                                String currentDate=day+"-"+month+"-"+year;
                                if (etDataEditApp.getText().toString().compareTo(currentDate)>0) {
                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(patientId, null, "Nu uitati de programarea din data de " + etDataEditApp.getText().toString() + ", ora " + etOraInceputEditApp.getText().toString() + ". Clinica DentalPlus", null, null);
                                    Toast.makeText(getActivity(), "Notificare trimisa!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Nu se pot notifica programarile din trecut!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getActivity(),ex.getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                                ex.printStackTrace();
                            }


                        }
                    });
                    builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle("Notificare pacient");
                    alertDialog.show();
                }
            });

            try {
                SimpleDateFormat f = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());

                Calendar cal1 = Calendar.getInstance();
                int year = cal1.get(Calendar.YEAR);
                int month = cal1.get(Calendar.MONTH)+1;
                int day = cal1.get(Calendar.DAY_OF_MONTH);
                String currentDate = day + "-" + month + "-" + year;

                Date cD=f.parse(currentDate);
                Date data=f.parse(etDataEditApp.getText().toString());

                if(data.before(cD)){
                    tvEditProg.setVisibility(view.INVISIBLE);
                    tvNotifPat.setVisibility(view.INVISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else{
            //fragment istoric
            etDataEditApp.setOnClickListener(new View.OnClickListener() {
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
                    etDataEditApp.setText(date);
                }
            };
            Calendar cal=Calendar.getInstance();
            final int hour=cal.get(Calendar.HOUR_OF_DAY);
            final int minute=cal.get(Calendar.MINUTE);

            etOraInceputEditApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            etOraInceputEditApp.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePickerDialog.show();
                }
            });

            etOraSfarsitEditApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            etOraSfarsitEditApp.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePickerDialog.show();
                }
            });

            loadDataInSpinners2();


            for(Appointment a:IstoricPacient.appointmentsI){
                if (IstoricPacient.appIstoric.equals(a.getAppId().toString())){
                    appoint=new Appointment(a.getAppId(),a.getDate(), a.getDoctor(),a.getEndH(),a.getPatient(),a.getServiceCode(), a.getStartH());
                }
            }
            etDataEditApp.setText(appoint.getDate().toString());
            etOraInceputEditApp.setText(appoint.getStartH().toString());
            etOraSfarsitEditApp.setText(appoint.getEndH().toString());
            for(Doctor doc: MainActivity.doctorList){
                if(doc.getUsername().equals(appoint.getDoctor())){
                    spDoctorEditApp.setText((doc.getFirstName()+" "+doc.getLastName()));
                    doctorId=doc.getUsername();
                }
            }

            for(Patient p :FirstActivityDoctor.patients){
                if(p.getPatientPhone().equals(appoint.getPatient())){
                    spPacientEditApp.setText(p.getPatientName());
                    patientId=p.getPatientPhone();
                }
            }

            for(int i=0;i<FirstActivityDoctor.services.size();i++){
                if(FirstActivityDoctor.services.get(i).getServiceCode().equals(appoint.getServiceCode())){
                    spServiciuEditApp.setSelection(i);
                }
            }

            tvEditProg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sigur doriti sa editati aceasta programare?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String data=etDataEditApp.getText().toString();
                            String dataI=etOraInceputEditApp.getText().toString();
                            String dataS=etOraSfarsitEditApp.getText().toString();
                            String serviciu=serviceId;

                            SimpleDateFormat format =new SimpleDateFormat("HH:mm");
                            SimpleDateFormat f=new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());

                            Calendar cal=Calendar.getInstance();
                            int year=cal.get(Calendar.YEAR);
                            int month=cal.get(Calendar.MONTH)+1;
                            int day =cal.get(Calendar.DAY_OF_MONTH);
                            String currentDate=day+"-"+month+"-"+year;

                            try {
                                Date dataIF=format.parse(dataI);
                                Date dataSF=format.parse(dataS);
                                boolean ok=true;

                                if (data.isEmpty()||dataI.isEmpty()|| dataS.isEmpty()||serviceId.isEmpty()||
                                        data.equals("")||dataI.equals("")||dataS.equals("")||serviceId.equals("")){
                                    Toast.makeText(getActivity(),"Toate campurile trebuie completate!",Toast.LENGTH_SHORT).show();
                                    ok=false;
                                } else if(dataI.compareTo(dataS)>0 || dataI.compareTo(dataS)==0){
                                    Toast.makeText(getActivity(),"Ora sfarsit trebuie sa fie mai fare decat ora inceput!",Toast.LENGTH_SHORT).show();
                                    ok=false;
                                }
                                else if(data.compareTo(currentDate)<0){
                                    Toast.makeText(getActivity(),"Data trebuie aleasa in viitor!",Toast.LENGTH_SHORT).show();
                                    ok=false;
                                }
                                else {
                                    boolean app=true;
                                    for(Appointment appointment: appointments){
                                        if(data.equals(appointment.getDate()) && dataI.equals(appointment.getStartH())&&dataS.equals(appointment.getEndH())
                                                && doctorId.equals(appointment.getDoctor())){
                                            Toast.makeText(getActivity(),"Exista deja o programare pentru acest doctor in aceeasi zi!Alegeti alta data!",Toast.LENGTH_SHORT).show();
                                            app=false;
                                            break;
                                        }
                                    }

                                    //verificare interval
                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if (dataIF.after(format.parse(appointment.getStartH())) && dataIF.before(format.parse(appointment.getEndH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if ((dataIF.after(format.parse(appointment.getStartH())) && dataIF.before(format.parse(appointment.getEndH()))) && dataSF.after(format.parse(appointment.getEndH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                   /* if(app==true){
                        for(Appointment appointment: fragment_AddAppoinments.appointmentsApp){
                            if(data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())){
                                if (dataIF.before(format.parse(appointment.getStartH())) && dataSF.before(format.parse(appointment.getStartH()))) {
                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                    app = false;
                                    break;
                                }
                            }
                        }
                    }*/
                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if (dataIF.before(format.parse(appointment.getStartH())) && dataSF.after(format.parse(appointment.getStartH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();

                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if (app == true) {
                                        for (Appointment appointment : appointments) {
                                            if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                                if (dataIF.after(format.parse(appointment.getStartH())) && dataSF.before(format.parse(appointment.getEndH()))) {
                                                    Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();
                                                    app = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }


                                    if(app==true && ok==true){
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("date").setValue(data);
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("serviceCode").setValue(serviceId);
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("startH").setValue(dataI);
                                        dbAppointments.child(String.valueOf(appoint.getAppId())).child("endH").setValue(dataS);
                                        Toast.makeText(getActivity(), "Programarea a fost actualizata",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Va rugam verificati datele introduse!",Toast.LENGTH_SHORT).show();
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
                    alertDialog.setTitle("Actualizare programare");
                    alertDialog.show();
                }
            });

            tvDeleteProg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sigur doriti sa stergeti aceasta programare?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            for(Invoice i:invoices){
                                dbInvoices.child(appoint.getAppId()).removeValue();
                            }
                            for(Appointment a:fragment_AddAppoinments.appointmentsApp){
                                dbAppointments.child(appoint.getAppId()).removeValue();
                            }

                            Toast.makeText(getActivity(),"Programarea a fost stearsa!",Toast.LENGTH_SHORT).show();
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
                    alertDialog.setTitle("Stergere programare");
                    alertDialog.show();
                }
            });

            tvNotifPat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Sigur doriti sa notificati acest pacient?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                SimpleDateFormat format =new SimpleDateFormat("HH:mm");
                                SimpleDateFormat f=new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());

                                Calendar cal=Calendar.getInstance();
                                int year=cal.get(Calendar.YEAR);
                                int month=cal.get(Calendar.MONTH)+1;
                                int day =cal.get(Calendar.DAY_OF_MONTH);
                                String currentDate=day+"-"+month+"-"+year;
                                if (etDataEditApp.getText().toString().compareTo(currentDate)>0) {
                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(patientId, null, "Nu uitati de programarea din data de " + etDataEditApp.getText().toString() + ", ora " + etOraInceputEditApp.getText().toString() + ". Clinica DentalPlus", null, null);
                                    Toast.makeText(getActivity(), "Notificare trimisa!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Nu se pot notifica programarile din trecut!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getActivity(),ex.getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                                ex.printStackTrace();
                            }


                        }
                    });
                    builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle("Notificare pacient");
                    alertDialog.show();
                }
            });


            try {
                DateFormat f = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());

                Calendar cal1 = Calendar.getInstance();
                int year = cal1.get(Calendar.YEAR);
                int month = cal1.get(Calendar.MONTH)+1;
                int day = cal1.get(Calendar.DAY_OF_MONTH);
                String currentDate = day + "-" + month + "-" + year;

                Date cD=f.parse(currentDate);
                Date data=f.parse(etDataEditApp.getText().toString());

                if(data.before(cD)){
                    tvEditProg.setVisibility(view.INVISIBLE);
                    tvNotifPat.setVisibility(view.INVISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        return view;
    }

    private void loadDataInSpinners(){
        serviceArrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, fragment_AddAppoinments.servicesApp);
        spServiciuEditApp.setAdapter(serviceArrayAdapter);
        spServiciuEditApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Service service=(Service) parent.getSelectedItem();
                serviceId=service.getServiceCode();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadDataInSpinners2(){
        serviceArrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, FirstActivityDoctor.services);
        spServiciuEditApp.setAdapter(serviceArrayAdapter);
        spServiciuEditApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Service service=(Service) parent.getSelectedItem();
                serviceId=service.getServiceCode();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
