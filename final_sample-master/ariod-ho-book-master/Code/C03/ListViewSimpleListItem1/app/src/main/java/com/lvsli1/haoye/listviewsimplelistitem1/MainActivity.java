package com.lvsli1.haoye.listviewsimplelistitem1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    private SimpleAdapter listItemAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<HashMap<String, Object>> listData=new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<15; i++){
            HashMap<String,Object> myHasMap=new HashMap<String,Object>();
            myHasMap.put("NAME","Name "+i);
            myHasMap.put("ID","ID"+i);
            myHasMap.put("AREA","Area "+i);
            myHasMap.put("AREA1","Area "+i);
            myHasMap.put("AREA2","Area "+i);
            myHasMap.put("AREA3","Area "+i);
            listData.add(myHasMap);
        }

        listItemAdapter = new SimpleAdapter( this,
                listData,// Data Source
                android.R.layout.simple_list_item_1,// ListItem
                new String[] { "NAME", "ID", "AREA"},
                new int[] { android.R.id.text1}
        );
        listView = findViewById(R.id.item_route);
        listView.setAdapter(listItemAdapter);
    }
}
