package com.example.twmoore.localrestaurants;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity,implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private String PlacesAPIKey = "AIzaSyCTgrdsST-RspbNj3TZf78bG68rfqrDiDM";
    private double[] latitudeLongitude;

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
    }

    protected void onStart(){

        mGoogleApiClient.connect();             //connects to Google Play services
        super.onStart();
    }

    protected void onPause(){

    }

    protected void onDestroy(){

    }

    protected void onResume(){

    }

    protected void onStop(){

        mGoogleApiClient.disconnect();          //disconnects from Google Play services
        super.onStop();

    }

    protected void onRestart(){

    }

    protected long[] getLocation(){
        //return lat and long
        return null;
    }

    protected String[] getNearbyPlace(){
        StringBuilder url = new StringBuilder("//maps.googleapis.com/maps/api/place/nearbysearch/json?");
        //parameters
        int locationRadius = 40500;
        long latLongitude;
        String open = "opennow";
        String rankby = "prominence";

        //Build URL
        url.append("&" + open);
        url.append("&radius=" + locationRadius);
        url.append("&rankby=" + rankby);
        url.append("&key=" + PlacesAPIKey);

        return null;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        //need to add a checkPermission method

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Show an explanation to the user. After the user sees the explanation, try again to request permission
            } else {
                //need to define an int for this later on for mypermissions request
               /*
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        */
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
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
