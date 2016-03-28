package com.mycompany.fureverfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class PetList extends AppCompatActivity {

    private String auth_key;
    private String api_key = "c2afcd54cfa21c24c13d6f7e57c64e69";
    private String api_secret = "991e256c1465e3c97dc2d624c6d98b85";

    private ArrayList<Pet> pet_list = new ArrayList<>();

    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        String query = buildQuery();

        new RetrievePetList(query).execute();
    }

    private String buildQuery() {

        Intent i = getIntent();

        String location = i.getStringExtra("location");
        String breed = i.getStringExtra("breed");
        String age = i.getStringExtra("age");
        String gender = i.getStringExtra("gender");
        String type = i.getStringExtra("type");

        switch (type) {
            case "Dog":
                type = "dog";
                break;
            case "Cat":
                type = "cat";
                break;
            case "Horse":
                type = "horse";
                break;
            case "Small & Furry":
                type = "smallfurry";
                break;
            case "Bird":
                type = "bird";
                break;
            case "Barnyard":
                type = "barnyard";
                break;
            case "Rabbit":
                type = "rabbit";
                break;
            case "Pig":
                type = "pig";
                break;
            case "Scales, Fins & Other":
                type = "reptile";
        }

        String query = "http://api.petfinder.com/pet.find?key=" + api_key + "&location=" + location + "&sex=" + gender + "&type=" + type + "&output=basic";


        return query;
    }

    class Pet {
        private String id;
        private String name;
        private String breed;
        private String type;
        private String gender;
        private String location;
        private String age;

        public Pet(String id, String name, String breed, String type, String gender, String location, String age) {
            this.id = id;
            this.name = name;
            this.breed = breed;
            this.type = type;
            this.gender = gender;
            this.location = location;
            this.age = age;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBreed() {
            return breed;
        }

        public String getType() {
            return type;
        }

        public String getGender() {
            return gender;
        }

        public String getLocation() {
            return location;
        }

        public String getAge() {
            return age;
        }
    }

    class RetrievePetList extends AsyncTask<Void, Void, Void> {

        String query;

        public RetrievePetList(String query) {
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pet_list.clear();
            Toast.makeText(PetList.this, "Searching for pets...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            PetAdapter p = new PetAdapter(PetList.this, R.layout.list_item, pet_list);
            lstView = (ListView) findViewById(R.id.lstView);
            assert lstView != null;
            lstView.setAdapter(p);

            Toast.makeText(PetList.this, "Complete!", Toast.LENGTH_SHORT).show();
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

                String id = "";
                String name = "";
                String breed = "";
                String type = "";
                String gender = "";
                String location = "";
                String age = "";

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
                        }
                    } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("pet")) {
                        inPet = false;
                        Pet pet = new Pet(id, name, breed, type, gender, location, age);
                        pet_list.add(pet);
                    }
                    eventType = parser.next();
                }

            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class PetAdapter extends ArrayAdapter<Pet> {

        private ArrayList<Pet> pets;

        public PetAdapter(Context context, int resource, ArrayList<Pet> pets) {
            super(context, resource, pets);
            this.pets = pets;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }

            Pet l = pet_list.get(position);

            if (l != null) {
                TextView tt = (TextView) v.findViewById(R.id.toptext);
                TextView bt = (TextView) v.findViewById(R.id.bottomtext);

                if (tt != null) {
                    tt.setText(l.getName());
                    tt.setTextColor(Color.WHITE);
                }

                if (bt != null) {
                    bt.setText(l.getId());
                    bt.setTextColor(Color.WHITE);
                }
            }

            return v;
        }
    }
}
