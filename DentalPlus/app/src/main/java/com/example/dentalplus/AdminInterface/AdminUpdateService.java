package com.example.dentalplus.AdminInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dentalplus.R;
import com.example.dentalplus.clase.ServiceAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUpdateService extends AppCompatActivity {

    EditText etCodServiciuEditare, etNumeServiciuEditare, etPretEditare;
    Button editareServiciu;

    DatabaseReference dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_service);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        etCodServiciuEditare=(EditText)findViewById(R.id.etEditareCodServ);
        etNumeServiciuEditare=(EditText)findViewById(R.id.etEditareNumeServ);
        etPretEditare=(EditText)findViewById(R.id.etEditarePretServ);
        editareServiciu=(Button)findViewById(R.id.btnActualizareServiciu);

        etCodServiciuEditare.setEnabled(false);
        etCodServiciuEditare.setText(ServiceAdapter.serviceEditate.getServiceCode());
        etNumeServiciuEditare.setText(ServiceAdapter.serviceEditate.getServiceName());
        etPretEditare.setText(ServiceAdapter.serviceEditate.getServicePrice());

        dbService= FirebaseDatabase.getInstance().getReference("services").child(ServiceAdapter.serviceEditate.getServiceCode().toString());
        editareServiciu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok=true;
                if (etNumeServiciuEditare.getText().toString().equals("")||etNumeServiciuEditare.getText().toString().isEmpty()){
                    Toast.makeText(AdminUpdateService.this,"Numele serviciului nu poate lipsi!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }
                if(etPretEditare.getText().toString().equals("")||etPretEditare.getText().toString().isEmpty()){
                    Toast.makeText(AdminUpdateService.this,"Pretul serviciului nu poate lipsi!", Toast.LENGTH_SHORT).show();
                    ok=false;
                }

                if(ok==true){
                    dbService.child("serviceName").setValue(etNumeServiciuEditare.getText().toString());
                    dbService.child("servicePrice").setValue(etPretEditare.getText().toString());
                    ServiceAdapter.services.clear();
                    Toast.makeText(AdminUpdateService.this,"Informatii actualizate!",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),AdminActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });
    }
}
