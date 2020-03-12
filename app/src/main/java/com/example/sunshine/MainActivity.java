package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    String myUrl = "http://api.openweathermap.org/data/2.5/forecast?id=1580240&units=metric&cnt=1&appid=40ff03be4a925ef6e8a0f68a7c3a37f3";
    CustomAdapter customAdapter;
    ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();
    // all data
    private JSONObject mainObject;
    private static final String TAG = "MainActivity Logcat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        createData();
        initView();
        setDataToBoard();
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
        String data;
        RetrieveFeedTask getRequest = new RetrieveFeedTask();
        try {
            data = getRequest.execute(myUrl).get();
            mainObject = new JSONObject(data);
            Log.d(TAG, data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int getResId(String resName, Class<?> C)
    {
        try
        {
            Field idField = C.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    private String convertTemp(String temp)
    {
        double doubleResult = Double.parseDouble(temp);
        int intResult = (int) doubleResult;
        String result = Integer.toString(intResult);
        result += "\u00B0";
        return result;
    }

    private void setDataToBoard()
    {
        ImageView iconBoard = (ImageView) findViewById(R.id.imageView_td_icon);
        TextView tv_td_kind = (TextView) findViewById(R.id.textView_td_kind);
        TextView tv_td_max = (TextView) findViewById(R.id.textView_td_max);
        TextView tv_td_low = (TextView) findViewById(R.id.textView_td_low);
        TextView tv_td = (TextView) findViewById(R.id.textView_td);
        try {
            JSONArray listData = mainObject.getJSONArray("list");
            JSONObject td_data = listData.getJSONObject(0);
            JSONObject weatherObject = td_data.getJSONArray("weather").getJSONObject(0);
            // Set to the image of the board
            String icon = "ic_";
            icon += weatherObject.getString("icon");
            int image = getResId(icon, R.drawable.class);
            iconBoard.setImageResource(image);
            // Set to the kind of weather of board
            String kindWeather = weatherObject.getString("main");
            tv_td_kind.setText(kindWeather);
            // Set temp to this day on board
            String temp_max = td_data.getJSONObject("main").getString("temp_max");
            temp_max = convertTemp(temp_max);
            String temp_min = td_data.getJSONObject("main").getString("temp_min");
            temp_min = convertTemp(temp_min);
            tv_td_max.setText(temp_max);
            tv_td_low.setText(temp_min);

            // Set the textView td
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentDay = calendar.get(Calendar.DATE);
            String td_text = "Today, ";
            td_text += Integer.toString(currentDay) + "/" + Integer.toString(currentMonth);
            tv_td.setText(td_text);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
