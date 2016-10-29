package com.example.twmoore.localrestaurants;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private String PlacesAPIKey = "AIzaSyCTgrdsST-RspbNj3TZf78bG68rfqrDiDM";
    private long[] latitudeLongitude;

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

    protected void checkPermissions() {

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
    }

    protected String[] nearbyPlace(){
        String url = "//maps.googleapis.com/maps/api/place/nearbysearch/json?";
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

    }
}
