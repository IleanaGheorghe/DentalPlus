package com.example.dentalplus.AdminInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.Doctor;
import com.example.dentalplus.clase.Specialization;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class CreateUserAdmin extends AppCompatActivity {

    EditText etLname, etFname, etEmail, etPassword, etPhone, etBirthDate, etSalary, etUsername;
    RadioButton rbF, rbM, rbActiv, rbInactiv;
    Button btnCreateNewUser;
    Spinner spinnerSpec;

    DatabaseReference dbReference;

    ArrayList<Doctor> doctors;
    ArrayAdapter<Specialization> arrayAdapter;

    boolean okGender=true;
    boolean okActiv=true;
    String gender, specSelected, specSelectedId;
    boolean isActiv;

    private DatePickerDialog.OnDateSetListener datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_admin);

       Intent intent=getIntent();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        etLname=(EditText)findViewById(R.id.etLname);
        etFname=(EditText)findViewById(R.id.etFname);
        etUsername=(EditText)findViewById(R.id.etUsernameCreate);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etBirthDate=(EditText)findViewById(R.id.etBirthDate);
        rbM=(RadioButton)findViewById(R.id.rbMasculin);
        rbF=(RadioButton)findViewById(R.id.rbFeminin);
        rbActiv=(RadioButton)findViewById(R.id.rbActiv);
        rbInactiv=(RadioButton)findViewById(R.id.rbInactiv);
        btnCreateNewUser=(Button)findViewById(R.id.btnRegisterUser);
        spinnerSpec=(Spinner) findViewById(R.id.spinnerSpecializare);
        etSalary=(EditText)findViewById(R.id.etSalary) ;

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        CreateUserAdmin.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerDialog,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.show();
            }
        });

        datePickerDialog=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"-"+month+"-"+year;
                etBirthDate.setText(date);
            }
        };

        doctors=new ArrayList<>();

        dbReference= FirebaseDatabase.getInstance().getReference("doctors");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctors.clear();

                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Doctor doctor=ds.getValue(Doctor.class);
                    doctors.add(doctor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadDataInSpinner();

        btnCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbF.isChecked()){
                    okGender=true;
                    gender="Feminin";
                } else if (rbM.isChecked()){
                    okGender=true;
                    gender="Masculin";
                } else if (!(rbF.isChecked()) || (rbM.isChecked())){
                    okGender=false;
                    Toast.makeText(CreateUserAdmin.this, "Trebuie selectat genul!", Toast.LENGTH_SHORT).show();
                }

                if (rbActiv.isChecked()){
                    okActiv=true;
                    isActiv=true;
                } else if (rbInactiv.isChecked()){
                    okActiv=true;
                    isActiv=false;
                } else if (!(rbActiv.isChecked()) || (rbInactiv.isChecked())){
                    okActiv=false;
                    Toast.makeText(CreateUserAdmin.this, "Trebuie selectat daca este un cont activ sau inactiv!", Toast.LENGTH_LONG).show();
                }

                if(okActiv==true && okGender==true){
                    String lname=etLname.getText().toString();
                    String fname=etFname.getText().toString();
                    String email=etEmail.getText().toString();
                    String username=etUsername.getText().toString();
                    String password=etPassword.getText().toString();
                    String phone=etPhone.getText().toString();
                    String birtDate=etBirthDate.getText().toString();
                    String salary=etSalary.getText().toString();

                    if (lname.equals("")|| fname.equals("")||email.equals("")||password.equals("")||phone.equals("")||birtDate.equals("")||specSelectedId.equals("")||etSalary.equals("")||etUsername.equals("")
                    ||lname.isEmpty()||fname.isEmpty()||email.isEmpty()||password.isEmpty()||phone.isEmpty()||birtDate.isEmpty()||salary.isEmpty()||username.isEmpty()){
                        Toast.makeText(CreateUserAdmin.this, "Toate campurile trebuie completate!",Toast.LENGTH_SHORT).show();
                    } else {
                        if(phone.length()>10 ||phone.length()<10)
                        {
                            Toast.makeText(CreateUserAdmin.this, "Numarul de telefon trebuie sa aiba 10 cifre!",Toast.LENGTH_SHORT).show();
                        }
                        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(CreateUserAdmin.this, "Introduceti un email valid!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean okDoctor=true;
                            for (Doctor doctor : doctors) {
                                if (etUsername.getText().toString().equals(doctor.getUsername())) {
                                    Toast.makeText(CreateUserAdmin.this, "Exista deja un doctor cu acest nume de utilizator!", Toast.LENGTH_SHORT).show();
                                    okDoctor=false;
                                    break;
                                }
                            }
                            if(okDoctor==true){
                                    dbReference.child(username).child("firstName").setValue(fname);
                                    dbReference.child(username).child("lastName").setValue(lname);
                                    dbReference.child(username).child("email").setValue(email);
                                    dbReference.child(username).child("password").setValue(password);
                                    dbReference.child(username).child("phone").setValue(phone);
                                    dbReference.child(username).child("birthDate").setValue(birtDate);
                                    dbReference.child(username).child("gender").setValue(gender);
                                    dbReference.child(username).child("enabled").setValue(isActiv);
                                    dbReference.child(username).child("specCode").setValue(specSelectedId);
                                    dbReference.child(username).child("salary").setValue(salary);
                                    dbReference.child(username).child("username").setValue(username);

                                    etEmail.setText("");
                                    etPassword.setText("");
                                    etLname.setText("");
                                    etFname.setText("");
                                    etPhone.setText("");
                                    etBirthDate.setText("");
                                    etSalary.setText("");
                                    etUsername.setText("");

                                    Toast.makeText(CreateUserAdmin.this, "Cont creat cu succes!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

        });
    }

    private void loadDataInSpinner(){
        arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, AdminActivity.specializations);
        spinnerSpec.setAdapter(arrayAdapter);
        spinnerSpec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Specialization spec=(Specialization) parent.getSelectedItem();
                specSelectedId=spec.getSpecializationCode();
                specSelected=spec.getSpecializationName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
