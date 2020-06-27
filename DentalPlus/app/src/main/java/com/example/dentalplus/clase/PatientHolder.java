package com.example.dentalplus.clase;

public class PatientHolder {
    private String patientName;
    private String patientPhone;
    private String patientRegisterDate;

    public PatientHolder() {
    }

    public PatientHolder(String patientName, String patientPhone, String patientRegisterDate) {
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.patientRegisterDate = patientRegisterDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientRegisterDate() {
        return patientRegisterDate;
    }

    public void setPatientRegisterDate(String patientRegisterDate) {
        this.patientRegisterDate = patientRegisterDate;
    }
}
