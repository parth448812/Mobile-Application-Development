//Assignment 01
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
//import java.lang.Math;

public class MainActivity extends AppCompatActivity {


    EditText editTextListPrice;
    TextView textViewDiscountAmount, textViewFinalPriceAmount, textViewCustomPercent;
    RadioGroup radioGroupSalePercent;
    SeekBar seekBarCustom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextListPrice = findViewById(R.id.editTextListPrice);
        radioGroupSalePercent = findViewById(R.id.radioGroupSalePercent);
        textViewDiscountAmount = findViewById(R.id.textViewDiscountAmount);
        textViewFinalPriceAmount = findViewById(R.id.textViewFinalPriceAmount);
        textViewCustomPercent = findViewById(R.id.textViewCustomPercent);
        seekBarCustom = findViewById(R.id.seekBarCustom);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextListPrice.setText("");
                radioGroupSalePercent.check(R.id.radioButtonTen);
                seekBarCustom.setProgress(25);
                textViewCustomPercent.setText("25%");
                textViewDiscountAmount.setText("0.00");
                textViewFinalPriceAmount.setText("0.00");

            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String enteredPrice = editTextListPrice.getText().toString();
                if(enteredPrice.equals("")){
                    Toast.makeText(MainActivity.this, "Enter Item Price", Toast.LENGTH_SHORT).show();
                    textViewDiscountAmount.setText("");
                    textViewFinalPriceAmount.setText("");
                }

                double price = Double.valueOf(enteredPrice);
                double sale = 0.10;


                if(radioGroupSalePercent.getCheckedRadioButtonId() == R.id.radioButtonFifteen){
                    sale = 0.15;
                }
                else if (radioGroupSalePercent.getCheckedRadioButtonId() == R.id.radioButtonEighteen) {
                    sale = 0.18;
                }
                else if (radioGroupSalePercent.getCheckedRadioButtonId() == R.id.radioButtonCustom) {
                    sale =  ((double)seekBarCustom.getProgress())/100.0;
                }
                double discount = price * sale;
                double final_amount = price - discount;
                textViewDiscountAmount.setText(String.valueOf(discount));
                textViewFinalPriceAmount.setText(String.valueOf(final_amount));



            }
        });

        seekBarCustom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewCustomPercent.setText(String.valueOf(progress) + "%");
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