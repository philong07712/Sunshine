package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    String myUrl = "http://api.openweathermap.org/data/2.5/forecast?id=1580240&units=metric&cnt=1&appid=40ff03be4a925ef6e8a0f68a7c3a37f3";
    CustomAdapter customAdapter;
    ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();
    private static final String TAG = "MainActivity Logcat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();
        initView();
        getData();
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

    public void getData()
    {
        String result;
        RetrieveFeedTask getRequest = new RetrieveFeedTask();
        try {
            result = getRequest.execute(myUrl).get();
            Log.d(TAG, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
