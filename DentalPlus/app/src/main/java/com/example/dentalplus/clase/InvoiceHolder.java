package com.example.dentalplus.clase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InvoiceHolder implements  Comparable{
    String appointmentNr;
    String doctor;
    String invoiceDate;
    String invoiceValue;
    String patient;

    public InvoiceHolder() {
    }

    public InvoiceHolder(String appointmentBr, String doctor, String invoiceDate, String invoiceValue, String patient) {
        this.appointmentNr = appointmentBr;
        this.doctor = doctor;
        this.invoiceDate = invoiceDate;
        this.invoiceValue = invoiceValue;
        this.patient = patient;
    }

    public String getAppointmentNr() {
        return appointmentNr;
    }

    public void setAppointmentNr(String appointmentBr) {
        this.appointmentNr = appointmentBr;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    @Override
    public int compareTo(Object o) {
        InvoiceHolder inv=(InvoiceHolder) o;
        SimpleDateFormat f=new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
        int var=0;
        try {
            Date dInv=f.parse(this.invoiceDate);
            Date invH=f.parse(inv.invoiceDate);
            if(dInv.after(invH))
                var=1;
            else if(dInv.before(invH))
                var= -1;
            else var= this.invoiceValue.compareTo(inv.invoiceValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return var;
    }
}
