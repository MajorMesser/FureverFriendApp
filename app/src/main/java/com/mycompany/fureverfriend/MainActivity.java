package com.mycompany.fureverfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText edtLocation;
    EditText edtBreed;


    LinearLayout linGenderLayout;
    LinearLayout linAgeLayout;

    RadioGroup rdgGender;
    RadioGroup rdgAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
