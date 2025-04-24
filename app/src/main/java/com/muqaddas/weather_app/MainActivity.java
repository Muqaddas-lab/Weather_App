package com.muqaddas.weather_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.*;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvCity, tvTemp, tvCondition;
    ImageView ivIcon;

    FusedLocationProviderClient fusedLocationClient;
    final int LOCATION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Bindings
        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);
        tvCondition = findViewById(R.id.tvCondition);
        ivIcon = findViewById(R.id.ivIcon);

        // Location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Check permission
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    fetchWeatherData(lat, lon);
                } else {
                    Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Location error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } catch (SecurityException e) {
            Toast.makeText(this, "Permission not granted: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Always call this

        if (requestCode == LOCATION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location fetch
                getCurrentLocation();
            } else {
                // Permission denied, show message
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchWeatherData(double lat, double lon) {
        String apiKey = "78cbcb3d35858aaaf82cd9dc41ee038d";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String cityName = response.getString("name");
                        JSONObject main = response.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                        String description = weather.getString("description");
                        String icon = weather.getString("icon");
/*
                        // Show in UI
                        tvCity.setText("City: " + cityName);
                        tvTemp.setText("Temp: " + temp + "°C");
                        tvCondition.setText("Condition: " + description);*/

                        tvCity.setText(getString(R.string.label_city, cityName));
                        tvTemp.setText(getString(R.string.label_temp, temp));
                        tvCondition.setText(getString(R.string.label_condition, description));


                        // Weather icon
                        String iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                        Glide.with(this).load(iconUrl).into(ivIcon);

                    } catch (JSONException e) {
                        Toast.makeText(this, "JSON parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Failed to fetch weather data", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }
}
