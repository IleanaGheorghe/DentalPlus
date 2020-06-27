package com.example.dentalplus.DoctorInterface;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.AppointmentAdapter;
import com.example.dentalplus.clase.AppointmentHolder;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Patient;
import com.example.dentalplus.clase.Service;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
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
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_AddAppoinments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_AddAppoinments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_AddAppoinments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_AddAppoinments.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_AddAppoinments newInstance(String param1, String param2) {
        fragment_AddAppoinments fragment = new fragment_AddAppoinments();
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

    TextView tvAddApp,tvAppAct,tvLunaAn;
    public static CompactCalendarView compactCalendarView;

    DatabaseReference dbDoctors, dbPatients, dbServices;
    public static ArrayList<Doctor> doctorsApp;
    public static ArrayList<Patient> patientsApp;
    public static ArrayList<Service> servicesApp;
    ListView lvProgramariData;

    DatabaseReference dbAppointments;
    public static ArrayList<AppointmentHolder> appointmentHolderApp;
    public static ArrayList<Appointment> appointmentsApp;
    public static AppointmentAdapter appointmentAdapterApp;
    public static String app;

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat=new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment__add_appoinments, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvAddApp=(TextView)view.findViewById(R.id.tvAddApp);
        tvAppAct=(TextView)view.findViewById(R.id.tvAppAct);
        tvLunaAn=(TextView)view.findViewById(R.id.tvLunaAn);
        compactCalendarView=(CompactCalendarView)view.findViewById(R.id.compactcalendar_view);
        lvProgramariData=(ListView)view.findViewById(R.id.lvProgramariData);

        compactCalendarView.setFirstDayOfWeek(2);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        tvLunaAn.setText(dateFormatMonth.format(Calendar.getInstance().getTime()).toUpperCase());

        dbAppointments=FirebaseDatabase.getInstance().getReference("appointments");
        AppointmentAdapter.appointmentAdapte.clear();
        appointmentHolderApp=new ArrayList<>();

        initProgramari();
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                final String data=dateFormat.format(dateClicked);
                AppointmentAdapter.appointmentAdapte.clear();

                dbAppointments.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        appointmentAdapterApp.clear();;
                        appointmentHolderApp.clear();

                        for(DataSnapshot ds :dataSnapshot.getChildren()){
                            AppointmentHolder app=ds.getValue(AppointmentHolder.class);
                            if(data.equals(app.getDate())){
                                appointmentAdapterApp.add(app);
                                appointmentHolderApp.add(app);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                appointmentAdapterApp=new AppointmentAdapter(getContext(),R.layout.appointment_item);
                lvProgramariData.setAdapter(appointmentAdapterApp);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvLunaAn.setText(dateFormatMonth.format(firstDayOfNewMonth).toUpperCase());
            }
        });

        dbDoctors= FirebaseDatabase.getInstance().getReference("doctors");
        dbPatients=FirebaseDatabase.getInstance().getReference("patients");
        dbServices=FirebaseDatabase.getInstance().getReference("services");

        doctorsApp=new ArrayList<>();
        patientsApp=new ArrayList<>();
        servicesApp=new ArrayList<>();
        appointmentsApp=new ArrayList<>();
        dbDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorsApp.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Doctor doctor=ds.getValue(Doctor.class);
                    doctorsApp.add(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbPatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientsApp.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Patient patient=ds.getValue(Patient.class);
                    patientsApp.add(patient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesApp.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Service service=ds.getValue(Service.class);
                    servicesApp.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentsApp.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Appointment a=ds.getValue(Appointment.class);
                    appointmentsApp.add(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tvAddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=null;
                if(v== v.findViewById(R.id.tvAddApp)){
                    fragment=new RealAddAppointments();
                }
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        lvProgramariData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.codFragment=1;
                app=appointmentHolderApp.get(position).getAppId();
                Fragment fragment=null;
                fragment=new EditAppointment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    public void initProgramari(){
        final String data=dateFormat.format(Calendar.getInstance().getTime());
        AppointmentAdapter.appointmentAdapte.clear();

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentAdapterApp.clear();;
                appointmentHolderApp.clear();

                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    AppointmentHolder app=ds.getValue(AppointmentHolder.class);
                    if(data.equals(app.getDate())){
                        appointmentAdapterApp.add(app);
                        appointmentHolderApp.add(app);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        appointmentAdapterApp=new AppointmentAdapter(getContext(),R.layout.appointment_item);
        lvProgramariData.setAdapter(appointmentAdapterApp);
    }

}
