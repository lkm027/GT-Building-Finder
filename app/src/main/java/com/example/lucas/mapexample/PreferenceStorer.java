package com.example.lucas.mapexample;

import android.content.SharedPreferences;
import android.support.v7.util.SortedList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lkm02 on 4/5/2017.
 */

public class PreferenceStorer {

    InputStream inputStream;
    ArrayList<String> list;

    public PreferenceStorer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void store(SharedPreferences preferences) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            list = new ArrayList<>();
            list.add("test");
            preferences.edit().putString("test", "test").commit();
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                preferences.edit().putString(row[0], row[1]).commit();
                list.add(row[0]);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading csv file");
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                throw new RuntimeException("Error closing csv file");
            }
        }
    }

    public ArrayList<String> getList() {
        return list;
    }
}
