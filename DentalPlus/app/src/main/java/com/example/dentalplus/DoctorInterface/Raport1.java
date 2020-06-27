package com.example.dentalplus.DoctorInterface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.DayAxisValueFormatter;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.DoctorSalary;
import com.example.dentalplus.clase.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Raport1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raport1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Raport1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Raport1.
     */
    // TODO: Rename and change types and number of parameters
    public static Raport1 newInstance(String param1, String param2) {
        Raport1 fragment = new Raport1();
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

    TextView tvGraficaMed;
    BarChart barChart;

    ArrayList<DoctorSalary> doctorSalaries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_raport1, container, false);

        tvGraficaMed=(TextView)view.findViewById(R.id.tvGraficaMed);
        barChart=(BarChart)view.findViewById(R.id.idBarChart);



        doctorSalaries=new ArrayList<>();

        for(int i=0;i<fragment_SeeReports.listaDoctori.size();i++){
            DoctorSalary ds=new DoctorSalary(fragment_SeeReports.listaDoctori.get(i).getFirstName()+" "+fragment_SeeReports.listaDoctori.get(i).getLastName(), Float.valueOf(fragment_SeeReports.listaDoctori.get(i).getSalary()));
            doctorSalaries.add(ds);
        }


        barChart.getDescription().setText("");
        barChart.getDescription().setTextSize(10);

        ArrayList<BarEntry> yVals=new ArrayList<>();
        ArrayList<String> labels=new ArrayList<>();

        for(int i=0; i<doctorSalaries.size();i++){
            yVals.add(new BarEntry(i, Float.valueOf(doctorSalaries.get(i).getSalariul())));
            labels.add(doctorSalaries.get(i).getNume());
        }

        BarDataSet set=new BarDataSet(yVals,"");
        set.setColors(ColorTemplate.PASTEL_COLORS);
        set.setDrawValues(true);

        BarData  data =new BarData(set);
        barChart.setData(data);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(270);

        ValueFormatter custom = new MyValueFormatter(" RON");
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);


        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        barChart.animateY(500);
        barChart.setFitBars(true);
        barChart.invalidate();
        return view;
    }
}
