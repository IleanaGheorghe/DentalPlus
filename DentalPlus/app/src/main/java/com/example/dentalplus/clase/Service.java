package com.example.dentalplus.clase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Service implements Parcelable {
    String serviceCode;
    String serviceName;
    String servicePrice;

    public Service(){
    }

    public Service(String serviceCode, String serviceName, String servicePrice) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    protected Service(Parcel in) {
        serviceCode = in.readString();
        serviceName = in.readString();
        servicePrice = in.readString();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceCode);
        dest.writeString(serviceName);
        dest.writeString(servicePrice);
    }

    @NonNull
    @Override
    public String toString() {
        return this.getServiceName();
    }
}
