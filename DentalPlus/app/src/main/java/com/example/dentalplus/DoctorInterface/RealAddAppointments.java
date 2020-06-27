package com.example.dentalplus.DoctorInterface;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.AppointmentAdapter;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.Service;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealAddAppointments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealAddAppointments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RealAddAppointments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RealAddAppointments.
     */
    // TODO: Rename and change types and number of parameters
    public static RealAddAppointments newInstance(String param1, String param2) {
        RealAddAppointments fragment = new RealAddAppointments();
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

    TextView tvProg, tvDataApp, etDataApp, tvOraInceputApp,
    etOraInceputApp, tvOraSfarsitApp, etOraSfarsitApp,
    tvDoctorApp, tvPacientApp,tvServiciuApp;
    Spinner spDoctorApp, spPacientApp,spServiciuApp;

    Button btnSaveAppointments;
    DatabaseReference dbDoctors, dbPatients, dbServices, dbAppointments;

    ArrayList<Appointment> appointments;

    ArrayAdapter<Doctor> doctorArrayAdapter;
    ArrayAdapter<Patient> patientArrayAdapter;
    ArrayAdapter<Service> serviceArrayAdapter;

    String doctorId, patientId, serviceId, patientName;

    private DatePickerDialog.OnDateSetListener datePickerDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_real_add_appointments, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvProg=(TextView)view.findViewById(R.id.tvProg);
        tvDataApp=(TextView)view.findViewById(R.id.tvDataApp);
        etDataApp=(TextView)view.findViewById(R.id.etDataApp);
        tvOraInceputApp=(TextView)view.findViewById(R.id.tvOraInceputApp);
        etOraInceputApp=(TextView)view.findViewById(R.id.etOraInceputApp);
        tvOraSfarsitApp=(TextView)view.findViewById(R.id.tvOraSfarsitApp);
        etOraSfarsitApp=(TextView)view.findViewById(R.id.etOraSfarsitApp);
        tvDoctorApp=(TextView)view.findViewById(R.id.tvDoctorApp);
        tvPacientApp=(TextView)view.findViewById(R.id.tvPacientApp);
        tvServiciuApp=(TextView)view.findViewById(R.id.tvServiciuApp);

        spDoctorApp=(Spinner) view.findViewById(R.id.spDoctorApp);
        spPacientApp=(Spinner)view.findViewById(R.id.spPacientApp);
        spServiciuApp=(Spinner)view.findViewById(R.id.spServiciuApp);

        btnSaveAppointments=(Button)view.findViewById(R.id.btnSaveAppointments);

        etDataApp.setOnClickListener(new View.OnClickListener() {
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
                etDataApp.setText(date);
            }
        };
        Calendar cal=Calendar.getInstance();
        final int hour=cal.get(Calendar.HOUR_OF_DAY);
        final int minute=cal.get(Calendar.MINUTE);

        etOraInceputApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etOraInceputApp.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        etOraSfarsitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etOraSfarsitApp.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        loadDataInSpinners();

        appointments=new ArrayList<>();
        dbAppointments= FirebaseDatabase.getInstance().getReference("appointments");

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();

                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    Appointment app=ds.getValue(Appointment.class);
                    appointments.add(app);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnSaveAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etDataApp.getText().toString();
                String dataI = etOraInceputApp.getText().toString();
                String dataS = etOraSfarsitApp.getText().toString();
                String doctor = doctorId;
                String pacient = patientId;
                String serviciu = serviceId;

                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                SimpleDateFormat f = new SimpleDateFormat("DD-M-YYYY");

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String currentDate = day + "-" + month + "-" + year;

                try {
                    boolean ok = true;
                    Date data1=f.parse(data);
                    Date cD=f.parse(data);
                    if (data.isEmpty() || dataI.isEmpty() || dataS.isEmpty() || doctor.isEmpty() || pacient.isEmpty() || serviceId.isEmpty() ||
                            data.equals("") || dataI.equals("") || dataS.equals("") || doctorId.equals("") || patientId.equals("") || serviceId.equals("")
                       ||dataS.equals("Introduceti ora sfarsit") || dataI.equals("Introduceti ora sfarsit")|| data.equals("Introduceti data")) {
                        Toast.makeText(getActivity(), "Toate campurile trebuie completate!", Toast.LENGTH_SHORT).show();
                        ok = false;
                    } else if (dataI.compareTo(dataS) > 0 || dataI.compareTo(dataS) == 0) {
                        Toast.makeText(getActivity(), "Ora sfarsit trebuie sa fie mai fare decat ora inceput!", Toast.LENGTH_SHORT).show();
                        ok = false;
                    } else if (data1.before(cD)) {
                        Toast.makeText(getActivity(), "Data trebuie aleasa in viitor!", Toast.LENGTH_SHORT).show();
                        ok = false;
                    } else {
                        Date dataIF = format.parse(dataI);
                        Date dataSF = format.parse(dataS);

                        boolean app = true;
                        for (Appointment appointment : fragment_AddAppoinments.appointmentsApp) {
                            if (data.equals(appointment.getDate()) && dataI.equals(appointment.getStartH()) && dataS.equals(appointment.getEndH())
                                    && doctorId.equals(appointment.getDoctor())) {
                                Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in aceeasi zi si aceeasi ora!", Toast.LENGTH_SHORT).show();
                                app = false;
                                break;
                            }
                        }

                        //verificare interval
                        if (app == true) {
                            for (Appointment appointment : fragment_AddAppoinments.appointmentsApp) {
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
                            for (Appointment appointment : fragment_AddAppoinments.appointmentsApp) {
                                if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                    if (dataIF.after(format.parse(appointment.getStartH())) && dataSF.after(format.parse(appointment.getEndH()))) {
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
                            for (Appointment appointment : fragment_AddAppoinments.appointmentsApp) {
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
                            for (Appointment appointment : fragment_AddAppoinments.appointmentsApp) {
                                if (data.equals(appointment.getDate()) && doctorId.equals(appointment.getDoctor())) {
                                    if (dataIF.after(format.parse(appointment.getStartH())) && dataSF.before(format.parse(appointment.getEndH()))) {
                                        Toast.makeText(getActivity(), "Exista deja o programare pentru acest doctor in intervalul orar ales!", Toast.LENGTH_SHORT).show();
                                        app = false;
                                        break;
                                    }
                                }
                            }
                        }

                        if (app == true && ok == true) {
                            int id = 0;
                            for (int i = 0; i < appointments.size(); i++) {
                                if (Integer.valueOf(appointments.get(i).getAppId()) > id) {
                                    id = Integer.valueOf(appointments.get(i).getAppId());
                                }
                            }
                            id = id + 1;
                            dbAppointments.child(String.valueOf(id)).child("appId").setValue(String.valueOf(id));
                            dbAppointments.child(String.valueOf(id)).child("date").setValue(data);
                            dbAppointments.child(String.valueOf(id)).child("doctor").setValue(doctorId);
                            dbAppointments.child(String.valueOf(id)).child("patient").setValue(patientId);
                            dbAppointments.child(String.valueOf(id)).child("serviceCode").setValue(serviceId);
                            dbAppointments.child(String.valueOf(id)).child("startH").setValue(dataI);
                            dbAppointments.child(String.valueOf(id)).child("endH").setValue(dataS);
                            Toast.makeText(getActivity(), "Programarea a fost adaugata", Toast.LENGTH_SHORT).show();

                            Fragment fragment = null;
                            if (v == v.findViewById(R.id.btnSaveAppointments)) {
                                fragment = new fragment_AddAppoinments();
                            }
                            AppointmentAdapter.appointmentAdapte.clear();
                            fragment_AddAppoinments.appointmentAdapterApp.clear();
                            fragment_AddAppoinments.appointmentHolderApp.clear();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Toate campurile trebuie completate!", Toast.LENGTH_SHORT).show();
                }
            }});
        return view;
    }

    private void loadDataInSpinners(){
        doctorArrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, fragment_AddAppoinments.doctorsApp);
        spDoctorApp.setAdapter(doctorArrayAdapter);
        spDoctorApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Doctor doc=(Doctor) parent.getSelectedItem();
                doctorId=doc.getUsername();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        patientArrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, fragment_AddAppoinments.patientsApp);
        spPacientApp.setAdapter(patientArrayAdapter);
        spPacientApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Patient patient=(Patient) parent.getSelectedItem();
                patientId=patient.getPatientPhone();
                patientName=patient.getPatientName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        serviceArrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, fragment_AddAppoinments.servicesApp);
        spServiciuApp.setAdapter(serviceArrayAdapter);
        spServiciuApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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