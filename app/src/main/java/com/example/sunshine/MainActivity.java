package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
        String myUrl = "http://api.openweathermap.org/data/2.5/forecast?id=1580240&units=metric&cnt=40&appid=40ff03be4a925ef6e8a0f68a7c3a37f3";
    CustomAdapter customAdapter;
    ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();
    ArrayList<JSONObject> list_data = new ArrayList<JSONObject>();
    ArrayList<String> list_lowTemp = new ArrayList<String>();
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
        // Set onClick for listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), activity_detail.class);
                // get Data from dataModelist
                JSONObject item_data = dataModelArrayList.get(position).getData();
                intent.putExtra("data", item_data.toString());
                intent.putExtra("icon", dataModelArrayList.get(position).getImage());
                intent.putExtra("kind", dataModelArrayList.get(position).getKindOfWeather());
                intent.putExtra("day", dataModelArrayList.get(position).getDay());
                intent.putExtra("MaxTemp", dataModelArrayList.get(position).getMaxTemp());
                intent.putExtra("LowTemp", dataModelArrayList.get(position).getLowTemp());
                startActivity(intent);
            }
        });
    }

    public void initView()
    {
        listView = (ListView) findViewById(R.id.listView_day);
        customAdapter = new CustomAdapter(MainActivity.this, dataModelArrayList);
        listView.setAdapter(customAdapter);
    }
    public void createData()
    {
        try {
            JSONArray dataList = mainObject.getJSONArray("list");
            // get All data from api
            for (int i = 1; i < dataList.length(); i++)
            {
                String time = dataList.getJSONObject(i).getString("dt_txt");
                if (time.contains("09:00:00"))
                {
                    list_data.add(dataList.getJSONObject(i));
                }
                if (time.contains("00:00:00"))
                {
                    list_lowTemp.add(dataList.getJSONObject(i).getJSONObject("main").getString("temp_min"));
                }
            }
            Log.d(TAG, Integer.toString(list_lowTemp.size()));
            // set data to the list
            for (int i = 0; i < list_data.size(); i++)
            {
                // init variable
                int resIdIcon;
                String day;
                String weather_kind;
                int maxTemp;
                int lowTemp;
                // get current day
                Calendar calendar = Calendar.getInstance();
                int today = calendar.get(Calendar.DAY_OF_WEEK);
                day = getDayOfWeek(today - 2 + i + 1);
                // init data for icon
                String nameIcon = "ic_";
                JSONObject weather = list_data.get(i).getJSONArray("weather").getJSONObject(0);
                nameIcon += weather.getString("icon");
                resIdIcon = getResId(nameIcon, R.drawable.class);
                // init data for weather kind
                weather_kind = weather.getString("description");
                // init data for max temp
                String maxTempString = list_data.get(i).getJSONObject("main").getString("temp_max");
                String minTempString = list_lowTemp.get(i);
                maxTemp = Integer.parseInt(convertTemp(maxTempString));
                lowTemp = Integer.parseInt(convertTemp(minTempString));
                DataModel data = new DataModel(resIdIcon, day, weather_kind,
                        maxTemp, lowTemp, list_data.get(i));
                dataModelArrayList.add(data);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void getData()
    {
        String data;
        RetrieveFeedTask getRequest = new RetrieveFeedTask();
        try {
            data = getRequest.execute(myUrl).get();
            mainObject = new JSONObject(data);
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

    public static String getDayOfWeek(int i)
    {
        i %= 7;
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"));
        return list.get(i);
    }

    private String convertTemp(String temp)
    {
        double doubleResult = Double.parseDouble(temp) + 0.5;
        int intResult = (int) doubleResult;
        String result = Integer.toString(intResult);
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
            String kindWeather = weatherObject.getString("description");
            tv_td_kind.setText(kindWeather);
            // Set temp to this day on board
            String temp_max = td_data.getJSONObject("main").getString("temp_max");
            temp_max = convertTemp(temp_max) + "\u00B0";
            String temp_min = td_data.getJSONObject("main").getString("temp_min");
                temp_min = convertTemp(temp_min) + "\u00B0";
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
