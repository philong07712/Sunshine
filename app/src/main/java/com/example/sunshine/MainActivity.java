package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    CustomAdapter customAdapter;
    ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();
        initView();
//        ArrayList<String> arrayList = new ArrayList<String>();
//        arrayList.add("Tomorrow");
//        arrayList.add("Sunday");
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(arrayAdapter);

        // Test setting custom adapter
    }

    public void initView()
    {
        listView = (ListView) findViewById(R.id.listView_day);
        customAdapter = new CustomAdapter(MainActivity.this, dataModelArrayList);
        listView.setAdapter(customAdapter);
        Log.d(TAG, "Done init");

    }
    public void createData()
    {
        for (int i = 0; i < 2; i++)
        {
            DataModel data = new DataModel(R.drawable.sun, "Tomorrow", "Sunny",
                    13, 14);
            dataModelArrayList.add(data);
        }
        Log.d(TAG, "done create");
    }
}
