package com.example.dentalplus.clase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Patient  implements Parcelable {
    private String patientBirthDate;
    private String patientEmail;
    private String patientName;
    private String patientObservations;
    private String patientPhone;
    private String patientRegisterDate;
    private String patientSex;

    public Patient() {
    }

    public Patient(String patientBirthDate, String patientEmail, String patientName, String patientObservations, String patientPhone, String patientRegisterDate, String patientSex) {
        this.patientBirthDate = patientBirthDate;
        this.patientEmail = patientEmail;
        this.patientName = patientName;
        this.patientObservations = patientObservations;
        this.patientPhone = patientPhone;
        this.patientRegisterDate = patientRegisterDate;
        this.patientSex = patientSex;
    }

    protected Patient(Parcel in) {
        patientBirthDate = in.readString();
        patientEmail = in.readString();
        patientName = in.readString();
        patientObservations = in.readString();
        patientPhone = in.readString();
        patientRegisterDate = in.readString();
        patientSex = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientObservations() {
        return patientObservations;
    }

    public void setPatientObservations(String patientObservations) {
        this.patientObservations = patientObservations;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientRegistrationDate() {
        return patientRegisterDate;
    }

    public void setPatientRegistrationDate(String patientRegisterDate) {
        this.patientRegisterDate = patientRegisterDate;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patientBirthDate);
        dest.writeString(patientEmail);
        dest.writeString(patientName);
        dest.writeString(patientObservations);
        dest.writeString(patientPhone);
        dest.writeString(patientRegisterDate);
        dest.writeString(patientSex);
    }

    @NonNull
    @Override
    public String toString() {
        return this.getPatientName()+" "+this.getPatientPhone();
    }
}
