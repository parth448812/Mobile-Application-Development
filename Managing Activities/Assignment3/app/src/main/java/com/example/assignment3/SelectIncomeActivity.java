//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectIncomeActivity extends AppCompatActivity {
    public static final String KEY_INCOME = "INCOME";
    SeekBar seekBarIncome;
    TextView textViewIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_income);

        setTitle("Select Income");

        seekBarIncome = findViewById(R.id.seekBarIncome);
        textViewIncome = findViewById(R.id.textViewIncome);

//        seekBarIncome.setProgress(2);
//        textViewIncome.setText(getString(R.string.income_2));


        findViewById(R.id.buttonIncomeSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String income = textViewIncome.getText().toString();
//                if (seekBarIncome.getProgress() == 0){
//                    textViewIncome.setText(getString(R.string.income_0));
//                } else if (seekBarIncome.getProgress() == 1) {
//                    textViewIncome.setText(getString(R.string.income_1));
//                } else if (seekBarIncome.getProgress() == 2) {
//                    textViewIncome.setText(getString(R.string.income_2));
//                } else if (seekBarIncome.getProgress() == 3) {
//                    textViewIncome.setText(getString(R.string.income_3));
//                } else if (seekBarIncome.getProgress() == 4) {
//                    textViewIncome.setText(getString(R.string.income_4));
//                }
                Intent intent = new Intent();
                intent.putExtra(KEY_INCOME, income);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonIncomeCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seekBarIncome.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewIncome.setText(getString(R.string.income_2));
                if (seekBarIncome.getProgress() == 0){
                    textViewIncome.setText(getString(R.string.income_0));
                } else if (seekBarIncome.getProgress() == 1) {
                    textViewIncome.setText(getString(R.string.income_1));
                } else if (seekBarIncome.getProgress() == 2) {
                    textViewIncome.setText(getString(R.string.income_2));
                } else if (seekBarIncome.getProgress() == 3) {
                    textViewIncome.setText(getString(R.string.income_3));
                } else if (seekBarIncome.getProgress() == 4) {
                    textViewIncome.setText(getString(R.string.income_4));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}