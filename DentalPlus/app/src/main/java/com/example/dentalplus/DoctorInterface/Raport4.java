package com.example.dentalplus.DoctorInterface;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Appointment;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Service;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Raport4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raport4 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Raport4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Raport4.
     */
    // TODO: Rename and change types and number of parameters
    public static Raport4 newInstance(String param1, String param2) {
        Raport4 fragment = new Raport4();
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

    TextView tvGraficProgMedici;
    HorizontalBarChart horizontalBarChart;

    Map<String, Integer> map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_raport4, container, false);

        tvGraficProgMedici=(TextView)view.findViewById(R.id.tvGraficProgMedici);
        horizontalBarChart=(HorizontalBarChart) view.findViewById(R.id.idHorizontalBarChart);


        map=new HashMap<String, Integer>();
        for(Doctor dr:fragment_SeeReports.listaDoctori){
            map.put(dr.getUsername(),0);
        }

        for(Appointment a :fragment_SeeReports.listaProgramari){
            map.put(a.getDoctor(), map.get(a.getDoctor())+1);
        }

        ArrayList<BarEntry> yVals=new ArrayList<>();
        ArrayList<String> labels=new ArrayList<>();

        for(int i=0;i<fragment_SeeReports.listaDoctori.size();i++){
            yVals.add(new BarEntry(i,map.get(fragment_SeeReports.listaDoctori.get(i).getUsername())));
            labels.add(fragment_SeeReports.listaDoctori.get(i).getFirstName()+" "+fragment_SeeReports.listaDoctori.get(i).getLastName());
        }

        BarDataSet set=new BarDataSet(yVals,"");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setDrawValues(true);

        BarData data =new BarData(set);
        horizontalBarChart.setData(data);

        XAxis xAxis=horizontalBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(270);


        YAxis leftAxis = horizontalBarChart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setGranularity(1.0f);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = horizontalBarChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);


        Legend l = horizontalBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.animateY(500);
        horizontalBarChart.setFitBars(true);
        horizontalBarChart.invalidate();
        return view;
    }
}
