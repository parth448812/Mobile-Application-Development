//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


public class DemographicActivity extends AppCompatActivity {

    TextView textViewSelectedEducation, textViewSelectedMaritalStatus, textViewSelectedLivingStatus, textViewSelectedIncome;
    String education_level, marital_status, living_status, income;
    public ActivityResultLauncher<Intent> startEducationForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        assert data != null;
                        education_level = data.getStringExtra(SelectEducationActivity.KEY_LEVEL);
                        textViewSelectedEducation.setText(education_level);
                        IdentificationActivity.response.setEducation(education_level);
                    } else {
                        if (education_level != "N/A") {
                            ;
                        }
                        else{
                            textViewSelectedEducation.setText("N/A");
                        }
                    }
                }
            }
    );
    public ActivityResultLauncher<Intent> startMaritalForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        assert data != null;
                        String marital_status = data.getStringExtra(SelectMaritalActivity.KEY_STATUS);
                        textViewSelectedMaritalStatus.setText(marital_status);
                        IdentificationActivity.response.setMaritial_status(marital_status);
                    } else {
                        if (marital_status != "N/A") {
                            ;
                        }
                        else{
                            textViewSelectedMaritalStatus.setText("N/A");
                        }

                    }
                }
            }
    );
    public ActivityResultLauncher<Intent> startLivingForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        assert data != null;
                        String living_status = data.getStringExtra(SelectLivingActivity.KEY_LIVING);
                        textViewSelectedLivingStatus.setText(living_status);
                        IdentificationActivity.response.setLiving_status(living_status);
                    } else {
                        if (living_status != "N/A") {
                            ;
                        }
                        else{
                            textViewSelectedLivingStatus.setText("N/A");
                        }

                    }
                }
            }
    );
    public ActivityResultLauncher<Intent> startIncomeForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        assert data != null;
                        String income = data.getStringExtra(SelectIncomeActivity.KEY_INCOME);
                        textViewSelectedIncome.setText(income);
                        IdentificationActivity.response.setIncome(income);
                    } else {
                        if (income != "N/A") {
                            ;
                        }
                        else{
                            textViewSelectedIncome.setText("N/A");
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);
        setTitle("Demographic Info");

        textViewSelectedEducation = findViewById(R.id.textViewSelectedEducation);
        textViewSelectedMaritalStatus = findViewById(R.id.textViewSelectedMaritalStatus);
        textViewSelectedLivingStatus = findViewById(R.id.textViewSelectedLivingStatus);
        textViewSelectedIncome = findViewById(R.id.textViewSelectedIncome);

        findViewById(R.id.buttonEducationSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemographicActivity.this, SelectEducationActivity.class);
                startEducationForResult.launch(intent);
            }
        });
        findViewById(R.id.buttonMaritalSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemographicActivity.this, SelectMaritalActivity.class);
                startMaritalForResult.launch(intent);
            }
        });
        findViewById(R.id.buttonLivingSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemographicActivity.this, SelectLivingActivity.class);
                startLivingForResult.launch(intent);
            }
        });
        findViewById(R.id.buttonIncomeSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemographicActivity.this, SelectIncomeActivity.class);
                startIncomeForResult.launch(intent);
            }
        });
        findViewById(R.id.buttonDemoNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String education = textViewSelectedEducation.getText().toString();
                String marital = textViewSelectedMaritalStatus.getText().toString();
                String living = textViewSelectedLivingStatus.getText().toString();
                String income1 = textViewSelectedIncome.getText().toString();

                if(education.isEmpty() || education.equals("N/A")){
                    Toast.makeText(DemographicActivity.this, "Select Education Level!!", Toast.LENGTH_SHORT).show();
                } else if(marital.isEmpty() || marital.equals("N/A")){
                    Toast.makeText(DemographicActivity.this, "Select Marital Status!!", Toast.LENGTH_SHORT).show();
                } else if(living.isEmpty() || living.equals("N/A")){
                    Toast.makeText(DemographicActivity.this, "Select Living Status!!", Toast.LENGTH_SHORT).show();
                } else if(income1.isEmpty() || income1.equals("N/A")){
                    Toast.makeText(DemographicActivity.this, "Select Income !!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(DemographicActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}