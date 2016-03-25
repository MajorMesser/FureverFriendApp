package com.mycompany.fureverfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText edtLocation;
    EditText edtBreed;
    Spinner spnType;

    LinearLayout linGenderLayout;
    LinearLayout linAgeLayout;

    RadioGroup rdgGender;
    RadioGroup rdgAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnType = (Spinner) findViewById(R.id.spnType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pet_types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapter);
    }

    public void passRequest(View view) {

        edtLocation = (EditText) findViewById(R.id.edtLocation);
        spnType = (Spinner) findViewById(R.id.spnType);
        rdgAge = (RadioGroup) findViewById(R.id.rdgAge);
        rdgGender = (RadioGroup) findViewById(R.id.rdgGender);
        spnType = (Spinner) findViewById(R.id.spnType);
        edtBreed = (EditText) findViewById(R.id.edtBreed);

        Intent i = new Intent(this, PetList.class);
        i.putExtra("location", edtLocation.getText().toString());
        i.putExtra("type", spnType.getSelectedItemId());
        i.putExtra("age", rdgAge.getCheckedRadioButtonId());
        i.putExtra("gender", rdgGender.getCheckedRadioButtonId());
        i.putExtra("type", spnType.getSelectedItemId());
        i.putExtra("type", edtBreed.getText().toString());

        startActivity(i);
    }
}
