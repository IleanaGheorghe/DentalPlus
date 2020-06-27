package com.example.dentalplus.clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor  implements Parcelable {
    String lastName; //nume
    String firstName; //prenume
    String email;
    String password;
    String phone;
    String birthDate;
    String gender;
    boolean enabled;
    String salary;
    String specCode;
    String username;


    public Doctor (){
    }

    public Doctor(String lastName, String firstName, String email, String password, String phone, String birthDate, String gender, boolean enabled, String specCode, String salary, String username) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.enabled = enabled;
        this.specCode=specCode;
        this.salary=salary;
        this.username=username;
    }

    protected Doctor(Parcel in) {
        lastName = in.readString();
        firstName = in.readString();
        email = in.readString();
        password = in.readString();
        phone = in.readString();
        birthDate = in.readString();
        gender = in.readString();
        enabled = in.readByte() != 0;
        specCode=in.readString();
        salary=in.readString();
        username=in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(String.valueOf(phone));
        dest.writeString(birthDate);
        dest.writeString(gender);
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeString(specCode);
        dest.writeString(String.valueOf(salary));
        dest.writeString(username);
    }

    @Override
    public String toString() {
        return this.getFirstName()+" "+this.getLastName();
    }
}
