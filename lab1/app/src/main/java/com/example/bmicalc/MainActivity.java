package com.example.bmicalc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.service.autofill.Validator;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText height;
    private EditText weight;
    private TextView resultKcal;
    private TextView resultBMI;
    private TextView resultTitle;
    private TextView age;
    private Button calculate;
    private Button showRecipe;

    private String kcal = "";

    private boolean sexMan = false;

    private final List<BmiOption> bmiOptions = new ArrayList<BmiOption>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        resultKcal = (TextView) findViewById(R.id.result_kcal);
        resultBMI = (TextView) findViewById(R.id.result_bmi);
        resultTitle = (TextView) findViewById(R.id.result_title);
        calculate = (Button) findViewById(R.id.form_calculate);
        showRecipe = (Button) findViewById(R.id.form_show_recipe);
        age = (EditText) findViewById(R.id.age);

        bmiOptions.add(new BmiOption(30.0, "OBESE"));
        bmiOptions.add(new BmiOption(25.0, "OVERWEIGHT"));
        bmiOptions.add(new BmiOption(18.5, "IDEAL"));
        bmiOptions.add(new BmiOption(0.0, "UNDERWEIGHT"));
    }

    public void calculate(View v) {

        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();
        String ageStr = age.getText().toString();

        float heightValue = Float.parseFloat(heightStr);
        float weightValue = Float.parseFloat(weightStr);
        int ageValue = Integer.parseInt(ageStr);

        calculateAndDisplayKcal(heightValue, weightValue, ageValue);
        calculateAndDisplayBmi(heightValue / 100, weightValue);


        show();
        calculate.setVisibility(View.INVISIBLE);
        showRecipe.setVisibility(View.VISIBLE);
    }

    private void calculateAndDisplayBmi(float height, float weight) {
        float bmi = weight / (height * height);

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

    private void calculateAndDisplayKcal(float height, float weight, int age) {

        double result = 0.00;

        if (!sexMan) {
            result = 655.1 + (9.653 * weight) + (1.85 * height) - (4.676 * age);
        } else {
            result = 66.5 + (13.75 * weight) + (5.003 * height) - (6.775 * age);
        }

        kcal = String.format(Locale.ENGLISH, "%.2f", result);

        resultKcal.setText(String.format("Daily kcal: %s", kcal));
    }


    private void displayBMI(float bmi, String label) {
        DecimalFormat df = new DecimalFormat("##.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String bmiResult = df.format(bmi);

        resultBMI.setText(String.format("BMI: %s is %s", bmiResult, label));

    }

    private void show() {
        resultTitle.setVisibility(View.VISIBLE);
        resultKcal.setVisibility(View.VISIBLE);
        resultBMI.setVisibility(View.VISIBLE);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.form_sex_man:
                if (checked)
                    sexMan = true;
                break;
            case R.id.form_sex_woman:
                if (checked)
                    sexMan = false;
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void onButtonClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), RecipesActivity.class);
        myIntent.putExtra("kcal", kcal);
        startActivity(myIntent);
    }

    public void quiz(View v) {
        Intent myIntent = new Intent(getBaseContext(), QuizActivity.class);
        startActivity(myIntent);
    }

}