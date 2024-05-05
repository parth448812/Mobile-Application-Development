//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IdentificationActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail;
    RadioGroup radioGroupRole;
    public static final String KEY_PROFILE = "PROFILE";
     static Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        setTitle("Indentification Info");

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);

        radioGroupRole = findViewById(R.id.radioGroupRole);
        findViewById(R.id.buttonIDNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();

                int selectedRole = radioGroupRole.getCheckedRadioButtonId();
                String role = getString(R.string.student);
                if(selectedRole == R.id.radioButtonStudent){
                    role = getString(R.string.student);
                } else if(selectedRole == R.id.radioButtonEmployee){
                    role = getString(R.string.employee);
                } else if(selectedRole == R.id.radioButtonIDOther){
                    role = getString(R.string.other);
                }



                if(name.isEmpty()){
                    Toast.makeText(IdentificationActivity.this, "Enter name!!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(IdentificationActivity.this, "Enter email!!", Toast.LENGTH_SHORT).show();
                } else {
                    response = new Response(name, email, role, null, null, null, null);
                    Intent intent = new Intent(IdentificationActivity.this, DemographicActivity.class);
                    intent.putExtra(KEY_PROFILE, response);
                    startActivity(intent);
                }


            }
        });
    }
}