package com.example.dentalplus.AdminInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.DoctorAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AdminUpdateDoctor extends AppCompatActivity {

    EditText editareLname, editareFname, editareUsername, editareParola, editareEmail, editareTelefon, editareDataNastere, editareSalariu;
    RadioButton rbA, rbIn;
    Button btnUpdateDoctor;

    DatabaseReference dbDoctors;
    boolean okActiv=true,isActiv;
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_doctor);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        editareLname=(EditText)findViewById(R.id.etEditareLname);
        editareFname=(EditText)findViewById(R.id.etEditareFname);
        editareUsername=(EditText)findViewById(R.id.etEditareUsername);
        editareParola=(EditText)findViewById(R.id.etEditarePassword);
        editareEmail=(EditText)findViewById(R.id.etEditareEmail);
        editareTelefon=(EditText)findViewById(R.id.etEditarePhone);
        editareDataNastere=(EditText)findViewById(R.id.etEditareBirthDate);
        editareSalariu=(EditText)findViewById(R.id.etEditareSalary);
        rbA=(RadioButton)findViewById(R.id.rbActivEdit);
        rbIn=(RadioButton)findViewById(R.id.rbInactivEdit);
        btnUpdateDoctor=(Button)findViewById(R.id.btnUpdateDoctor);

        editareLname.setText(DoctorAdapter.doctorEditare.getLastName());
        editareFname.setText(DoctorAdapter.doctorEditare.getFirstName());
        editareUsername.setText(DoctorAdapter.doctorEditare.getUsername());
        editareParola.setText(DoctorAdapter.doctorEditare.getPassword());
        editareEmail.setText(DoctorAdapter.doctorEditare.getEmail());
        editareTelefon.setText(DoctorAdapter.doctorEditare.getPhone());
        editareDataNastere.setText(DoctorAdapter.doctorEditare.getBirthDate());
        editareSalariu.setText(DoctorAdapter.doctorEditare.getSalary());

        if(DoctorAdapter.doctorEditare.isEnabled())
            rbA.setChecked(true);
        else
            rbIn.setChecked(true);

        editareDataNastere.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        AdminUpdateDoctor.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerDialog,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.show();
            }
        });

        datePickerDialog=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"-"+month+"-"+year;
                editareDataNastere.setText(date);
            }
        };
        dbDoctors= FirebaseDatabase.getInstance().getReference("doctors").child(DoctorAdapter.doctorEditare.getUsername());

        btnUpdateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok=true;
                if (editareLname.getText().toString().isEmpty()||editareLname.getText().toString().equals("")){
                    Toast.makeText(AdminUpdateDoctor.this, "Numele trebuie completat!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if (editareFname.getText().toString().isEmpty()||editareFname.getText().toString().equals("")){
                    Toast.makeText(AdminUpdateDoctor.this, "Prenumele trebuie completat!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if (editareParola.getText().toString().isEmpty()||editareParola.getText().toString().equals("")){
                    Toast.makeText(AdminUpdateDoctor.this, "Parola trebuie completata!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if (editareEmail.getText().toString().isEmpty()||editareEmail.getText().toString().equals("")|| !Patterns.EMAIL_ADDRESS.matcher(editareEmail.getText().toString()).matches()){
                    Toast.makeText(AdminUpdateDoctor.this, "Completati cu un email valid!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if (editareSalariu.getText().toString().isEmpty()||editareSalariu.getText().toString().equals("")){
                    Toast.makeText(AdminUpdateDoctor.this, "Salariul trebuie completat!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if (editareTelefon.getText().toString().isEmpty()||editareTelefon.getText().toString().equals("")||editareTelefon.getText().toString().length()<10||editareTelefon.getText().toString().length()>10){
                    Toast.makeText(AdminUpdateDoctor.this, "Completati cu un numar de telefon valid!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if (editareDataNastere.getText().toString().isEmpty()||editareDataNastere.getText().toString().equals("")){
                    Toast.makeText(AdminUpdateDoctor.this, "Data nastere trebuie completata!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }

                if (rbA.isChecked()){
                    okActiv=true;
                    isActiv=true;
                } else if (rbIn.isChecked()){
                    okActiv=true;
                    isActiv=false;
                } else if (!(rbA.isChecked()) || (rbIn.isChecked())){
                    okActiv=false;
                    Toast.makeText(AdminUpdateDoctor.this, "Trebuie selectat daca este un cont activ sau inactiv!", Toast.LENGTH_LONG).show();
                }

                if(okActiv==true && ok==true){
                    dbDoctors.child("firstName").setValue(editareFname.getText().toString());
                    dbDoctors.child("lastName").setValue(editareLname.getText().toString());
                    dbDoctors.child("email").setValue(editareEmail.getText().toString());
                    dbDoctors.child("password").setValue(editareParola.getText().toString());
                    dbDoctors.child("phone").setValue(editareTelefon.getText().toString());
                    dbDoctors.child("birthDate").setValue(editareDataNastere.getText().toString());
                    dbDoctors.child("enabled").setValue(isActiv);
                    dbDoctors.child("salary").setValue(editareSalariu.getText().toString());

                    Toast.makeText(AdminUpdateDoctor.this, "Cont actualizat cu succes!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),AdminActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }
        });
    }
}
