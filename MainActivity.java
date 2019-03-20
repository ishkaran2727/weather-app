package com.example.clima20;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.lang.*;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity
{

    EditText editText;
    TextView resultTextView;
    ImageView image;
    public static final String CITY_NAME1 = "com.example.clima20.CITY_NAME1";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);
        image = findViewById(R.id.imageView);

    }

    public void getWeather(View view)
    {
        try
        {
            DownloadTask task = new DownloadTask();
            String encodedCityName = URLEncoder.encode(editText.getText().toString(), "UTF-8");
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&APPID=1175debd0bee439d7e53537f3b75a687");
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                String tempInfo = jsonObject.getString("main");
                String windInfo = jsonObject.getString("wind");
                Log.i("Weather content", weatherInfo);
                Log.i("Temperature content", tempInfo);
                Log.i("Wind content", windInfo);
                JSONArray arr = new JSONArray(weatherInfo);
                String message = "";
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    if (!main.equals("") && !description.equals("")) {
                        message = "Sky: " + description + "\r\n";
                    }
                    if(description.contains("cloud")) {
                        image.setImageResource(R.drawable.cloud);
                    }
                    else if(description.contains("rain")) {
                        image.setImageResource(R.drawable.rain);
                    }
                    else if(description.contains("clear")) {
                        image.setImageResource(R.drawable.clear);
                    }
                    else{
                        image.setImageResource(R.drawable.b);
                    }
                }
                JSONObject jsonPart1 = new JSONObject(tempInfo);
                String temp = jsonPart1.getString("temp");
                String humidity = jsonPart1.getString("humidity");
                Double temp1 = Double.parseDouble(temp);
                Double temp_deg = round((temp1 - 273.15)*100.00)/100.00;
                Double temp_far = round(((1.8 * (temp1 - 273.15)) + 32)*100.00)/100.00;
                if (!temp.equals("") && !humidity.equals("")) {
                    message += "Temperature \nCelsius : " + temp_deg + "°C\r\n" + "Fahrenheit : " + temp_far + "°F\r\n" + "Humidity : " + humidity + "%" + "\r\n";
                }
                JSONObject jsonPart2 = new JSONObject(windInfo);
                String speed = jsonPart2.getString("speed");
                if (!speed.equals("")) {
                    message += "Wind Speed : " + speed + " m/s" + "\r\n";
                }
                if (!message.equals("")) {
                    resultTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(), "Could not find weather :(", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                String message;
                message = "Place not found";
                resultTextView.setText(message);
            }
        }
    }
    protected void gotomaps(View view)
    {

        try{
        EditText edit = findViewById(R.id.editText);
        String result = edit.getText().toString();
        Intent intent1 = new Intent(MainActivity.this,Map.class);
        intent1.putExtra(CITY_NAME1, result);
        MainActivity.this.startActivity(intent1);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Map not available.", Toast.LENGTH_LONG).show();
        }

    }
}
