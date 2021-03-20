package com.example.bmicalc;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText height;
    private EditText weight;
    private TextView result;
    private TextView resultTitle;

    private final List<BmiOption> bmiOptions = new ArrayList<BmiOption>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        resultTitle = (TextView) findViewById(R.id.result_title);

        bmiOptions.add(new BmiOption(30.0, "OBESE"));
        bmiOptions.add(new BmiOption(25.0, "OVERWEIGHT"));
        bmiOptions.add(new BmiOption(18.5, "IDEAL"));
        bmiOptions.add(new BmiOption(0.0, "UNDERWEIGHT"));
    }

    public void calculateBMI(View v) {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        float heightValue = Float.parseFloat(heightStr) / 100;
        float weightValue = Float.parseFloat(weightStr);

        float bmi = weightValue / (heightValue * heightValue);
        hideSoftKeyboard(weight);

        for (BmiOption bmiOption :
                bmiOptions) {
            if (bmi >= bmiOption.value) {
                displayBMI(bmi, bmiOption.label);
                return;
            }
        }

        BmiOption lastOption = bmiOptions.get(bmiOptions.size() - 1);
        displayBMI(bmi, lastOption.label);
    }

    private void displayBMI(float bmi, String label) {
        DecimalFormat df = new DecimalFormat("##.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String bmiResult = df.format(bmi);

        result.setText(String.format("%s is %s", bmiResult, label));

        show();
    }

    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    private void show() {
        resultTitle.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);
    }
}