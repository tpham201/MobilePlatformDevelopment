
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


//        place1 = new MarkerOptions().position(new LatLng(55.864239, -4.251806)).title("Your current location");
//        place2 = new MarkerOptions().position(new LatLng(latitude, langtitude)).title(titleView.getText().toString());
//        new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

//        list.add(place1);
//        list.add(place2);



        //new FetchURL(MapActivity.this).execute(getUrl(currentPosition.getPosition(), roadPosition.getPosition(), "driving"), "driving");
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
//        mMap = map;
//        mMap.addMarker(place1);
//        mMap.addMarker(place2);
//        showAllMarker();
        //LatLng current = new LatLng(55.8771639, -4.1506034000000005);
//        LatLng road = new LatLng(latitude, langtitude);
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        //builder.include(current);
//        builder.include(road);
//        int padding=50;
//        LatLngBounds bounds = builder.build();
//        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//            @Override
//            public void onMapLoaded() {
//                /**set animated zoom camera into map*/
//                map.animateCamera(cu);
//               // map.addMarker(new MarkerOptions().position(current).title("Your current location"));
//                map.addMarker(new MarkerOptions().position(road).title(titleView.getText().toString()));
//                PolylineOptions polyLineOptions = new PolylineOptions();
//                polyLineOptions.add(current, road);
//                polyLineOptions.width(5);
//                polyLineOptions.color(Color.BLUE);
//                map.addPolyline(polyLineOptions);
            }
       // });
   }
//   private void showAllMarker()
////   {
////       LatLngBounds.Builder builder = new LatLngBounds.Builder();
////       for (MarkerOptions m:list)
////       {
////           builder.include(m.getPosition());
////       }
////       LatLngBounds bounds = builder.build();
////       int width = getResources().getDisplayMetrics().widthPixels;
////       int height = getResources().getDisplayMetrics().heightPixels;
////       int padding = (int)(width*3);
////       CameraUpdate cu=CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding);
////       mMap.animateCamera(cu);
////
////
////
////   }
////    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
////        // Origin of route
////        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
////        // Destination of route
////        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
////        // Mode
////        String mode = "mode=" + directionMode;
////        // Building the parameters to the web service
////        String parameters = str_origin + "&" + str_dest + "&" + mode;
////        // Output format
////        String output = "json";
////        // Building the url to the web service
////        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyCP60KpPsNA4lQnUdU54QZEfh9chg6b7bc";
////        return url;
////    }
////
////
////    @Override
////    public void onTaskDone(Object... values) {
////        if (currentPolyline != null)
////            currentPolyline.remove();
////        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
////    }
//}
