package com.example.locationfinder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.edge.Edge;
import com.adobe.marketing.mobile.edge.ExperienceEvent;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_CODE = 100;

    private TextView txtLat;
    private TextView txtLong;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MobileCore.setLogLevel(LoggingMode.DEBUG);
        MobileCore.initialize(this, "072780f565a4/a3f02c9a77ab/launch-f366749920de-development");

        txtLat = findViewById(R.id.txtLat);
        txtLong = findViewById(R.id.txtLong);

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        askForLocationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenViewEvent("MainScreen");
    }

    private void sendScreenViewEvent(String screenName) {
        Map<String, Object> xdmData = new HashMap<>();
        xdmData.put("eventType", "web.webpagedetails.pageViews");

        Map<String, Object> webPageDetails = new HashMap<>();
        webPageDetails.put("name", screenName);
        webPageDetails.put("pageViews", new HashMap<String, Object>() {{ put("value", 1); }});

        Map<String, Object> web = new HashMap<>();
        web.put("webPageDetails", webPageDetails);
        xdmData.put("web", web);

        ExperienceEvent event = new ExperienceEvent.Builder()
                .setXdmSchema(xdmData)
                .build();

        Edge.sendEvent(event, handles -> {
            // optional: inspect response handles
        });
    }

    private void askForLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fetchLocation();

        } else {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    LOCATION_PERMISSION_CODE);
        }
    }

    private void fetchLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {

                        txtLat.setText(
                                "Latitude: " +
                                        location.getLatitude());

                        txtLong.setText(
                                "Longitude: " +
                                        location.getLongitude());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {

            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {

                fetchLocation();

            } else {

                txtLat.setText("No Permissions");
                txtLong.setText("");
            }
        }
    }
}