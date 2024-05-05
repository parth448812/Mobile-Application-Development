//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SelectEducationActivity extends AppCompatActivity {


    RadioGroup radioGroupEducation;
    public static final String KEY_LEVEL = "LEVEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_education);
        setTitle("Select Education");

        radioGroupEducation = findViewById(R.id.radioGroupEducation);

        findViewById(R.id.buttonEducationSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupEducation.getCheckedRadioButtonId();
                String level = getString(R.string.high_school);
                if(selectedId == R.id.radioButtonBelow){
                    level = getString(R.string.below_high_school);
                } else if(selectedId == R.id.radioButtonHigh){
                    level = getString(R.string.high_school);
                } else if(selectedId == R.id.radioButtonBachelors){
                    level = getString(R.string.bachelors);
                } else if(selectedId == R.id.radioButtonMasters){
                    level = getString(R.string.masters);
                } else if(selectedId == R.id.radioButtonPHD){
                    level = getString(R.string.phd);
                } else if(selectedId == R.id.radioButtonTrade){
                    level = getString(R.string.trade);
                } else if(selectedId == R.id.radioButtonEducationPreferNot){
                    level = getString(R.string.prefer_not);
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_LEVEL, level);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonEducationCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}