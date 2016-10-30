package com.example.twmoore.localrestaurants;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private String PlacesAPIKey = "AIzaSyCTgrdsST-RspbNj3TZf78bG68rfqrDiDM";
    private double[] latitudeLongitude;
    /*
    private ListView listView; 
    private ArrayAdapter<String> adapter; 
    private String[] list = {
            "Test1",
            "Test2",
            "Test1",
            "Test2",
            "Test1",
            "Test2",
            "Test1",
            "Test2",
            "Test1",
            "Test2",
            "Test1",
            "Test2",
            "Test1",
            "Test2"};
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {         //adds the Google Play API
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
/*
        listView = (ListView) findViewById(R.id.view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list); 
        listView.setAdapter(adapter);

*/
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();             //connects to Google Play services
        //getNearbyPlace();

    }

    protected void onPause() {

    }

    protected void onDestroy() {

    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {

        mGoogleApiClient.disconnect();          //disconnects from Google Play services
        super.onStop();

    }

    protected void onRestart() {

    }

    protected double[] getLocation() {
        return latitudeLongitude;
    }

    protected double getLatitude() {
        return latitudeLongitude[0];
    }

    protected double getLongitude() {
        return latitudeLongitude[1];
    }

    protected Object getNearbyPlace() {
        StringBuilder url = new StringBuilder("//maps.googleapis.com/maps/api/place/nearbysearch/json?");
        Object output;

        //parameters
        int locationRadius = 40500;
        double[] latLongitude = getLocation();
        String open = "opennow";
        String rankby = "prominence";

        //Build URL
        url.append("&" + open);
        url.append("&radius=" + locationRadius);
        url.append("&rankby=" + rankby);
        url.append("&key=" + PlacesAPIKey);
        url.append("&location=" + getLatitude() + "," + getLongitude());
        String urlString = url.toString();

        //connection
        output = null;
        try {
            URL connection = new URL(urlString);
            output = connection.getContent();
        } catch (MalformedURLException e) {
            System.out.println("URL failure");
        } catch (IOException e) {
            System.out.println("Input Output Error");
        }


        return output;
    }


        @Override
        public void onConnected (Bundle connectionHint){
            System.out.println("connection success");
            //need to add a checkPermission method

            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    ACCESS_FINE_LOCATION);
                int status = PackageManager.PERMISSION_GRANTED;
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    //Show an explanation to the user. After the user sees the explanation, try again to request permission
                } else {
                    //need to define an int for this later on for mypermissions request

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                }
            } else {
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                //need to define an xmlString for that I think
                if (mLastLocation != null) {
                    latitudeLongitude[0] = mLastLocation.getLatitude();
                    latitudeLongitude[1] = mLastLocation.getLongitude();
                }
            }
        }
        @Override
        public void onConnectionSuspended ( int i){

        }

        @Override
        public void onConnectionFailed (ConnectionResult connectionResult){
            System.out.println("connection failed");
        }
    }