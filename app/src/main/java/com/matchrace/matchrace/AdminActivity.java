package com.matchrace.matchrace;

import java.text.DecimalFormat;

import com.matchrace.matchrace.classes.BuoyPosition;
import com.matchrace.matchrace.classes.C;
import com.matchrace.matchrace.classes.SendDataHThread;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Administrator activity. Shows a google map with option to add buoys on it.
 */
public class AdminActivity extends FragmentActivity implements LocationListener, OnClickListener {

    // Application variables.
    private String user = "", event = "";
    private LocationManager locationManager;
    private boolean firstUse = true;
    private boolean disableLocation = false;

    // Views.
    private Marker currentPosition;
    private GoogleMap googleMap;
    private TextView tvLat, tvLng, tvUser, tvSpeed, tvDirection, tvEvent;
    private Button bBuoy1, bBuoy2, bBuoy3,/* bBuoy4, bBuoy5, bBuoy6, bBuoy7, bBuoy8, bBuoy9, bBuoy10,*/
            cancelButton, applyButton;
    private TimePicker time_picker;
    private DatePicker date_picker;
    //Added
    private BuoyPosition[] buoysArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Disables lock-screen and keeps screen on.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initialize();
    }

    /**
     * Initialize components.
     */
    private void initialize() {
        // The user name and event number connected to the application.
        user = getIntent().getStringExtra(C.USER_NAME);
        event = getIntent().getStringExtra(C.EVENT_NUM);

        // Initialize location ability.
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider, C.MIN_TIME, C.MIN_DISTANCE, this);

        // Initialize map.
        FragmentManager fm = getSupportFragmentManager();
        googleMap = ((SupportMapFragment) fm.findFragmentById(R.id.map)).getMap();

        // Adds location button in the top-right screen.
        googleMap.setMyLocationEnabled(true);

        // Focus the camera on the latLng location.
        LatLng latLng = new LatLng(32.056286, 34.824598);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, C.ZOOM_LEVEL);
        googleMap.animateCamera(cameraUpdate);

        // Initializing TextViews and Buttons.
        tvLat = (TextView) findViewById(R.id.tvLat);
        tvLng = (TextView) findViewById(R.id.tvLng);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        tvDirection = (TextView) findViewById(R.id.tvDirection);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvEvent = (TextView) findViewById(R.id.tvEvent);
        tvUser.setText(user.substring(6));
        tvEvent.setText(event);

        bBuoy1 = (Button) findViewById(R.id.bBuoy1);
        bBuoy2 = (Button) findViewById(R.id.bBuoy2);
        bBuoy3 = (Button) findViewById(R.id.bBuoy3);
        /*
        bBuoy4 = (Button) findViewById(R.id.bBuoy4);
        bBuoy5 = (Button) findViewById(R.id.bBuoy5);
        bBuoy6 = (Button) findViewById(R.id.bBuoy6);
        bBuoy7 = (Button) findViewById(R.id.bBuoy7);
        bBuoy8 = (Button) findViewById(R.id.bBuoy8);
        bBuoy9 = (Button) findViewById(R.id.bBuoy9);
        bBuoy10 = (Button) findViewById(R.id.bBuoy10);
        */
        //<added code
        cancelButton = (Button) findViewById(R.id.cancel_button);
        applyButton = (Button) findViewById(R.id.apply_button);
        time_picker = (TimePicker) findViewById(R.id.time_picker);
        date_picker = (DatePicker) findViewById(R.id.date_picker);
        buoysArr = new BuoyPosition[10];
        //added code>

        bBuoy1.setOnClickListener(this);
        bBuoy2.setOnClickListener(this);
        bBuoy3.setOnClickListener(this);
        /*
        bBuoy4.setOnClickListener(this);
        bBuoy5.setOnClickListener(this);
        bBuoy6.setOnClickListener(this);
        bBuoy7.setOnClickListener(this);
        bBuoy8.setOnClickListener(this);
        bBuoy9.setOnClickListener(this);
        bBuoy10.setOnClickListener(this);
        */
        //<added code
        cancelButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        time_picker.setOnClickListener(this);
        time_picker.setIs24HourView(true);
        date_picker.setOnClickListener(this);

        //added code>

        //***************************************************TEST***********************************************
        /*
        if (firstUse) {
            firstUse = false;
            bBuoy1.setEnabled(true);
            bBuoy2.setEnabled(true);
            bBuoy3.setEnabled(true);
            bBuoy4.setEnabled(true);
            bBuoy5.setEnabled(true);
            bBuoy6.setEnabled(true);
            bBuoy7.setEnabled(true);
            bBuoy8.setEnabled(true);
            bBuoy9.setEnabled(true);
            bBuoy10.setEnabled(true);
        }
        */
        //******************************************************************************************************
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Disables the location changed code.
        Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
        startActivity(intent);
        disableLocation = true;
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!disableLocation) {
            // If true- enables the buoy buttons.
            if (firstUse) {
                firstUse = false;
                bBuoy1.setEnabled(true);
                bBuoy2.setEnabled(true);
                bBuoy3.setEnabled(true);
                /*
                bBuoy4.setEnabled(true);
                bBuoy5.setEnabled(true);
                bBuoy6.setEnabled(true);
                bBuoy7.setEnabled(true);
                bBuoy8.setEnabled(true);
                bBuoy9.setEnabled(true);
                bBuoy10.setEnabled(true);
                */
                cancelButton.setEnabled(true);
            }

            String lat = new DecimalFormat("##.######").format(location.getLatitude());
            String lng = new DecimalFormat("##.######").format(location.getLongitude());
            String speed = "" + location.getSpeed();
            String bearing = "" + location.getBearing();

            // Updates TextViews in layout.
            tvLat.setText(lat);
            tvLng.setText(lng);
            tvSpeed.setText(speed);
            tvDirection.setText(bearing);

            // Adds currentPosition marker to the google map.
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            if (currentPosition != null) {
                currentPosition.remove();
            }
            currentPosition = googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Position").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_sailor_user_low)));

            // Focus the camera on the currentPosition marker.
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, C.ZOOM_LEVEL);
            googleMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // The buoy name with the event number.
        String fullBuoyName = "";
        int bouyIndex = -1;
        switch (v.getId()) {
            //<added
            case R.id.cancel_button:
                applyButton.setEnabled(false);//for testing
                cancelButton.setEnabled(true);//for testing
                googleMap.clear();
                buoysArr = new BuoyPosition[10];
                bBuoy1.setEnabled(true);
                bBuoy2.setEnabled(true);
                bBuoy3.setEnabled(true);
                /*
                bBuoy4.setEnabled(true);
                bBuoy5.setEnabled(true);
                bBuoy6.setEnabled(true);
                bBuoy7.setEnabled(true);
                bBuoy8.setEnabled(true);
                bBuoy9.setEnabled(true);
                bBuoy10.setEnabled(true);
                */
                return;
            case R.id.apply_button:
                applyButton.setEnabled(false);//for testing
                //cancelButton.setEnabled(false);//for testing

                // HandlerThread for sending the buoy location to the DB through thread.
                SendDataHThread thread = new SendDataHThread("SendBuoys");
                thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
                thread.createEvent(buoysArr,time_picker,date_picker,event);
                thread.start();


                return;
            //added>
            case R.id.bBuoy1:
                //fullBuoyName = C.BUOY_PREFIX + "1_" + event;
                bouyIndex = 0;
                bBuoy1.setEnabled(false);
                break;
            case R.id.bBuoy2:
                //  fullBuoyName = C.BUOY_PREFIX + "2_" + event;
                bouyIndex = 1;
                bBuoy2.setEnabled(false);
                break;
            case R.id.bBuoy3:
                // fullBuoyName = C.BUOY_PREFIX + "3_" + event;
                bouyIndex = 2;
                bBuoy3.setEnabled(false);
                break;
            /*
            case R.id.bBuoy4:
                //fullBuoyName = C.BUOY_PREFIX + "4_" + event;
                bouyIndex = 3;
                bBuoy4.setEnabled(false);
                break;
            case R.id.bBuoy5:
                // fullBuoyName = C.BUOY_PREFIX + "5_" + event;
                bouyIndex = 4;
                bBuoy5.setEnabled(false);
                break;
            case R.id.bBuoy6:
                //fullBuoyName = C.BUOY_PREFIX + "6_" + event;
                bouyIndex = 5;
                bBuoy6.setEnabled(false);
                break;
            case R.id.bBuoy7:
                //fullBuoyName = C.BUOY_PREFIX + "7_" + event;
                bouyIndex = 6;
                bBuoy7.setEnabled(false);
                break;
            case R.id.bBuoy8:
                // fullBuoyName = C.BUOY_PREFIX + "8_" + event;
                bouyIndex = 7;
                bBuoy8.setEnabled(false);
                break;
            case R.id.bBuoy9:
                // fullBuoyName = C.BUOY_PREFIX + "9_" + event;
                bouyIndex = 8;
                bBuoy9.setEnabled(false);
                break;
            case R.id.bBuoy10:
                // fullBuoyName = C.BUOY_PREFIX + "10_" + event;
                bouyIndex = 9;
                bBuoy10.setEnabled(false);
                break;
                */
        }


        String lat = new DecimalFormat("##.######").format(currentPosition.getPosition().latitude);
        String lng = new DecimalFormat("##.######").format(currentPosition.getPosition().longitude);

        if (bouyIndex > -1) {
            applyButton.setEnabled(true);
            //buoysArr[bouyIndex] = new BuoyPosition(""+bouyIndex, ""+bouyIndex);
            //**********************************TEST**********************************************
            buoysArr[bouyIndex] = new BuoyPosition(lat, lng);
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(fullBuoyName.split("_")[0]).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_buoy_low)));

        }
        // String speed = "" + 0;
        // String bearing = "" + 0;

        /*
        // HandlerThread for sending the buoy location to the DB through thread.
        SendDataHThread thread = new SendDataHThread("SendBuoys");
        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        thread.setFullUserName(fullBuoyName);
        thread.setLat(lat);
        thread.setLng(lng);
        thread.setSpeed(speed);
        thread.setBearing(bearing);
        thread.setEvent(event);
        thread.start();
        */

        // Adds a buoy on the map.

    }

}
