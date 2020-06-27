package com.example.dentalplus.clase;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Admin implements Parcelable {
    private String username;
    private String password;
    boolean enabled;

    public Admin(){
    }

    public Admin(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    protected Admin(Parcel in) {
        username = in.readString();
        password = in.readString();
        enabled = in.readByte() != 0;
    }

    public static final Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        String rez = "";

        if(this.enabled == false) {
            rez =  this.username + "\n" + "   nu are drept sa se conecteze";
        } else
            rez = this.username + "\n" + "   is allowed to connect";

        return rez;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeByte((byte) (enabled ? 1 : 0));
    }
}
