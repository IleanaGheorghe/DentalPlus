package com.example.dentalplus.DoctorInterface;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalplus.MainActivity;
import com.example.dentalplus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
    public static EditText etPass, etNewPass, etRetypePass;
    TextView tvchangePass;
    Button btnSave;
    DatabaseReference dbReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        etPass=(EditText)view.findViewById(R.id.etChangePass);
        etNewPass=(EditText)view.findViewById(R.id.etNewPass);
        etRetypePass=(EditText)view.findViewById(R.id.etConfNewPass);

        tvchangePass=(TextView)view.findViewById(R.id.tvchangePass);

        btnSave =(Button)view.findViewById(R.id.btnSavePass);

        dbReference= FirebaseDatabase.getInstance().getReference("doctors").child(MainActivity.doctorLogin.getUsername());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!(etPass.getText().toString().equals(MainActivity.doctorLogin.getPassword()))) ||
                        (!(etNewPass.getText().toString().equals(etRetypePass.getText().toString())))) {
                    Toast.makeText(getActivity(), "Parolele nu se potrivesc!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (etNewPass.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Noua parola nu trebuie sa fie empty!", Toast.LENGTH_SHORT).show();
                    } else {

                        dbReference.child("password").setValue(etNewPass.getText().toString());

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Din motive de securitate veti fi deconectat!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = getActivity().getPackageManager()
                                        .getLaunchIntentForPackage( getActivity().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setTitle("Schimbare parola");
                        alertDialog.show();
                    }
                }
            }
        });
        return view;
    }
}
