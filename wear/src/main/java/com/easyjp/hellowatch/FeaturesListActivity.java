package com.easyjp.hellowatch;

import android.app.ListActivity;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FeaturesListActivity extends ListActivity {

    FeatureArrayAdaper adapter;

    class FeatureArrayAdaper extends ArrayAdapter<FeatureInfo> {
        FeatureArrayAdaper(Context context, int resource, FeatureInfo[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView)super.getView(position, convertView, parent);
            FeatureInfo f = getItem(position);
            String text = (null != f.name)? f.name : f.toString();
            view.setText(text);

            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_list);


        PackageManager pm = getPackageManager();

        FeatureInfo[] features = pm.getSystemAvailableFeatures();

        adapter = new FeatureArrayAdaper(this, R.layout.list_item_layout, features);

        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.features_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        FeatureInfo f = adapter.getItem(position);
        Toast.makeText(this, f.name, Toast.LENGTH_SHORT).show();
    }
}
