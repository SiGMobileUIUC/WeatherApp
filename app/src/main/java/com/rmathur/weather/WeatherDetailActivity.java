package com.rmathur.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDetailActivity extends AppCompatActivity {

    // Meta
    private final String TAG = this.getClass().getSimpleName();

    // UI
    private TextView currTemp;
    private TextView currConditions;

    // Networking
    private RequestQueue queue;

    // API
    private static final String API_OWM_KEY = "bccb424ce1ae4677532682647bda387d";
    private static final String API_OWM_WEATHER = "http://api.openweathermap.org/data/2.5/weather?units=imperial&zip=%1$s,us&APPID=%2$s";
    private String zipcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        // link UI
        currTemp = (TextView) findViewById(R.id.temperature);
        currConditions = (TextView) findViewById(R.id.conditions);

        // get zip code
        Intent intent = getIntent();
        zipcode = intent.getStringExtra(getString(R.string.zip_key));

        // set up networking queue
        queue = Volley.newRequestQueue(this);

        // update weather for the first time
        updateWeather();
    }

    private void updateWeather() {
        String url = String.format(API_OWM_WEATHER, zipcode, API_OWM_KEY);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // get the main json object
                    JSONObject jsonResponse = new JSONObject(response);

                    // get the current temp from the json response
                    JSONObject mainResponse = jsonResponse.getJSONObject("main");
                    double weatherTemp = mainResponse.getDouble("temp");
                    currTemp.setText(Double.toString(weatherTemp));

                    // get the current conditions from the json response
                    JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                    JSONObject weatherResponse = weatherArray.getJSONObject(0);
                    String weatherConditions = weatherResponse.getString("main");
                    currConditions.setText(weatherConditions);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON parsing failed:");
                    Log.d(TAG, response);
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "Error while fetching request:");
                Log.e(TAG, error.getMessage());
            }
        });

        queue.add(request);
    }
}