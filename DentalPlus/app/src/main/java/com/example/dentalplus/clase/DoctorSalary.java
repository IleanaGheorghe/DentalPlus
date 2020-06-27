package com.example.dentalplus.clase;

public class DoctorSalary {
   String nume;
   Float salariul;

    public DoctorSalary(String nume, Float salariul) {
        this.nume = nume;
        this.salariul = salariul;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Float getSalariul() {
        return salariul;
    }

    public void setSalariul(Float salariul) {
        this.salariul = salariul;
    }
}
