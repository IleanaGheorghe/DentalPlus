package com.example.dentalplus.DoctorInterface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePersonalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePersonalInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePersonalInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePersonalInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePersonalInfoFragment newInstance(String param1, String param2) {
        ChangePersonalInfoFragment fragment = new ChangePersonalInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseReference dbReference;
    EditText etNume, etPrenume, etEmail, etTelefon;
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_personal_info, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dbReference = FirebaseDatabase.getInstance().getReference("doctors").child(MainActivity.doctorLogin.getUsername());

        etNume=(EditText)view.findViewById(R.id.etNume);
        etPrenume=(EditText)view.findViewById(R.id.etPRenume);
        etEmail=(EditText)view.findViewById(R.id.etEmail);
        etTelefon=(EditText)view.findViewById(R.id.etTelefon);
        btnSave=(Button) view.findViewById(R.id.buttonSave);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                etNume.setHint(dataSnapshot.child("lastName").getValue(String.class).toString());
                etPrenume.setHint(dataSnapshot.child("firstName").getValue(String.class).toString());
                etEmail.setHint(dataSnapshot.child("email").getValue(String.class).toString());
                etTelefon.setHint(dataSnapshot.child("phone").getValue(String.class).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                if (etNume.getText().toString().equals("") && etPrenume.getText().toString().equals("")
                        && etEmail.getText().toString().equals("") && etTelefon.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Nu pot exista campuri necompletate!", Toast.LENGTH_SHORT).show();
                    ok = false;
                }
                else {
                    ok = true;
                    if (etNume.getText().toString().equals(""))
                        dbReference.child("lastName").setValue(etNume.getHint().toString());
                    else
                        dbReference.child("lastName").setValue(etNume.getText().toString());

                    if (etTelefon.getText().toString().equals(""))
                        dbReference.child("phone").setValue(etTelefon.getHint().toString());
                    else if (etTelefon.getText().length() < 10 || etTelefon.getText().length()>10) {
                        Toast.makeText(getActivity(), "Numar de telefon invalid!", Toast.LENGTH_SHORT).show();
                        ok = false;
                    }
                    else
                        dbReference.child("phone").setValue(etTelefon.getText().toString());

                    if (etEmail.getText().toString().equals(""))
                        dbReference.child("email").setValue(etEmail.getHint().toString());
                    else {
                        if (isEmailValid(etEmail.getText().toString()) == false) {
                            Toast.makeText(getActivity(), "Email invalid!", Toast.LENGTH_SHORT).show();
                            ok = false;
                        } else {
                            dbReference.child("email").setValue(etEmail.getText().toString());
                        }
                    }

                    if (etPrenume.getText().toString().equals(""))
                        dbReference.child("firstName").setValue(etPrenume.getHint().toString());
                    else
                        dbReference.child("firstName").setValue(etPrenume.getText().toString());
                }

                if (ok == true) {
                    Toast.makeText(getActivity(), "Date personale au fost actualizate!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
