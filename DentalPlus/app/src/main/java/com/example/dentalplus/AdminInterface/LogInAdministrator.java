package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogInAdministrator extends AppCompatActivity {

    EditText etusername, etpassword;
    Button btnLogAdmin;
    CheckBox cbRMAdmin;

    DatabaseReference dbAdmin;

    public static Admin adminLogIn=new Admin();
    public static ArrayList<Admin> adminList;

    private static String PREF = "PREF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_administrator);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        etusername=(EditText) findViewById(R.id.etusername);
        etpassword=(EditText)findViewById(R.id.etpassword);
        cbRMAdmin=(CheckBox)findViewById(R.id.cbRMAdmin);
        btnLogAdmin=(Button)findViewById(R.id.btnLogAdmin);

        receiveData();

        dbAdmin= FirebaseDatabase.getInstance().getReference("admins");
        adminList=new ArrayList<>();

        dbAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminList.clear();
                for (DataSnapshot admins :dataSnapshot.getChildren()){
                    Admin adm=admins.getValue(Admin.class);
                    adminList.add(adm);
                }
                btnLogAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strPassword = etpassword.getText().toString();
                        String strUsername=etusername.getText().toString();
                        for (Admin adm :adminList){
                            if(etusername.getText().toString().equals(adm.getUsername())){
                                adminLogIn=adm;
                                break;
                            }
                            else adminLogIn.setPassword("");
                        }

                        if (adminLogIn.isEnabled()==true) {
                            if (etusername.getText().toString().isEmpty() || etpassword.getText().toString().isEmpty()) {
                                Toast.makeText(LogInAdministrator.this, "Campuri necompletate!", Toast.LENGTH_LONG).show();
                            } else if (etpassword.getText().toString().equals(adminLogIn.getPassword())) {
                                if (cbRMAdmin.isChecked()) {
                                    saveData();
                                } else {
                                    etusername.setText("");
                                    etpassword.setText("");
                                    saveData();
                                }
                                Intent intent1 = new Intent(getApplicationContext(), AdminActivity.class);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(LogInAdministrator.this, "Sigur aveti cont de administrator?", Toast.LENGTH_LONG).show();
                            }
                        }
                            else   Toast.makeText(LogInAdministrator.this, "Nu va puteti loga!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void receiveData() {
        SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String user_name = preferences.getString("USERNAME", null);
        String pass_word = preferences.getString("PASSWORD", null);

        etusername.setText(user_name);
        etpassword.setText(pass_word);
    }

    public void saveData() {
        String user_name = etusername.getText().toString();
        String pass_word = etpassword.getText().toString();

        SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("USERNAME", user_name);
        editor.putString("PASSWORD", pass_word);
        editor.commit();
    }
}
