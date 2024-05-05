package com.example.assignment3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewName, textViewEmail, textViewRole, textViewIncome, textViewEducation, textViewMarital, textViewLiving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile");
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewRole = findViewById(R.id.textViewRole);
        textViewIncome = findViewById(R.id.textViewProfileIncome);
        textViewEducation = findViewById(R.id.textViewEducation);
        textViewMarital = findViewById(R.id.textViewMarital);
        textViewLiving = findViewById(R.id.textViewLiving);

        textViewName.setText(IdentificationActivity.response.getName());
        textViewEmail.setText(IdentificationActivity.response.getEmail());
        textViewRole.setText(IdentificationActivity.response.getRole());
        textViewIncome.setText(IdentificationActivity.response.getIncome());
        textViewEducation.setText(IdentificationActivity.response.getEducation());
        textViewMarital.setText(IdentificationActivity.response.getMarital());
        textViewLiving.setText(IdentificationActivity.response.getLiving());

    }
}