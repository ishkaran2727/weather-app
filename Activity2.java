package com.example.clima20;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Activity2 extends AppCompatActivity {
    public static final string LAT = "com.example.clima20.LAT";
    public static final string LON = "com.example.clima20.LON";
    public static final String CITY_NAME1 = "com.example.clima20.CITY_NAME1";
    EditText editText;
    TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Intent intent = getIntent();
        String cityname = intent.getStringExtra(MainActivity.CITY_NAME);
        TextView textView2 = (TextView) findViewById(R.id.resultTextView);

        textView2.setText(cityname);
        resultTextView = findViewById(R.id.resultTextView);

        try {
            DownloadTask task = new DownloadTask();
            String encodedCityName = URLEncoder.encode(cityname, "UTF-8");
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&APPID=1175debd0bee439d7e53537f3b75a687");

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
           // mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

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

            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();

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

                //the first part is array . see api sample in begining for reference

                for (int i=0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if (!main.equals("") && !description.equals("")) {
                        message += main + ": " + description + "\r\n";
                    }
                }


                //either parser could be used , or we can just extract json Object
                // JSONObject jsonPart1 = arr1.getJSONObject(i);
                // JSONParser parser = new JSONParser();


                JSONObject jsonPart1 = new JSONObject(tempInfo);

                String temp = jsonPart1.getString("temp");
                String humidity = jsonPart1.getString("humidity");
                Double temp_1 = Double.parseDouble(temp);
                Double temp_deg= Double.parseDouble(temp);
                temp_deg= temp_1-273.15;
                Double temp_far= Double.parseDouble(temp);
                temp_far= (1.8*(temp_1-273.15))+32;

                if (!temp.equals("") && !humidity.equals("")) {
                    message += "Temperature in Degrees Celsius : " + temp_deg +"°C\r\n" + " Temperature in Fahrenheit : " + temp_far + "°F\r\n" + "Humidity :"+humidity +"%"+"\r\n";
                }


                JSONObject jsonPart2 = new JSONObject(windInfo);

                String speed = jsonPart2.getString("speed");

                if (!speed.equals("") ) {
                    message += "Wind Speed : " + speed +" m/s"+ "\r\n";
                }


                if (!message.equals("")) {
                    resultTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    protected void gotomaps(View view){
        //EditText editText1 = (EditText) findViewById(R.id.editText1);
        //String cityname_1 = editText1.getText().toString();
        JSONObject jsonObject1 = new JSONObject(s);
        String coordinates = jsonObject1.getString("coord");
        Log.i("coordinate content", coordinates);

        JSONArray arr3 = new JSONArray(coordinates);
        for (int i=0; i < arr3.length(); i++) {
            JSONObject jsonPart = arr3.getJSONObject(i);

            string lon = jsonPart.getString("lon");
            string lat = jsonPart.getString("lat");

        }
        //Double temp_lon= Double.parseDouble(lon);
        //Double temp_lat= Double.parseDouble(lat);


        Intent intent1 = new Intent(Activity2.this,MapsActivity2.class);
        intent1.putExtra(LAT,lat);
        intent1.putExtra(LON,lon);
        intent1.putExtra(CITY_NAME1,(intent.getStringExtra(MainActivity.CITY_NAME))
        startActivity(intent1);
    }
}

