package com.example.dentalplus.clase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Appointment implements Parcelable,Comparable<Appointment> {
    private String appId;
    private String date;
    private String doctor;
    private String endH;
    private String patient;
    private String serviceCode;
    private String startH;

    public Appointment() {
    }

    public Appointment(String appId, String date, String doctor, String endH, String patient, String serviceCode, String startH) {
        this.appId = appId;
        this.date = date;
        this.doctor = doctor;
        this.endH = endH;
        this.patient = patient;
        this.serviceCode = serviceCode;
        this.startH = startH;
    }

    protected Appointment(Parcel in) {
        appId=in.readString();
        date = in.readString();
        doctor = in.readString();
        endH = in.readString();
        patient = in.readString();
        serviceCode = in.readString();
        startH = in.readString();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getEndH() {
        return endH;
    }

    public void setEndH(String endH) {
        this.endH = endH;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getStartH() {
        return startH;
    }

    public void setStartH(String startH) {
        this.startH = startH;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appId);
        dest.writeString(date);
        dest.writeString(doctor);
        dest.writeString(endH);
        dest.writeString(patient);
        dest.writeString(serviceCode);
        dest.writeString(startH);
    }

    @Override
    public int compareTo(Appointment o) {
        return this.date.compareTo(o.date);
    }

    @NonNull
    @Override
    public String toString() {
        return this.date + " - "+ this.doctor+" - "+this.patient;
    }
}
