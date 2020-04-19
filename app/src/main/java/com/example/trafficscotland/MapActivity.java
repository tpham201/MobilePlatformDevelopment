
//Pham Thi Hue
//S1839331

package com.example.trafficscotland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficscotland.DirectionsHelper.FetchURL;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.trafficscotland.DirectionsHelper.FetchURL;
import com.example.trafficscotland.DirectionsHelper.TaskLoadedCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView titleView;
    private TextView descriptionView;
    private TextView geopointView;
    private TextView dateView;
    private TextView durationView;
    private Double latitude;
    private Double langtitude;
//    private GoogleMap mMap;
//    private MarkerOptions place1, place2;
//    private Polyline currentPolyline;
//    List<MarkerOptions> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent myintent= getIntent();
        String title = myintent.getStringExtra("title");
        String description = myintent.getStringExtra("description");
        String geopoint = myintent.getStringExtra("geopoint");
        String seperatedString[] = geopoint.split(" ");
        latitude = Double.parseDouble(seperatedString[0]);
        langtitude = Double.parseDouble(seperatedString[1]);
        String date = myintent.getStringExtra("date");
        String time = myintent.getStringExtra("duration");

        int duration = (Integer) Integer.parseInt(time)+1;

        titleView = (TextView) findViewById(R.id.title);
        descriptionView = (TextView) findViewById(R.id.description);
        durationView = (TextView) findViewById(R.id.duration);
        dateView = (TextView) findViewById(R.id.date);
        titleView.setText(title);
        descriptionView.setText(description);
        durationView.setText("Duration "+ duration +" day(s)");
        dateView.setText(date);

        if(duration <= 10)
        {
            durationView.setTextColor(Color.WHITE);
            //mainView.setBackgroundColor(getResources().getColor(R.color.silver,null));

            durationView.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if (duration >10 && duration <=50)
        {
            durationView.setTextColor(Color.BLACK);
            durationView.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        else if (duration >50)
        {
            durationView.setTextColor(Color.WHITE);
            durationView.setBackgroundColor(getResources().getColor(R.color.red));
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng marker = new LatLng(latitude, langtitude);
        map.addMarker(new MarkerOptions().position(marker).title(titleView.getText().toString()));
        map.moveCamera(CameraUpdateFactory.newLatLng(marker));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, langtitude), 8.0f));

            }
   }

