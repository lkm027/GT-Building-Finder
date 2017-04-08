package com.example.lucas.mapexample;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LocationPicker extends AppCompatActivity {

    private SharedPreferences preferences;
    private ArrayList<String> locations;
    private Spinner originSpinner;
    private Spinner destSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_location_picker);

        // Configure toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.locPick_toolBar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.buzzYellow));
        toolbar.setTitle(R.string.toolbar_title);

        preferences = getPreferences(MODE_PRIVATE);
        // If preferences exist then retrieve all of their key values, else add new shared preferences
        if (!preferences.contains("test")) {
            InputStream inputStream = getResources().openRawResource(R.raw.locations);
            PreferenceStorer storer = new PreferenceStorer(inputStream);
            storer.store(preferences);
            locations = storer.getList();
        } else {
            Map<String, ?> allEntries = preferences.getAll();
            locations = new ArrayList<>();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                locations.add(entry.getKey());
                Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            }
        }
        // Sort our list to place items in the correct order
        locations.subList(1, locations.size());
        Collections.sort(locations);

        // Create adapter
        originSpinner = (Spinner) findViewById(R.id.spinner_origin_loc);
        destSpinner = (Spinner) findViewById(R.id.spinner_dest_loc);
        ArrayAdapter<String> originAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                locations);
        ArrayAdapter<String> destAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                locations);

        // Specif the layout to use when the list of choices appears
        originAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply adapter to spinner
        originSpinner.setAdapter(originAdapter);
        destSpinner.setAdapter(destAdapter);

//        // Set toolbar title and text color
//        Toolbar appbar = (Toolbar) findViewById(R.id.locPic_toolBar);
//        appbar.setTitle(R.string.toolbar_title);
//        appbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

    /**
     * Submits the location and destination information the user selected to MapsActivity.class
     * and starts the MapsActivity class
     * @param view
     */
    public void submitLocations(View view) {
        Intent intent = new Intent(getApplicationContext() , MapsActivity.class);
        intent.putExtra("origin", originSpinner.getSelectedItem().toString());
        intent.putExtra("destination", destSpinner.getSelectedItem().toString());
        startActivity(intent);
    }


}
