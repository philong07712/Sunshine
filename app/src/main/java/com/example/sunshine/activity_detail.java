package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class activity_detail extends AppCompatActivity {
    private final static String TAG = "Detail_Logcat";
    // Value
    private JSONObject jsonObject = null;
    private int image_id;
    private String day;
    private String kindOfWeather;
    private int maxTemp;
    private int lowTemp;
    private String humid;
    private String ps;
    private String wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView img_icon = (ImageView) findViewById(R.id.imageView_detail_icon);
        TextView tv_day = (TextView) findViewById(R.id.textView_detail_head);
        TextView tv_kind = (TextView) findViewById(R.id.textView_detail_kind);
        TextView tv_max = (TextView) findViewById(R.id.textView_detail_max);
        TextView tv_low = (TextView) findViewById(R.id.textView_detail_low);
        TextView tv_humid = (TextView) findViewById(R.id.textView_detail_hm_val);
        TextView tv_ps = (TextView) findViewById(R.id.textView_detail_ps_val);
        TextView tv_wind = (TextView) findViewById(R.id.textView_detail_wind_val);
        Intent intent = getIntent();
        // get Data from intent
        try {
            jsonObject = new JSONObject(intent.getStringExtra("data"));
            image_id = intent.getExtras().getInt("icon");
            day = intent.getExtras().getString("day");
            kindOfWeather = intent.getExtras().getString("kind");
            maxTemp = intent.getExtras().getInt("MaxTemp", 0);
            lowTemp = intent.getExtras().getInt("LowTemp", 0);
            humid = jsonObject.getJSONObject("main").getString("humidity") + " %";
            ps = jsonObject.getJSONObject("main").getString("pressure") + " hPa";
            wind = jsonObject.getJSONObject("wind").getString("speed") + " km/h E";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Set data to the View
        String temp = Integer.toString(maxTemp) + "\u00B0";
        tv_max.setText(temp);
        temp = Integer.toString(lowTemp) + "\u00B0";
        tv_low.setText(temp);
        img_icon.setImageResource(image_id);
        tv_kind.setText(kindOfWeather);
        tv_day.setText(day);
        // Set value for more information
        tv_humid.setText(humid);
        tv_ps.setText(ps);
        tv_wind.setText(wind);
    }
}
