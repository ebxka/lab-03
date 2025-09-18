package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    private int selectedPosition = -1;

    public void addCity(City city){
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    public void editCity(City city, String name, String province){
        city.setName(name);
        city.setProvince(province);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };


        dataList = new ArrayList<City>();
        for(int i = 0; i < cities.length; i++){
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });


        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            selectedPosition = -1;
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        FloatingActionButton fab_edit = findViewById(R.id.button_edit_city);
        fab_edit.setOnClickListener(v -> {
            if(selectedPosition != -1) {
                new AddCityFragment(dataList.get(selectedPosition)).show(getSupportFragmentManager(), "Edit City");
                selectedPosition = -1;
            }else{
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Please first select a city to edit")
                        .setPositiveButton("OK", null)
                        .show();
            }

        });

    }
}