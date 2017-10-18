package com.shevroman.android.myschedule;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.shevroman.android.myschedule.ui.ChooseGroupActivity;
import com.shevroman.android.myschedule.ui.GroupScheduleActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Рома on 10/15/2017.
 */

public class ScheduleAsyncTask extends AsyncTask<URL, Integer, String> {

    private String csvResponse = "";
    private static final String SCHEDULE_URL =
            "https://docs.google.com/spreadsheets/d/e/2PACX-1vRYTu3kPdUePi3A2PiTYpHf64a" +
                    "9gQynzN522BYUsLPIMnUtvqVpxJHs2bc3hCVNalLCQu9G3SVHpgK7/pub?output=csv";

    @Override
    protected String doInBackground(URL... params) {
        String LOG_TAG = getClass().getSimpleName();
        URL url = createUrl(SCHEDULE_URL);
        try {
            csvResponse = makeHttpRequest(url);
            ChooseGroupActivity.csvR = csvResponse;
            GroupScheduleActivity.csvR = csvResponse;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with making HTTP Request", e);
        }


        return csvResponse;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Activity app = new Activity();
        super.onProgressUpdate(values);
        int state = Integer.valueOf(values.toString());
        while (state<=100){
            Toast.makeText(app.getBaseContext(),"Downloading fresh version of schedule",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) {
        String LOG_TAG = GroupScheduleActivity.class.getSimpleName();
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given SCHEDULE_URL and return a String as the response.
     */
    private static String makeHttpRequest(java.net.URL url) throws IOException {
        String LOG_TAG = GroupScheduleActivity.class.getSimpleName();
        String csvResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                inputStream = urlConnection.getInputStream();
                csvResponse = readFromStream(inputStream);
            } else Log.v(LOG_TAG, "Response code: " + statusCode);
        } catch (IOException e) {
            Log.v(LOG_TAG, "CSV response couldn't receive");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return csvResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line).append("\n");
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
