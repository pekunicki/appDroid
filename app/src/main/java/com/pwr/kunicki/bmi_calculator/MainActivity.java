package com.pwr.kunicki.bmi_calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pwr.kunicki.bmi.Author;
import com.pwr.kunicki.bmi_calculator.Interfaces.ICountBMI;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bmiResult)
    TextView tv_bmiResult;
    @BindView(R.id.heightEditText)
    EditText et_height;
    @BindView(R.id.weightEditText)
    EditText et_weight;
    @BindView(R.id.heightSystem)
    TextView tv_heightMeasurement;
    @BindView(R.id.weightSystem)
    TextView tv_weightMeasurement;
    @BindView(R.id.measurementSystemEU)
    RadioButton b_euSystem;
    @BindView(R.id.measurementSystemUS)
    RadioButton b_usSystem;
    @BindView(R.id.measurementSystemRadioGroup)
    RadioGroup rg_group;
    private float weight = 0f;
    private float height = 0f;
    private static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GetSharedPreferences();
    }

    @OnClick(R.id.measurementSystemEU)
    public void setEuMeasurementSystem() {
        tv_heightMeasurement.setText(R.string.app_height_measurment_system_eu);
        tv_weightMeasurement.setText(R.string.app_weight_measurment_system_eu);
        clearEtWeightAndHeight();
        et_height.setHint("170");
        et_weight.setHint("65");
    }

    @OnClick(R.id.measurementSystemUS)
    public void setUsMeasurementSystem() {
        tv_heightMeasurement.setText(R.string.app_height_measurment_system_us);
        tv_weightMeasurement.setText(R.string.app_weight_measurment_system_us);
        clearEtWeightAndHeight();
        et_height.setHint("6.2");
        et_weight.setHint("170");
    }


    @OnClick(R.id.calculateButton)
    public void calculateAndShowBMI() {
        Float bmi = 0f;
        ICountBMI bmiCounter;
        try {
            bmiCounter = createBMICounter();
            loadData();
            bmi = bmiCounter != null ? bmiCounter.countBMI(weight, height) : 0;
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            colorText(bmi);
            setBMIText(bmi);
            Utils.hideSoftKeyboard(this);
        }
    }

    private ICountBMI createBMICounter() {
        if (b_euSystem.getId() == rg_group.getCheckedRadioButtonId())
            return new CountBMIForEU();
        else if (b_usSystem.getId() == rg_group.getCheckedRadioButtonId())
            return new CountBMIForUS();
        return null;
    }

    private void setBMIText(Float bmi) {
        tv_bmiResult.setTextColor(colorText(bmi));
        tv_bmiResult.setText(String.format(Locale.ENGLISH, formatNumber(bmi), bmi));
    }

    private String formatNumber(Float bmi) {
        return bmi == 0 ? "%.0f" : "%.2f";
    }

    private int colorText(Float bmi) {
        return bmi == 0 ? Color.BLACK : (bmi <= 17.5 || bmi >= 25) ? Color.RED: Color.GREEN;
    }

    private void loadData() {
        try {
            height = Float.parseFloat(this.et_height.getText().toString());
            weight = Float.parseFloat(this.et_weight.getText().toString());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Wrong data input!");
        }
    }

    private void clearEtWeightAndHeight(){
        et_height.setText(EMPTY_STRING);
        et_weight.setText(EMPTY_STRING);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tv_bmiResult.setText(savedInstanceState.getString("bmi_text"));
        tv_bmiResult.setTextColor(savedInstanceState.getInt("bmi_color"));
        tv_heightMeasurement.setText(savedInstanceState.getString("height_measure"));
        tv_weightMeasurement.setText(savedInstanceState.getString("weight_measure"));

    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bmi_text",tv_bmiResult.getText().toString());
        outState.putInt("bmi_color",tv_bmiResult.getCurrentTextColor());
        outState.putString("height_measure",tv_heightMeasurement.getText().toString());
        outState.putString("weight_measure",tv_weightMeasurement.getText().toString());
    }

    @Override
    protected void onStop(){
        super.onStop();
        SetSharedPreferences();
    }

    @OnClick(R.id.shareBMI)
    public void SharingBMI(){
        String toExport = "My BMI is "+tv_bmiResult.getText().toString()+". I'm quite tall and slim.";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, toExport);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @OnClick(R.id.aboutAuthor)
    public void GetToTheAuthor(View view){
        Intent intent = new Intent(this, Author.class);
        startActivity(intent);
    }

    private void SetSharedPreferences(){
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("height", et_height.getText().toString());
        editor.putString("weight", et_weight.getText().toString());
        boolean isEU = b_euSystem.getId() == rg_group.getCheckedRadioButtonId();
        editor.putBoolean("isEU" , isEU);
        editor.commit();
    }

    private void GetSharedPreferences(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String height = pref.getString("height", "");
        String weight = pref.getString("weight", "");
        boolean isEU = pref.getBoolean("isEU",true);
        et_weight.setText(weight);
        et_height.setText(height);
        if(isEU) {
            rg_group.check(b_euSystem.getId());
        }
        else {
            rg_group.check(b_usSystem.getId());
        }
    }
}

