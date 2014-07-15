package com.easyjp.hellowatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyActivity extends Activity implements WearableListView.ClickListener {
    private class DemoActionItem {
        int id;
        String name;

        private DemoActionItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static DemoActionItem[] mainMenus;

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
                WearableListView listView = (WearableListView)stub.findViewById(R.id.listView);
                listView.setAdapter(new Adapter(MyActivity.this));
                listView.setClickListener(MyActivity.this);
            }
        });

        mainMenus = new DemoActionItem[]{
                new DemoActionItem(R.id.notifications, "Notifications"),
                new DemoActionItem(R.id.show_features, "Show Hardware Features"),
                new DemoActionItem(R.id.get_location, "Get Location"),
                new DemoActionItem(R.id.bluetooth_le, "Bluetooth LE test")
        };

    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        int pos = viewHolder.getPosition();

        switch (mainMenus[pos].id) {
            case R.id.show_features:
                showFeatures(viewHolder.itemView);
                break;

            case R.id.scan_bt_devices:
                scanBTDevices(viewHolder.itemView);
                break;

            case R.id.bluetooth_le:
                Intent i = new Intent(this, BluetoothLEActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    private static final class Adapter extends WearableListView.Adapter {
        private final Context mContext;
        private final LayoutInflater mInflater;

        private Adapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(
                    mInflater.inflate(R.layout.list_item_layout, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView view = (TextView) holder.itemView.findViewById(R.id.name);
            view.setText(mainMenus[position].name);
            holder.itemView.setTag(mainMenus[position].id);
        }

        @Override
        public int getItemCount() {
            return mainMenus.length;
        }
    }

    public void showFeatures(View v) {
        Intent intent = new Intent(this, FeaturesListActivity.class);

        startActivity(intent);
    }

    public void getLocation(View v) {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (null != manager) {
            List<String> providers = manager.getAllProviders();

            for (String p : providers)
                Log.d("provider", p);

            Location loc = manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (null != loc) {
                Log.d("location", loc.toString());
            }
        }
    }

    public void scanBTDevices(View v) {
        Intent intent = new Intent(this, NearbyBTDevices.class);

        startActivity(intent);
    }
}
