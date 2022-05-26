package com.example.taxulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText txtprice, txtOther;
        TextView txtvResult, txtvTax;
        CheckBox chkVat, chkService, chkOther;
        Button btnCalc;
        txtprice = findViewById(R.id.txtPrice);
        txtOther = findViewById(R.id.txtOther);
        txtvResult = findViewById(R.id.txtvResult);
        txtvTax = findViewById(R.id.txtvTax);
        chkVat = findViewById(R.id.chkVat);
        chkService = findViewById(R.id.chkService);
        chkOther = findViewById(R.id.chkOther);
        btnCalc = findViewById(R.id.btnCalc);
        btnCalc = findViewById(R.id.btnCalc);
        txtOther.setEnabled(false);
        DecimalFormat df = new DecimalFormat("0.00");
        chkOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    txtOther.setEnabled(true);
                else {
                    txtOther.setEnabled(false);
                    txtOther.setError(null);
                }
            }
        });
        String regex = "^\\d*\\.?\\d*$";
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtprice.getText().toString().matches(regex) || txtprice.getText().toString().isEmpty()) {
                    txtprice.setError("Please enter valid number");
                    txtprice.requestFocus();
                }
                else
                {
                    if((!txtOther.getText().toString().matches(regex) || txtOther.getText().toString().isEmpty()) && chkOther.isChecked())
                    {
                        txtOther.setError("Please enter valid number");
                        txtOther.requestFocus();
                    }
                    else {
                        float factor = 1F;
                        if(chkVat.isChecked()) {
                            factor *= 1.14;
                        }
                        if(chkService.isChecked()) {
                            factor *= 1.12;
                        }
                        if(chkOther.isChecked()) {
                            factor *= (Float.parseFloat(txtOther.getText().toString())+100)/100;
                        }
                        Float total;
                        total = Float.parseFloat(txtprice.getText().toString()) * factor;
                        txtvResult.setText("You should pay: \n" + df.format(total) +"LE");
                        txtvTax.setText("Total tax is: \n" + (factor * 100 - 100) + "%");
                    }
                }

            }
        });

    }
    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Close Application")
                .setMessage("Are you sure you want to exit the app?")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).create().show();
    }
}