package com.example.twmoore.localrestaurants;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private String PlacesAPIKey = "AIzaSyCTgrdsST-RspbNj3TZf78bG68rfqrDiDM";
    private Location mLastLocation;
    private String output;
    private ArrayList<Restaurant>  restArray = new ArrayList<>();
    private ListView listView;
    private RestuarantAdapter adapter;
    private double myLat;
    private double myLong;
    private double lat;
    private double longi;

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

    protected void onStart() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        mGoogleApiClient.connect();     //connects to Google Play services

        super.onStart();
        mGoogleApiClient.connect();

        listView = (ListView) findViewById(R.id.list_view);

        adapter = new RestuarantAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(adapter);

        getNearbyPlace();

        try {
            getRestaurantDataFromJSON(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("success");

        for(int i = 0; i < restArray.size(); i ++) {

            adapter.add(restArray.get(i));
        }//connects to Google Play services


    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        getNearbyPlace();

        try {
            getRestaurantDataFromJSON(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("success");
        // convertToArray();

    }

    protected void onStop() {

        mGoogleApiClient.disconnect();          //disconnects from Google Play services
        super.onStop();

    }

    protected void onRestart() {
        super.onRestart();

    }

    protected Location getLocation() {
        return mLastLocation;
    }

    protected double getLatitude() {
        if (getLocation() != null) {
            return getLocation().getLatitude();
        }
        return 0;
    }

    protected double getLongitude() {
        if (getLocation() != null) {
            return getLocation().getLongitude();
        }
        return 0;
    }

    protected Object getNearbyPlace() {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");


        //parameters
        int locationRadius = 10000;
        String open = "opennow";
        String rankby = "prominence";
        String type1 = "meal_takeaway";
        String type2 = "restaurant";
        String type3 = "food";

        //Build URL
        url.append("&" + open);
        url.append("&radius=" + locationRadius);
        url.append("&rankby=" + rankby);
        url.append("&key=" + PlacesAPIKey);
        // url.append("&location=" + getLatitude() + "," + getLongitude());
        url.append("&location=35.910180,-79.045277");
        url.append("&type=" + type1 );
        String urlString = url.toString();

        //connection
        output = null;
        StringBuilder content = new StringBuilder();
        BufferedReader buffer = null;
        try {
            URL connection = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) connection.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();



            //read input stream

            buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));


            String line;
            while ((line = buffer.readLine()) != null) {
                content.append(line + "/n");

            }
            buffer.close();




        } catch (MalformedURLException e) {
            System.out.println(urlString);
            System.out.println("URL failure");
        } catch (IOException e) {
            System.out.println("Input Output Error");
        }

        output = content.toString();


        return output;
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        System.out.println("connection success");
        //need to add a checkPermission method

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Show an explanation to the user. After the user sees the explanation, try again to request permission
            } else {
                //need to define an int for this later on for mypermissions request

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //need to define an xmlString for that I think

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("connection failed");
    }

    @TargetApi(19)
    protected void convertToArray() {

        try {
            JSONArray array = new JSONArray(output);
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                String abc = (String) array.get(i);
                arrayList.add(abc);

            }
            System.out.println(arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSON Exception");

        }
    }

    public void getRestaurantDataFromJSON(String restaurantJsonStr) throws JSONException {
        final String RESULTS= "results";
        final String NAME= "name";
        final String LATITUDE= "lat";
        final String LONGITUDE = "lng";
        final String RATING = "rating";

        myLat = getLatitude();
        myLong = getLongitude();
        restaurantJsonStr = restaurantJsonStr.replaceAll("/n"," ");
        String newString = restaurantJsonStr + "\"" + "}" + "]" + "}";
        System.out.println(newString);
        try {
            JSONObject restaurantJson = new JSONObject(restaurantJsonStr);
            JSONArray resultsArray = restaurantJson.getJSONArray(RESULTS);

            ArrayList<String> resultsArrayList = new ArrayList<String>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject restaurant = resultsArray.getJSONObject(i);

                lat = restaurant.getDouble("lat");
                longi = restaurant.getDouble("lng");



                restArray.add(i, new Restaurant(restaurant.getString("name"),distance(myLat, myLong, lat, longi),true,restaurant.getInt("rating")));
                System.out.println(restaurant.getString("name"));




//                String name;
//                String latitude;
//                String longitude;
//                String rating;
//
//                JSONObject restaurantName = resultsArray.getJSONObject(i);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private double distance(double myLat, double myLong, double lattitude, double longitude){

        double distance = 0;

        double p = Math.PI/180;

        distance = 0.5 - Math.cos((lattitude-myLat)*p)/2 + Math.cos(myLat*p)*Math.cos(lattitude*p) * (1-Math.cos((longitude-myLong)*p))/2;

        return distance;
    }


}