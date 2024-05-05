//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SelectMaritalActivity extends AppCompatActivity {
    public static final String KEY_STATUS = "STATUS";
    RadioGroup radioGroupMarital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_marital);
        setTitle("Select Marital Status");

        radioGroupMarital = findViewById(R.id.radioGroupMarital);

        findViewById(R.id.buttonMaritalSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupMarital.getCheckedRadioButtonId();
                String status = getString(R.string.not_married);
                if(selectedId == R.id.radioButtonNotMarried){
                    status = getString(R.string.not_married);
                } else if(selectedId == R.id.radioButtonMarried){
                    status = getString(R.string.married);
                } else if(selectedId == R.id.radioButtonMaritalPreferNot){
                    status = getString(R.string.prefer_not);
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_STATUS, status);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonMaritalCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}