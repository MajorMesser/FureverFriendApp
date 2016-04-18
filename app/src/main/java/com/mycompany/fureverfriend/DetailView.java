package com.mycompany.fureverfriend;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DetailView extends AppCompatActivity {

    private String api_key = "c2afcd54cfa21c24c13d6f7e57c64e69";
    private String query;

    private TextView txtName;
    private TextView txtBreed;
    private TextView txtLocation;
    private TextView txtGender;
    private TextView txtAge;
    private TextView txtDescription;

    private Button btnSave;

    String id = "";
    String name = "";
    String breed = "";
    String type = "";
    String gender = "";
    String location = "";
    String age = "";
    String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Intent i = getIntent();

        String query = "http://api.petfinder.com/pet.get?key=" + api_key + "&id=" + i.getStringExtra("id") + "&output=full";

        new RetrievePetList(query).execute();
    }

    public void savePet(View view) {
        PetHandler handler = new PetHandler(this, null, null, 1);

        String number = id;

        Pet pet = new Pet(number);

        handler.addPet(pet);
    }

    class RetrievePetList extends AsyncTask<Void, Void, Void> {

        String query;

        public RetrievePetList(String query) {
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DetailView.this, "Searching for pets...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            txtName = (TextView) findViewById(R.id.txtName);
            txtBreed = (TextView) findViewById(R.id.txtBreed);
            txtLocation = (TextView) findViewById(R.id.txtLocation);
            txtGender = (TextView) findViewById(R.id.txtGender);
            txtAge = (TextView) findViewById(R.id.txtAge);
            txtDescription = (TextView) findViewById(R.id.txtDescription);

            txtName.setText(name);
            txtBreed.setText(breed);
            txtLocation.setText(location);
            txtGender.setText(gender);
            txtAge.setText(age);
            txtDescription.setText(description);

            Toast.makeText(DetailView.this, "Complete!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL(this.query);
                InputStream in = url.openConnection().getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, "UTF_8");

                int eventType = parser.getEventType();
                boolean inPet = false;

                id = "";
                name = "";
                breed = "";
                type = "";
                gender = "";
                location = "";
                age = "";

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equalsIgnoreCase("pet")) {
                            inPet = true;
                        } else if (inPet && parser.getName().equalsIgnoreCase("id")) {
                            id = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("name")) {
                            name = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("breed")) {
                            breed = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("animal")) {
                            type = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("sex")) {
                            gender = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("location")) {
                            location = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("age")) {
                            age = parser.nextText();
                        } else if (inPet && parser.getName().equalsIgnoreCase("description")) {
                            description = parser.nextText();
                        }
                    } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("pet")) {
                        inPet = false;
                    }
                    eventType = parser.next();
                }

            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}