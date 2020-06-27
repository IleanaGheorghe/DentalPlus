package com.example.dentalplus.clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Invoice implements Parcelable,Comparable<Invoice> {
    private String appointmentNr;
    private String doctor;
    private String invoiceDate;
    private String invoiceValue;
    private boolean isPaid;
    private String patient;

    public Invoice() {
    }

    public Invoice(String appointmentNr, String doctor, String invoiceDate, String invoiceValue, boolean isPaid, String patient) {
        this.appointmentNr = appointmentNr;
        this.doctor = doctor;
        this.invoiceDate = invoiceDate;
        this.invoiceValue = invoiceValue;
        this.isPaid = isPaid;
        this.patient = patient;
    }

    protected Invoice(Parcel in) {
        appointmentNr = in.readString();
        doctor = in.readString();
        invoiceDate = in.readString();
        invoiceValue = in.readString();
        isPaid = in.readByte() != 0;
        patient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appointmentNr);
        dest.writeString(doctor);
        dest.writeString(invoiceDate);
        dest.writeString(invoiceValue);
        dest.writeByte((byte) (isPaid ? 1 : 0));
        dest.writeString(patient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Invoice> CREATOR = new Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel in) {
            return new Invoice(in);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };

    public String getAppointmentNr() {
        return appointmentNr;
    }

    public void setAppointmentNr(String appointmentNr) {
        this.appointmentNr = appointmentNr;
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    @Override
    public int compareTo(Invoice o) {
        return this.invoiceDate.compareTo(o.invoiceDate);
    }
}
