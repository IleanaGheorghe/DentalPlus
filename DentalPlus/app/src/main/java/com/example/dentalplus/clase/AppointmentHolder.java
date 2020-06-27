package com.example.dentalplus.clase;

public class AppointmentHolder {
    String appId;
    String patient;
    String date;
    String startH;
    String endH;
    String serviceCode;

    public AppointmentHolder(){}

    public AppointmentHolder(String appId,String patient, String date, String startH, String endH, String serviceCode) {
        this.appId=appId;
        this.patient = patient;
        this.date = date;
        this.startH = startH;
        this.endH = endH;
        this.serviceCode = serviceCode;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartH() {
        return startH;
    }

    public void setStartH(String startH) {
        this.startH = startH;
    }

    public String getEndH() {
        return endH;
    }

    public void setEndH(String endH) {
        this.endH = endH;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
