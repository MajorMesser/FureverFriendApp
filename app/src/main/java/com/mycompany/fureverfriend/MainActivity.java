package com.mycompany.fureverfriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText edtLocation;
    EditText edtBreed;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    LinearLayout linGenderLayout;
    LinearLayout linAgeLayout;

    RadioGroup rdgGender;
    RadioGroup rdgAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("main", MODE_PRIVATE);
        edit = pref.edit();

        edtLocation = (EditText) findViewById(R.id.edtLocation);
        edtBreed = (EditText) findViewById(R.id.edtBreed);

        edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit.putString("location", edtLocation.getText().toString());
                edit.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtBreed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit.putString("breed", edtBreed.getText().toString());
                edit.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        pref = getSharedPreferences("main", MODE_PRIVATE);
        edtBreed = (EditText) findViewById(R.id.edtBreed);
        edtLocation = (EditText) findViewById(R.id.edtLocation);

        edtBreed.setText(pref.getString("breed", ""));
        edtLocation.setText(pref.getString("location", ""));
    }

    public void passRequest(View view) {

        edtLocation = (EditText) findViewById(R.id.edtLocation);
        rdgAge = (RadioGroup) findViewById(R.id.rdgAge);
        rdgGender = (RadioGroup) findViewById(R.id.rdgGender);
        edtBreed = (EditText) findViewById(R.id.edtBreed);

        String gender = "";
        String age = "";

        switch (rdgAge.getCheckedRadioButtonId()) {
            case R.id.radAdult:
                age = "Adult";
                break;
            case R.id.radBaby:
                age = "Baby";
                break;
            case R.id.radSenior:
                age = "Senior";
                break;
            case R.id.radYoung:
                age = "Young";
                break;
            case R.id.radAgeAny:
                age = "";
        }

        switch (rdgGender.getCheckedRadioButtonId()) {
            case R.id.radFemale:
                gender = "F";
                break;
            case R.id.radMale:
                gender = "M";
        }

        Intent i = new Intent(this, PetList.class);
        i.putExtra("location", edtLocation.getText().toString());
        i.putExtra("age", age);
        i.putExtra("gender", gender);
        i.putExtra("breed", edtBreed.getText().toString());

        startActivity(i);
    }
}
