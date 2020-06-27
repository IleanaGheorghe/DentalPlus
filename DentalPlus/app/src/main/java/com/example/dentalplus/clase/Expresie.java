package com.example.dentalplus.clase;

public class Expresie {
    int id;
    String nume;
    String path;

    public Expresie() {
    }

    public Expresie(int id, String nume, String path) {
        this.id = id;
        this.nume = nume;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
