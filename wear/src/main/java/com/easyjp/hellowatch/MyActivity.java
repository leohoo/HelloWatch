package com.easyjp.hellowatch;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MyActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    public void showFeatures(View v) {
        Intent intent = new Intent(this, FeaturesListActivity.class);

        startActivity(intent);
    }

    public void getLocation(View v) {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(null != manager) {
            List<String> providers = manager.getAllProviders();

            for(String p : providers)
                Log.d("provider", p);

            Location loc = manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(null != loc) {
                Log.d("location", loc.toString());
            }
        }
    }

    public void scanBTDevices(View v) {
        Intent intent = new Intent(this, NearbyBTDevices.class);

        startActivity(intent);
    }
}
