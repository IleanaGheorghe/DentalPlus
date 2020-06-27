package com.example.dentalplus.clase;

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
        if(this.invoiceDate.compareTo(inv.invoiceDate)>0)
            return 1;
        else if(this.invoiceDate.compareTo(inv.invoiceDate)<0)
            return -1;
        else return this.invoiceValue.compareTo(inv.invoiceValue);
    }
}
