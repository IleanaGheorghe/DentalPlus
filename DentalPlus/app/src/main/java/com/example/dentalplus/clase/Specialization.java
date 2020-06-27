package com.example.dentalplus.clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Specialization implements Parcelable {
    String specializationCode;
    String specializationName;

    public Specialization(){
    }

    public Specialization(String specializationCode, String specializationName) {
        this.specializationName = specializationName;
        this.specializationCode = specializationCode;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public String getSpecializationCode() {
        return specializationCode;
    }

    public void setSpecializationCode(String specializationCode) {
        this.specializationCode = specializationCode;
    }

    protected Specialization(Parcel in) {
        specializationName = in.readString();
        specializationCode = in.readString();
    }

    public static final Creator<Specialization> CREATOR = new Creator<Specialization>() {
        @Override
        public Specialization createFromParcel(Parcel in) {
            return new Specialization(in);
        }

        @Override
        public Specialization[] newArray(int size) {
            return new Specialization[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(specializationName);
        dest.writeString(specializationCode);
    }

    @Override
    public String toString() {
        return this.specializationName;
    }
}
