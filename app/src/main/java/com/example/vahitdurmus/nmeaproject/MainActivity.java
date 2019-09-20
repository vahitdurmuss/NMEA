package com.example.vahitdurmus.nmeaproject;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vahitdurmus.nmeaproject.Adapters.RecyleLocationAdapter;
import com.example.vahitdurmus.nmeaproject.nmeamessage.GGA;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, GpsStatus.NmeaListener, com.google.android.gms.location.LocationListener {

    private static LocationTrack locationTrack;
    TextView displayTextView;
    RecyclerView locationRecyleView;
    FloatingActionButton floatingActionButton;
    RecyleLocationAdapter recyleLocationAdapter;
    List<GGA> ggaList;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ggaList=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(this);
        recyleLocationAdapter=new RecyleLocationAdapter(this, ggaList);
        displayTextView=findViewById(R.id.textview_display);
        floatingActionButton=findViewById(R.id.fabadd);
        locationRecyleView=findViewById(R.id.recylerview_location);

        locationRecyleView.setAdapter(recyleLocationAdapter);
        locationRecyleView.setLayoutManager(linearLayoutManager);

        locationTrack=new LocationTrack.Builder().setContext(this).buildGoogleApiClient().createLocationRequest(2000,1000).addNmeaStatusListener().connectGoogleApi().build();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    recyleLocationAdapter.addNLocation(locationTrack.get$GGA());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onNmeaReceived(long l, String s) {
       try {
           locationTrack.setNmeaObject(s);
           displayTextView.setText(locationTrack.get$GGA().toString());
       }
       catch (NullPointerException e){

       }
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationTrack.startLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        locationTrack.connectGoogleAPI();

    }

    @Override
    public void onLocationChanged(Location location) {
        locationTrack.setCurrentLocation(location);
    }


    @Override
    protected void onPause() {
        locationTrack.stopLocationRequest();
        super.onPause();
    }

    @Override
    protected void onResume() {
        locationTrack.startLocationRequest();
        super.onResume();
    }
}
