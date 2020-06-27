package com.example.dentalplus.DoctorInterface;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Service;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Raport2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raport2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Raport2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Raport2.
     */
    // TODO: Rename and change types and number of parameters
    public static Raport2 newInstance(String param1, String param2) {
        Raport2 fragment = new Raport2();
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

    TextView tvGraficaMedici;
    PieChart pieChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_raport2, container, false);

         tvGraficaMedici=(TextView)view.findViewById(R.id.tvGraficaMedici);
         pieChart=(PieChart)view.findViewById(R.id.idPieChart22);


         int mic=0;
         int mediu=0;
         int mare=0;

         for(Service service:fragment_SeeReports.listaServicii){
             if(Integer.valueOf(service.getServicePrice())<=50){
                 mic++;
             } else if(Integer.valueOf(service.getServicePrice())>50 && Integer.valueOf(service.getServicePrice())<=150){
                 mediu ++;
             } else if(Integer.valueOf(service.getServicePrice())>150){
                 mare++;
             }
         }

         ArrayList<Integer> valServ=new ArrayList<>();
         valServ.add(mic);
         valServ.add(mediu);
         valServ.add(mare);

        String[] specializari={"MIC (<50)","MEDIU (50-150)","MARE >(150)"};

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setText("PREȚ SERVICII / NUMĂR SERVICII");
        pieChart.getDescription().setTextSize(10);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.parseColor("#85CFCE"));
        //pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);


        ArrayList<PieEntry> yValues=new ArrayList<>();
        for(int i=0; i<valServ.size();i++)
        {
            yValues.add(new PieEntry(valServ.get(i),specializari[i]));
        }
        pieChart.setVisibility(View.VISIBLE);
        pieChart.animateY(1000, Easing.EaseInOutQuad);
        PieDataSet dataSet=new PieDataSet(yValues,"");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(8f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data =new PieData(dataSet);
        data.setValueTextSize(25f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.invalidate();
         return view;
    }
}
