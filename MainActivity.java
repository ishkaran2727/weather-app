package com.example.clima20;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class    MainActivity extends AppCompatActivity {

    public static final String CITY_NAME = "com.example.clima20.CITY_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    protected void getWeather(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String cityname = editText.getText().toString();
        Intent intent = new Intent(MainActivity.this,Activity2.class);
        intent.putExtra(CITY_NAME,cityname);
        startActivity(intent);
    }



}


