package com.example.dentalplus.clase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dentalplus.DoctorInterface.FirstActivityDoctor;
import com.example.dentalplus.DoctorInterface.fragment_addInvoices;
import com.example.dentalplus.DoctorInterface.fragment_addPatient;
import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InvoiceAdapter extends ArrayAdapter implements  Filterable{
    public static List invoiceAdapte=new ArrayList();
    public static InvoiceHolder invoiceEditare;
    DatabaseReference dbInvoice= FirebaseDatabase.getInstance().getReference("invoices");

    public InvoiceAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class InvoiceHandler{
        TextView appointmentNr;
        TextView doctor;
        TextView pacient;
        TextView dataFact;
        TextView valoareFact;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        invoiceAdapte.add(object);
    }

    @Override
    public int getCount() {
        return invoiceAdapte.size();
    }

    @Override
    public Object getItem(int position) {
        return invoiceAdapte.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        InvoiceHandler invoiceHandler;

        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.invoice_listview_item,parent,false);
            invoiceHandler=new InvoiceHandler();
            invoiceHandler.appointmentNr=(TextView)row.findViewById(R.id.tvNrProg);
            invoiceHandler.doctor=(TextView)row.findViewById(R.id.tvDoctorFact);
            invoiceHandler.pacient=(TextView)row.findViewById(R.id.tvPacientFact);
            invoiceHandler.dataFact=(TextView)row.findViewById(R.id.tvDataFact);
            invoiceHandler.valoareFact=(TextView)row.findViewById(R.id.tvValFactFact);

            row.setTag(invoiceHandler);
        } else invoiceHandler=(InvoiceHandler)row.getTag();

        final InvoiceHolder invoice=(InvoiceHolder) this.getItem(position);
        String doctor=null;
        for (Doctor doc : fragment_addInvoices.doctorsInv){
            if(doc.getUsername().equals(invoice.getDoctor())){
                doctor=doc.getFirstName()+" "+doc.getLastName();
                invoice.setDoctor(doc.getFirstName()+" "+doc.getLastName());
            }
        }
        String pacient;
        for(Patient p: fragment_addInvoices.patientsInv){
            if(p.getPatientPhone().equals(invoice.getPatient())){
                pacient=p.getPatientName();
                invoice.setPatient(p.getPatientName());
            }
        }

        invoiceHandler.appointmentNr.setText("Nr. programare: "+invoice.getAppointmentNr());
        invoiceHandler.doctor.setText("Doctor: "+invoice.getDoctor());
        invoiceHandler.pacient.setText("Pacient: "+invoice.getPatient());
        invoiceHandler.dataFact.setText("Data: "+invoice.getInvoiceDate());
        invoiceHandler.valoareFact.setText("Valoare: "+invoice.getInvoiceValue());

        return row;
    }

    CustomFilter cf;
    @NonNull
    @Override
    public Filter getFilter() {
        if (cf==null){
            cf=new CustomFilter();
        }
        return cf;
    }

    class CustomFilter extends  Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                constraint=constraint.toString().toUpperCase();

                ArrayList<InvoiceHolder> filters=new ArrayList<>();

                for(int i = 0; i< fragment_addInvoices.invoiceHolders.size(); i++){
                    if( fragment_addInvoices.invoiceHolders.get(i).getPatient().toUpperCase().contains(constraint)){
                        InvoiceHolder inv= new InvoiceHolder(fragment_addInvoices.invoiceHolders.get(i).appointmentNr,fragment_addInvoices.invoiceHolders.get(i).getDoctor(),fragment_addInvoices.invoiceHolders.get(i).getInvoiceDate(), fragment_addInvoices.invoiceHolders.get(i).getInvoiceValue(), fragment_addInvoices.invoiceHolders.get(i).getPatient());
                        filters.add(inv);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }
            else
            {
                results.count=fragment_addInvoices.invoiceHolders.size();
                results.values=fragment_addInvoices.invoiceHolders;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            invoiceAdapte=(ArrayList<InvoiceHolder>)results.values;
            fragment_addInvoices.invoiceAdapter.notifyDataSetChanged();
        }
    }
}
