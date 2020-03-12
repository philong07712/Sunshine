package com.example.sunshine;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveFeedTask extends AsyncTask<String, Void, String>
{
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        String dataLine;
        try
        {
            URL myUrl = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();

//            connection.setConnectTimeout(CONNECTION_TIMEOUT);
//            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(REQUEST_METHOD);

            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while ((dataLine = bufferedReader.readLine()) != null)
            {
                builder.append(dataLine);
            }
            bufferedReader.close();;
            result = builder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}