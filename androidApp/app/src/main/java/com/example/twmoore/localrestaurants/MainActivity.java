package com.example.twmoore.localrestaurants;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
}
