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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vahitdurmus.nmeaproject.Adapters.RecyleLocationAdapter;
import com.example.vahitdurmus.nmeaproject.nmeamessage.GGA;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.spec.ECField;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, GpsStatus.NmeaListener, com.google.android.gms.location.LocationListener {

    private static LocationTrack locationTrack;
    RecyclerView locationRecyleView;
    FloatingActionButton floatingActionButton;
    RecyleLocationAdapter recyleLocationAdapter;
    List<GGA> ggaList;
    LinearLayoutManager linearLayoutManager;

    TextView textViewLatitude;
    TextView textViewLongitude;
    TextView textViewTime;
    TextView textViewSatellites;
    TextView textViewQuality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ggaList=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        recyleLocationAdapter=new RecyleLocationAdapter(this, ggaList);
        floatingActionButton=findViewById(R.id.fabadd);
        locationRecyleView=findViewById(R.id.recylerview_location);

        locationRecyleView.setAdapter(recyleLocationAdapter);
        locationRecyleView.setLayoutManager(linearLayoutManager);

        textViewLatitude=findViewById(R.id.txtview_latitude);
        textViewLongitude=findViewById(R.id.txtview_longitude);
        textViewQuality=findViewById(R.id.txtview_quality);
        textViewSatellites=findViewById(R.id.txtview_satellitesnumber);
        textViewTime=findViewById(R.id.txtview_time);


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

           textViewLatitude.setText(String.valueOf(locationTrack.get$GGA().getLatitude()));
           textViewLongitude.setText(String.valueOf(locationTrack.get$GGA().getLongitude()));
           textViewTime.setText(locationTrack.get$GGA().getTime());
           textViewSatellites.setText(String.valueOf(locationTrack.get$GGA().getNumberOfSatellitesInUse()));
           textViewQuality.setText(String.valueOf(locationTrack.get$GGA().getFixQuality()));
       }
       catch (NullPointerException e){
            e.printStackTrace();
       }
       catch (Exception e){

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
