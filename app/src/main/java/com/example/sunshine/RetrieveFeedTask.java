package com.example.sunshine;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
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
        String stringUrl = strings[0];
        String inputLine;
        String result = "";
        try
        {
            URL myUrl = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            // Set method and timeout
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null)
            {
                builder.append(inputLine);
            }
            bufferedReader.close();
            result = builder.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}