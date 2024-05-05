//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLivingActivity extends AppCompatActivity {
    public static final String KEY_LIVING = "LIVING";
    RadioGroup radioGroupLiving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_living);

        setTitle("Select Living Status");

        radioGroupLiving = findViewById(R.id.radioGroupLiving);

        findViewById(R.id.buttonLivingSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupLiving.getCheckedRadioButtonId();
                String living = getString(R.string.homeowner);
                if(selectedId == R.id.radioButtonHomeowner){
                    living = getString(R.string.homeowner);
                } else if(selectedId == R.id.radioButtonRenter){
                    living = getString(R.string.renter);
                } else if(selectedId == R.id.radioButtonLessee){
                    living = getString(R.string.lessee);
                } else if(selectedId == R.id.radioButtonLivingOther){
                    living = getString(R.string.other);
                } else if(selectedId == R.id.radioButtonLivingPreferNot){
                    living = getString(R.string.prefer_not);
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_LIVING, living);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonLivingCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}