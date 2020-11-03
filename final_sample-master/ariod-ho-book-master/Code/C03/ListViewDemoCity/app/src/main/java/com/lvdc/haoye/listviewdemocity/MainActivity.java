package com.lvdc.haoye.listviewdemocity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private String[] cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        cities = getResources().getStringArray(R.array.cities);

        ListView cityList;
        cityList = (ListView) findViewById(R.id.cityList);

        ArrayAdapter<String> a = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, cities);
        cityList.setAdapter(a);
        cityList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
